/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.processamento.acompanhachain.status.anemic;

import br.com.techchallenge.fiap.process.adapter.gateways.ProcessamentoGateway;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import br.com.techchallenge.fiap.process.core.usecase.processamento.acompanhachain.AcompanhamentoChain;

import static br.com.techchallenge.fiap.process.core.domain.Finals.MESSAGE_PRONTO;

public class AcompanhamentoChainPronto implements AcompanhamentoChain {

    private ProcessamentoGateway processamentoGateway;

    public AcompanhamentoChainPronto(ProcessamentoGateway processamentoGateway) {
        this.processamentoGateway = processamentoGateway;
    }

    @Override
    public String sms(Status status) {
        return status.equals(Status.PRONTO) ? MESSAGE_PRONTO : null;
    }
}
