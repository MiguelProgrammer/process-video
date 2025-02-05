/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.framework.web;


import br.com.techchallenge.fiap.process.adapter.controllers.Processamento;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
public class ProcessamentoController {


    private final Processamento processamentoController;

    public ProcessamentoController(Processamento processamentoController) {
        this.processamentoController = processamentoController;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/process/video/", consumes = {"multipart/form-data"})
    public CompletableFuture<?> process(List<MultipartFile> filename) {
        processamentoController.processa(filename);
        return new CompletableFuture<>();
    }

}
