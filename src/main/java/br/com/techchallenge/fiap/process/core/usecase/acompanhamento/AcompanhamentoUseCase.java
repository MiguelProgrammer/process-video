/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.acompanhamento;

import br.com.techchallenge.fiap.process.adapter.gateways.AcompanhamentoGateway;
import br.com.techchallenge.fiap.process.adapter.gateways.PedidoGateway;
import br.com.techchallenge.fiap.process.adapter.presenter.AcompanhamentoResponse;
import br.com.techchallenge.fiap.process.config.exceptions.PedidoException;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import br.com.techchallenge.fiap.process.core.domain.pedido.Pedido;
import br.com.techchallenge.fiap.process.core.usecase.acompanhamento.acompanhachain.status.anemic.AcompanhamentoChainRecebido;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AcompanhamentoUseCase {

    private PedidoGateway pedidoGateway;
    private AcompanhamentoGateway acompanhamentoGateway;

    public AcompanhamentoUseCase(PedidoGateway pedidoGateway, AcompanhamentoGateway acompanhamentoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.acompanhamentoGateway = acompanhamentoGateway;
    }

    public AcompanhamentoResponse getOrderStatusExecute(Long idPedido) {

        Pedido pedido = new Pedido();
        try {
            pedido = pedidoGateway.findById(idPedido);

            if (pedido.getStatus().equals(Status.RECEBIDO)) {
                this.pedidoStatusExecute(idPedido, Status.EM_PREPARACAO);
                pedido.setStatus(Status.EM_PREPARACAO);

                return new AcompanhamentoResponse().pedidoEntityFromResponse(pedidoGateway.findById(pedido.getId()).domainFromEntity());
            } else if (pedido.getStatus().equals(Status.EM_PREPARACAO)) {
                this.pedidoStatusExecute(idPedido, Status.PRONTO);
                pedido.setStatus(Status.PRONTO);

                return new AcompanhamentoResponse().pedidoEntityFromResponse(pedidoGateway.findById(pedido.getId()).domainFromEntity());
            } else if (pedido.getStatus().equals(Status.PRONTO)) {
                this.fluxoStatusPedidoExecute(idPedido, Status.FINALIZADO);
                pedido.setStatus(Status.FINALIZADO);

                return new AcompanhamentoResponse().pedidoEntityFromResponse(pedidoGateway.findById(pedido.getId()).domainFromEntity());
            }
        } catch (Exception ex) {
            throw new PedidoException("Pedido não encontrado!");
        }
        return new AcompanhamentoResponse().pedidoEntityFromResponse(pedidoGateway.findById(pedido.getId()).domainFromEntity());
    }

    private void fluxoStatusPedidoExecute(Long idPedido, Status status) {
        Pedido pedidoDTO = pedidoGateway.findById(idPedido);
        pedidoDTO.setStatus(status);
        if (pedidoDTO.getStatus().equals(Status.FINALIZADO)) {
            pedidoDTO.setDataPedidoFim(new Date());
        }
        pedidoGateway.update(pedidoDTO.domainFromEntity());
        System.out.println(this.smsExecute(pedidoDTO.getStatus()));
    }

    private void pedidoStatusExecute(Long idPedido, Status status) {
        Pedido pedidoDTO = pedidoGateway.findById(idPedido);
        pedidoDTO.setStatus(status);
        Pedido pedidoDTO1 = pedidoGateway.update(pedidoDTO.domainFromEntity());
        System.out.println(this.smsExecute(pedidoDTO1.getStatus()));
    }

    private String smsExecute(Status status) {
        return new AcompanhamentoChainRecebido().sms(status);
    }

}
