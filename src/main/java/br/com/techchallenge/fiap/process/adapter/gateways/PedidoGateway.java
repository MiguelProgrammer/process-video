/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.gateways;

import br.com.techchallenge.fiap.process.core.domain.pedido.Pedido;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.entities.PedidoEntity;

public interface PedidoGateway {

    Pedido findById(Long id);
    Pedido update(PedidoEntity pedidoEntity);
}
