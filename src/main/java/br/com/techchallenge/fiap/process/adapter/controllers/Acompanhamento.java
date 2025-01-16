/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.controllers;

import br.com.techchallenge.fiap.process.adapter.presenter.AcompanhamentoResponse;
import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.core.usecase.acompanhamento.AcompanhamentoUseCase;
import org.springframework.stereotype.Component;

@Component
public class Acompanhamento {

    private final AcompanhamentoUseCase acompanhamentoUseCase;

    public Acompanhamento(AcompanhamentoUseCase acompanhamentoUseCase) {
        this.acompanhamentoUseCase = acompanhamentoUseCase;
    }

    public AcompanhamentoResponseDTO statusDoPedido(Long idPedido) {
        AcompanhamentoResponse orderStatusExecute = acompanhamentoUseCase.getOrderStatusExecute(idPedido);
        return orderStatusExecute.pedidoFromResponse();
    }

}
