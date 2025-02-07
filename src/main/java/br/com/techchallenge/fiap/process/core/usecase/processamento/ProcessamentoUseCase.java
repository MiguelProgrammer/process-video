/*
 * Copyright (c) 2024-2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.processamento;

import br.com.techchallenge.fiap.process.adapter.gateways.ProcessamentoGateway;
import br.com.techchallenge.fiap.process.adapter.presenter.ProcessamentoResponse;
import br.com.techchallenge.fiap.process.core.domain.document.Document;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import br.com.techchallenge.fiap.process.infrastructure.gateways.MapperGateway;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static br.com.techchallenge.fiap.process.core.domain.Finals.*;

@Component
public class ProcessamentoUseCase extends MediaListenerAdapter {

    static Document document = new Document();
    private final TaskExecutor taskExecutor;
    private final ProcessamentoGateway processamentoGateway;

    public ProcessamentoUseCase(ProcessamentoGateway processamentoGateway, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.processamentoGateway = processamentoGateway;
        this.taskExecutor = taskExecutor;
    }


    public ProcessamentoResponse processaExecute(List<Document> filename) {

        taskExecutor.execute(() -> {
            try {
                Thread.sleep(2000);

                AtomicReference<String> absolutePath = new AtomicReference<>();
                AtomicReference<IMediaReader> mediaReader = new AtomicReference<>();
                filename.forEach(vd -> {

                    document = new Document();
                    System.out.println("Processando arquivo: " + vd.getNome());
                    System.out.println("Status Documento -> " + document.getStatus() + " ...");

                    absolutePath.set(new File(inputFilename + vd.getNome()).getPath());
                    mediaReader.set(ToolFactory.makeReader(absolutePath.get()));
                    mediaReader.get().setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
                    mediaReader.get().addListener(this);
                    while (mediaReader.get().readPacket() == null) ;

                    document.setStatus(Status.FINALIZADO);
                    System.out.println("Status Documento -> " + document.getStatus() + " ...\n\n\n");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return null;
    }


    public void onVideoPicture(IVideoPictureEvent event) {


        if (event.getStreamIndex() != mVideoStreamIndex) {
            if (mVideoStreamIndex == -1) {
                mVideoStreamIndex = event.getStreamIndex();
            }
        }

        if (mLastPtsWrite == Global.NO_PTS) {
            mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
        }

        if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES) {

            try {

                document.setId(new Random().nextInt());
                String outputFilename = dumpImageToFile(event.getImage(), document);
                File file = new File(outputFilename);

                try (InputStream is = new FileInputStream(file)) {
                    document.setNome(file.getName());
                    document.setFile(new Binary(is.readAllBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                processamentoGateway.salvaProcessamento(new MapperGateway().toEntity(document));

                double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf("No tempo decorrido de  %6.3f segundos criou: %s\n\n", seconds, "um print. -> " + outputFilename);

                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String dumpImageToFile(BufferedImage image, Document document) {

        String outputFilename = null;
        try {
            document.setStatus(Status.PROCESSANDO);
            System.out.println("Status Documento -> " + document.getStatus() + " ...");
            outputFilename = outputFilePrefix + System.currentTimeMillis() + ".png";
            File file = new File(outputFilename);
            ImageIO.write(image, "png", file);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return outputFilename;
    }
}


