/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.processamento;

import br.com.techchallenge.fiap.process.adapter.gateways.ProcessamentoGateway;
import br.com.techchallenge.fiap.process.adapter.presenter.ProcessamentoResponse;
import br.com.techchallenge.fiap.process.core.domain.document.Document;
import br.com.techchallenge.fiap.process.infrastructure.gateways.MapperGateway;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static br.com.techchallenge.fiap.process.core.domain.Finals.*;

@Component
public class ProcessamentoUseCase extends MediaListenerAdapter {

    private final ProcessamentoGateway processamentoGateway;

    public ProcessamentoUseCase(ProcessamentoGateway processamentoGateway) {
        this.processamentoGateway = processamentoGateway;
    }


    public ProcessamentoResponse processaExecute(List<Document> filename) {

        AtomicReference<String> absolutePath = new AtomicReference<>();
        AtomicReference<IMediaReader> mediaReader = new AtomicReference<>();
        filename.forEach(vd -> {
            absolutePath.set(new File(inputFilename + vd.getNome()).getPath());
            mediaReader.set(ToolFactory.makeReader(absolutePath.get()));
            mediaReader.get().setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
            mediaReader.get().addListener(this);
            while (mediaReader.get().readPacket() == null) ;
        });

        return null;
    }


    public void onVideoPicture(IVideoPictureEvent event) {

        if (event.getStreamIndex() != mVideoStreamIndex) {
            if (mVideoStreamIndex == -1)
                mVideoStreamIndex = event.getStreamIndex();
        }

        if (mLastPtsWrite == Global.NO_PTS) {
            mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
        }

        if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES) {

            Document document = new Document();
            try {

                String outputFilename = dumpImageToFile(event.getImage());
                File file = new File(outputFilename);
                try (InputStream is = new FileInputStream(file)) {
                    document = new Document(new Random().nextInt(), file.getName(), new Binary(is.readAllBytes()));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                processamentoGateway.salvaProcessamento(new MapperGateway().toEntity(document));

                double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf("No tempo decorrido de  %6.3f segundos criou: %s\n", seconds, "um print. -> " + outputFilename);

                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String dumpImageToFile(BufferedImage image) {

        String outputFilename = null;
        try {

            outputFilename = outputFilePrefix + System.currentTimeMillis() + ".png";
            File file = new File(outputFilename);
            ImageIO.write(image, "png", file);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return outputFilename;
    }
}


