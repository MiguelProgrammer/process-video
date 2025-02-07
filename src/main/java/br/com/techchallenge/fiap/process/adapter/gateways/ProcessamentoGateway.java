/*
 * Copyright (c) 2024-2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.gateways;

import br.com.techchallenge.fiap.process.adapter.presenter.ProcessamentoResponse;
import br.com.techchallenge.fiap.process.infrastructure.persistence.processa.entities.DocumentEntity;

public interface ProcessamentoGateway {

    ProcessamentoResponse salvaProcessamento(DocumentEntity document);
}
