/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.acompanhamento.acompanhachain.status.anemic;

import br.com.techchallenge.fiap.process.adapter.gateways.AcompanhamentoGateway;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import br.com.techchallenge.fiap.process.core.usecase.acompanhamento.acompanhachain.AcompanhamentoChain;

import static br.com.techchallenge.fiap.process.core.domain.Finals.MESSAGE_FINALIZADO;

public class AcompanhamentoChainFinalizado implements AcompanhamentoChain {

    private AcompanhamentoGateway acompanhamentoGateway;

    public AcompanhamentoChainFinalizado(AcompanhamentoGateway acompanhamentoGateway) {
        this.acompanhamentoGateway = acompanhamentoGateway;
    }

    @Override
    public String sms(Status status) {
        return status.equals(Status.FINALIZADO) ? MESSAGE_FINALIZADO :
        new AcompanhamentoChainPronto(acompanhamentoGateway).sms(status);
    }
}
