/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.inbound;

import br.com.techchallenge.fiap.process.core.domain.document.Document;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DocumentDTO {

    private List<Binary> binaryList;

    public DocumentDTO() {
    }

    public DocumentDTO(List<Binary> binaryList) {
        this.binaryList = binaryList;
    }

    public List<Binary> getBinaryList() {
        return binaryList;
    }

    public void setBinaryList(List<Binary> binaryList) {
        this.binaryList = binaryList;
    }

    public List<Document> toDomain(List<MultipartFile> filename) {

        List<Document> documents = new ArrayList<>();
        filename.forEach(f -> {
            FileInputStream fileInputStream = new FileInputStream();
            try {
                Document document = new Document(new Random().nextInt(), f.getOriginalFilename(), new Binary(fileInputStream.readAllBytes()));
                documents.add(document);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return documents;
    }
}
