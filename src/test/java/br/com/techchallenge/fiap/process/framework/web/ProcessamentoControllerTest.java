/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.framework.web;

import br.com.techchallenge.fiap.process.config.exceptions.PedidoException;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProcessamentoControllerTest {


    @Autowired
    private ProcessamentoController controller;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CONTROLLER - Busca um pedido não exitente")
    void findOrderByIdOrder() {

        /* ACT */
        Long idPedido = 1L;

        assertThatThrownBy(() -> controller.findOrderByIdOrder(idPedido)).
                isInstanceOf(PedidoException.class).
                hasMessage("Pedido não encontrado!");

//        ResponseEntity<AcompanhamentoResponseDTO> pedidoById = controller.findOrderByIdOrder(idPedido);
//        AcompanhamentoResponseDTO body = pedidoById.getBody();
//
//        /* ASSERT */
//        assertThat(body).isInstanceOf(AcompanhamentoResponseDTO.class);
//        assertThat(body.getPedido().getIdCliente()).isNotZero();
//        assertThat(body.getPedido().getItensPedido()).asList();
//        assertThat(body.getTotal()).isNotZero();
//        assertThat(body).isExactlyInstanceOf(PedidoException.class);
    }
}