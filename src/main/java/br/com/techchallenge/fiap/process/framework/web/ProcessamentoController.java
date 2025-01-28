/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.framework.web;


import br.com.techchallenge.fiap.process.adapter.controllers.Processamento;
import br.com.techchallenge.fiap.process.adapter.presenter.ProcessamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class ProcessamentoController {


    private final Processamento processamentoController;

    public ProcessamentoController(Processamento processamentoController) {
        this.processamentoController = processamentoController;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/process/video/", consumes = {"multipart/form-data"})
    public ResponseEntity<?> process(List<MultipartFile> filename) {
        ProcessamentoResponse processamentoResponse = processamentoController.processa(filename);
        return ResponseEntity.ok(processamentoResponse);

    }
}
