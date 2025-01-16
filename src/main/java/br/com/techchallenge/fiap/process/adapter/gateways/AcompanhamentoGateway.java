/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.gateways;

import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;

public interface AcompanhamentoGateway {

    AcompanhamentoResponseDTO getOrderStatus(Long idPedido);
    void fluxoStatusPedido(Long idPedido, Status Status);
}
