/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.gateways;

import br.com.techchallenge.fiap.process.adapter.gateways.AcompanhamentoGateway;
import br.com.techchallenge.fiap.process.adapter.presenter.AcompanhamentoResponse;
import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import br.com.techchallenge.fiap.process.infrastructure.persistence.acompanhamento.AcompanhamentoRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.PedidoRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.entities.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AcompanhamentoPedidoRepositorioGateway implements AcompanhamentoGateway {

    private AcompanhamentoRepository repository;
    private PedidoRepository pedidoRepository;

    public AcompanhamentoPedidoRepositorioGateway(AcompanhamentoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public AcompanhamentoResponseDTO getOrderStatus(Long idPedido) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        AcompanhamentoResponse acompanhamentoResponse = new AcompanhamentoResponse();
        AcompanhamentoResponse acp = acompanhamentoResponse.pedidoEntityFromResponse(pedido.get());
        return acp.pedidoFromResponse();
    }

    @Override
    public void fluxoStatusPedido(Long idPedido, Status status) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        pedido.get().setStatus(status);
        pedidoRepository.save(pedido.get());
    }
}
