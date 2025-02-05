/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.gateways;


import br.com.techchallenge.fiap.process.adapter.gateways.ProcessamentoGateway;
import br.com.techchallenge.fiap.process.adapter.presenter.ProcessamentoResponse;
import br.com.techchallenge.fiap.process.infrastructure.persistence.processa.DocumentRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.processa.entities.DocumentEntity;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProcessamentoRepositorioGateway implements ProcessamentoGateway {

    private final DocumentRepository documentRepository;

    public ProcessamentoRepositorioGateway(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * @param document
     * @return
     */
    @Override

    public ProcessamentoResponse salvaProcessamento(DocumentEntity document) {
        ProcessamentoResponse processamentoResponse = new ProcessamentoResponse(HttpStatus.SC_NO_CONTENT);
        if (documentRepository.save(document).equals(Boolean.TRUE)) {
            processamentoResponse.setStatus(HttpStatus.SC_CREATED);
        }
        return processamentoResponse;
    }
}
