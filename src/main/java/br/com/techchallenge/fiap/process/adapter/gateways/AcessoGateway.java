package br.com.techchallenge.fiap.process.adapter.gateways;

import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;

public interface AcessoGateway {

    AcompanhamentoResponseDTO getOrderStatus(Long idPedido);
    void fluxoStatusPedido(Long idPedido, Status Status);
}
