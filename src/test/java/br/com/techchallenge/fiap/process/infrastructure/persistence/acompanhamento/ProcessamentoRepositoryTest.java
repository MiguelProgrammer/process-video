///*
// * Copyright (c) 2024. MiguelProgrammer
// */
//
//package br.com.techchallenge.fiap.process.infrastructure.persistence.acompanhamento;
//
//import br.com.techchallenge.fiap.process.core.domain.enums.Status;
//import br.com.techchallenge.fiap.process.infrastructure.persistence.acompanhamento.entities.AcompanhamentoEntity;
//import io.qameta.allure.Severity;
//import io.qameta.allure.SeverityLevel;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ProcessamentoRepositoryTest {
//
//    @Mock
//    private DocumentRepository repository;
//    private AutoCloseable autoCloseable;
//
//    private AcompanhamentoEntity acompanhamento;
//
//    @BeforeEach
//    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        acompanhamento = new AcompanhamentoEntity();
//        acompanhamento.setIdPedido(1L);
//        acompanhamento.setStatus(Status.RECEBIDO);
//    }
//
//    @Test
//    @Severity(SeverityLevel.TRIVIAL)
//    @DisplayName("Salvando o status de um pedido")
//    void save() {
//
//        /* ARRANGE */
//        when(repository.save(any(AcompanhamentoEntity.class))).thenReturn(acompanhamento);
//
//        /* ACT */
//        var acompanhamentoSalvo = repository.save(acompanhamento);
//
//        /* ASSERT */
//        assertThat(acompanhamentoSalvo).isNotNull().isEqualTo(acompanhamento);
//        verify(repository, times(1)).save(any(AcompanhamentoEntity.class));
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }
//}