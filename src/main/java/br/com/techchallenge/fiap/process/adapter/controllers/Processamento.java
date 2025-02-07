/*
 * Copyright (c) 2024-2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.controllers;

import br.com.techchallenge.fiap.process.adapter.inbound.DocumentDTO;
import br.com.techchallenge.fiap.process.adapter.presenter.ProcessamentoResponse;
import br.com.techchallenge.fiap.process.core.usecase.processamento.ProcessamentoUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class Processamento {

    private final ProcessamentoUseCase processamentoUseCase;

    public Processamento(ProcessamentoUseCase processamentoUseCase) {
        this.processamentoUseCase = processamentoUseCase;
    }

    public ProcessamentoResponse processa(List<MultipartFile> filename) {
        return processamentoUseCase.processaExecute(new DocumentDTO().toDomain(filename));
    }
}
