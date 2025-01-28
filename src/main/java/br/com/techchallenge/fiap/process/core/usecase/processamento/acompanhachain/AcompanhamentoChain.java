/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.processamento.acompanhachain;


import br.com.techchallenge.fiap.process.core.domain.enums.Status;

public interface  AcompanhamentoChain {

    String sms(Status status);
}
