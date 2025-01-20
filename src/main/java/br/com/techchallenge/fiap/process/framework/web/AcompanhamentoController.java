/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.framework.web;


import br.com.techchallenge.fiap.process.adapter.controllers.Acompanhamento;
import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.infrastructure.processa.DocumentRepository;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;

@RestController
public class AcompanhamentoController {

    @Autowired
    private DocumentRepository repository;

    private final Acompanhamento acompanhamentoController;


    public static final double SECONDS_BETWEEN_FRAMES = 10;

    private static final String inputFilename = "C:\\Users\\Miguel Silva\\Videos\\videos testes\\video-medio.mp4";
    private static final String outputFilePrefix = "C:\\Users\\Miguel Silva\\Videos\\videos testes\\";

    // The video stream index, used to ensure we display frames from one and
    // only one video stream from the media container.
    private static int mVideoStreamIndex = -1;

    // Time of last frame write
    private static long mLastPtsWrite = Global.NO_PTS;

    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
            (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
    private final DocumentRepository documentRepository;


    public AcompanhamentoController(Acompanhamento acompanhamentoController,
                                    DocumentRepository documentRepository) {
        this.acompanhamentoController = acompanhamentoController;
        this.documentRepository = documentRepository;
    }

    /**
     * GET /neighborfood/acompanhamento/{idPedido} : Procura o status de um pedido
     * Retorna o status de um pedido
     *
     * @param idPedido id do pedido (required)
     * @return successful operation (status code 200)
     * or Id inválido (status code 400)
     * or Pedido não encontrado (status code 404)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/neighborfood/acompanhamento/{idPedido}",
            produces = {"application/json"}
    )
    public ResponseEntity<AcompanhamentoResponseDTO> findOrderByIdOrder(@PathVariable("idPedido") Long idPedido) {
        AcompanhamentoResponseDTO orderStatusExecute = acompanhamentoController.statusDoPedido(idPedido);
        return ResponseEntity.ok(orderStatusExecute);
    }

    /**
     * @param filename (optional)
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/process/video/",
            consumes = {"multipart/form-data"}
    )
    public ResponseEntity<FileOutputStream> process(MultipartFile filename) throws IOException, URISyntaxException {
        String absolutePath = new File(inputFilename).getPath();
        IMediaReader mediaReader = ToolFactory.makeReader(absolutePath);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new ImageSnapListener());
        while (mediaReader.readPacket() == null) ;


        /*try (ZipFile zipFile = new ZipFile(new File(Objects.requireNonNull(filename.getOriginalFilename())).getAbsolutePath())) {

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            Integer count = 0;
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                System.out.println(entry.getName());
                InputStream stream = zipFile.getInputStream(entry);

                Document documen2t = new Document(count++, "Miguel",
                        new Binary(BsonBinarySubType.BINARY, stream.readAllBytes()));
                repository.save(documen2t);
            }
        } catch (Exception e) {
            System.err.println(e);
        }*/

        return null;
    }


    @org.springframework.data.mongodb.core.mapping.Document
    public static class Document {

        @Id
        @Field
        private Integer id;
        @Field
        private String nome;
        @Field
        private Binary file;

        public Document() {
        }

        public Document(Integer id, String nome, Binary file) {
            this.id = id;
            this.nome = nome;
            this.file = file;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }


    }

    @Transactional
    private class ImageSnapListener extends MediaListenerAdapter {

        public void onVideoPicture(IVideoPictureEvent event) {

            if (event.getStreamIndex() != mVideoStreamIndex) {
                if (mVideoStreamIndex == -1)
                    mVideoStreamIndex = event.getStreamIndex();
                else
                    return;
            }

            // if uninitialized, back date mLastPtsWrite to get the very first frame
            if (mLastPtsWrite == Global.NO_PTS)
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;

            // if it's time to write the next frame
            if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES) {

                String outputFilename = dumpImageToFile(event.getImage());
                File file = new File(outputFilename);
                InputStream is = null;
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                Document document;
                try {
                    document = new Document(this.generateSequence(), file.getName(), new Binary(is.readAllBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                documentRepository.save(document);

                double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf("at elapsed time of %6.3f seconds wrote: %s\n", seconds, "print criado -> "+outputFilename);

                // update last write time
                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            }

        }

        @Transactional
        private String dumpImageToFile(BufferedImage image) {
            try {
                String outputFilename = outputFilePrefix + System.currentTimeMillis() + ".png";
                File file = new File(outputFilename);
                ImageIO.write(image, "png", file);
                return outputFilename;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Integer generateSequence() {
            return new Random().nextInt();
        }
    }
}
