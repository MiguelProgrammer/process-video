/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.persistence.processa;

import br.com.techchallenge.fiap.process.infrastructure.persistence.processa.entities.DocumentEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DocumentRepositoryTest {


    private AutoCloseable autoCloseable;

    @Mock
    private DocumentRepository documentRepository;

    private DocumentEntity documentEntity;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        documentEntity = new DocumentEntity();
    }

    @Test
    @DisplayName("Salva um documento")
    void save() {

        /* ARRANGE */
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(documentEntity);

        /* ACT */
        var documentSaved = documentRepository.save(documentEntity);

        /* ASSERT */
         assertThat(documentSaved).isNotNull().isEqualTo(documentEntity);
         verify(documentRepository, times(1)).save(any(DocumentEntity.class));
    }

    @AfterEach
    void tearDown() throws Exception {
        this.autoCloseable.close();
    }
}