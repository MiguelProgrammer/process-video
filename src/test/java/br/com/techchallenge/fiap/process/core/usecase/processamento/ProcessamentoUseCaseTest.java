/*
 * Copyright (c) 2024-2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.processamento;

import br.com.techchallenge.fiap.process.adapter.inbound.DocumentDTO;
import br.com.techchallenge.fiap.process.infrastructure.gateways.ProcessamentoRepositorioGateway;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.internal.util.IOUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ProcessamentoUseCaseTest {

    @Autowired
    private ProcessamentoRepositorioGateway processamentoGateway;

    @Autowired
    private ProcessamentoUseCase processamentoUseCase;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("USECASE - Processa Videos UseCase")
    void processExecute() throws IOException {


        List<MultipartFile> fileList = preparedMultipartFiles();

        /* ACT */

        /* ASSERT */
        processamentoUseCase.processaExecute(new DocumentDTO().toDomain(fileList));

    }

    private List<MultipartFile> preparedMultipartFiles() throws IOException {
        String inputFilename = "C:\\Videos\\video1.mp4";
        List<MultipartFile> fileList = new ArrayList<>();

        File file = new File(inputFilename);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        fileList.add(multipartFile);
        return fileList;
    }


}