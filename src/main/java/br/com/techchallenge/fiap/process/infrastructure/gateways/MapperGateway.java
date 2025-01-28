/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.gateways;

import br.com.techchallenge.fiap.process.core.domain.document.Document;
import br.com.techchallenge.fiap.process.infrastructure.persistence.processa.entities.DocumentEntity;

public class MapperGateway {

    public DocumentEntity toEntity(Document dto) {
        DocumentEntity entity = new DocumentEntity();
        entity.setNome(dto.getNome());
        entity.setId(dto.getId());
        entity.setFile(dto.getFile());
        return entity;
    }
}
