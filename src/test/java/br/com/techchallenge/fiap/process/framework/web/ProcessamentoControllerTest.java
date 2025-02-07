/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.framework.web;

import br.com.techchallenge.fiap.process.config.security.SecSecurityConfig;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.internal.util.IOUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProcessamentoControllerTest {


    @Autowired
    private ProcessamentoController controller;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SecSecurityConfig secSecurityConfig;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CONTROLLER - Envia arquivos para processamento")
    void process() throws ExecutionException, InterruptedException, IOException {

        /* ACT */

        String inputFilename = "C:\\Videos\\video1.mp4";
        String outputFilePrefix = "C:\\Videos\\";
        List<MultipartFile> fileList = new ArrayList<>();

        File file = new File(inputFilename);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        fileList.add(multipartFile);

        Future<?> process = controller.process(fileList, request);
        assertThat(process).isInstanceOf(Future.class);
        assertThat(process).isNotNull();
    }
}