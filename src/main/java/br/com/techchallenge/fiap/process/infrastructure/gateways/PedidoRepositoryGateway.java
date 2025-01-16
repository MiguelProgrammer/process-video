/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.gateways;

import br.com.techchallenge.fiap.process.adapter.gateways.PedidoGateway;
import br.com.techchallenge.fiap.process.core.domain.pedido.Pedido;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.ItensRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.PedidoRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.entities.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PedidoRepositoryGateway implements PedidoGateway {

    private final ItensRepository itensRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoRepositoryGateway(ItensRepository itensRepository, PedidoRepository pedidoRepository) {
        this.itensRepository = itensRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido findById(Long id) {
        Optional<PedidoEntity> pedidoById = pedidoRepository.findById(id);
        PedidoEntity pedidoEntity = new PedidoEntity();
        if(pedidoById.isPresent()) {
            pedidoEntity = pedidoById.get();
            pedidoEntity.setItensProdutos(itensRepository.findByIdPedido(pedidoEntity.getId()));
        }
        return new Pedido().entityFromDomain(pedidoEntity);
    }

    /**
     * @param pedidoEntity
     * @return
     */
    @Override
    public Pedido update(PedidoEntity pedidoEntity) {
       return new Pedido().entityFromDomain(pedidoRepository.saveAndFlush(pedidoEntity));
    }
}
