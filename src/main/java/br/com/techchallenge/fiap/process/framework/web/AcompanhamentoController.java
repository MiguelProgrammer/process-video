/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.framework.web;


import _generated_sources_swagger_acompanhamento.NeighborfoodApi;
import br.com.techchallenge.fiap.process.adapter.controllers.Acompanhamento;
import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcompanhamentoController implements NeighborfoodApi {

    private Acompanhamento acompanhamentoController;

    public AcompanhamentoController(Acompanhamento acompanhamentoController) {
        this.acompanhamentoController = acompanhamentoController;
    }

    /**
     * GET /neighborfood/acompanhamento/{idPedido} : Procura o status de um pedido
     * Retorna o status de um pedido
     *
     * @param idPedido id do pedido (required)
     * @return successful operation (status code 200)
     * or Id inválido (status code 400)
     * or Pedido não encontrado (status code 404)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/neighborfood/acompanhamento/{idPedido}",
            produces = { "application/json" }
    )
    public ResponseEntity<AcompanhamentoResponseDTO> findOrderByIdOrder(@PathVariable("idPedido") Long idPedido) {
        AcompanhamentoResponseDTO orderStatusExecute = acompanhamentoController.statusDoPedido(idPedido);
        return ResponseEntity.ok(orderStatusExecute);
    }

}
