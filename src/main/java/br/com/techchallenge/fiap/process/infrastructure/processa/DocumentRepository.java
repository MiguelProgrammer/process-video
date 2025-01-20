/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.processa;

import br.com.techchallenge.fiap.process.framework.web.AcompanhamentoController;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends MongoRepository<AcompanhamentoController.Document, Long> {

}
