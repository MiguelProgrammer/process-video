/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.controllers;

import br.com.techchallenge.fiap.process.adapter.presenter.AcessoResponse;
import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.core.usecase.processamento.ProcessamentoUseCase;
import org.springframework.stereotype.Component;

@Component
public class Acesso {

    private final ProcessamentoUseCase processamentoUseCase;

    public Acesso(ProcessamentoUseCase processamentoUseCase) {
        this.processamentoUseCase = processamentoUseCase;
    }

    public AcompanhamentoResponseDTO statusDoPedido(Long idPedido) {
        AcessoResponse orderStatusExecute = processamentoUseCase.getOrderStatusExecute(idPedido);
        return orderStatusExecute.pedidoFromResponse();
    }

}
