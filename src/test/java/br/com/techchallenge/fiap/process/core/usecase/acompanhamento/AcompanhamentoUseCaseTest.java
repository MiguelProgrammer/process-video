/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.usecase.acompanhamento;

import br.com.techchallenge.fiap.process.adapter.gateways.AcompanhamentoGateway;
import br.com.techchallenge.fiap.process.adapter.gateways.PedidoGateway;
import br.com.techchallenge.fiap.process.core.domain.dto.AcompanhamentoResponseDTO;
import br.com.techchallenge.fiap.process.core.domain.dto.CategoriaDTO;
import br.com.techchallenge.fiap.process.core.domain.dto.StatusPedidoDTO;
import br.com.techchallenge.fiap.process.core.domain.enums.Categoria;
import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import br.com.techchallenge.fiap.process.core.domain.pedido.Pedido;
import br.com.techchallenge.fiap.process.core.usecase.acompanhamento.acompanhachain.status.anemic.AcompanhamentoChainRecebido;
import br.com.techchallenge.fiap.process.infrastructure.gateways.AcompanhamentoPedidoRepositorioGateway;
import br.com.techchallenge.fiap.process.infrastructure.persistence.acompanhamento.AcompanhamentoRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.PedidoRepository;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.entities.ItemEntity;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.entities.PedidoEntity;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AcompanhamentoUseCaseTest {

    @Autowired
    private PedidoGateway pedidoGateway;

    @Autowired
    private AcompanhamentoGateway acompanhamentoGateway;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    private AcompanhamentoPedidoRepositorioGateway repositorioGateway;

    private AcompanhamentoChainRecebido chainRecebido;


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("USECASE - Obtem status do pedido")
    void getOrderStatusExecute() {
        /* ARRANGE */
        PedidoEntity pedido = pedido();

        /* ACT */
        acompanhamentoGateway = new AcompanhamentoPedidoRepositorioGateway(acompanhamentoRepository, pedidoRepository);
        AcompanhamentoResponseDTO orderStatus = acompanhamentoGateway.getOrderStatus(pedido.getId());

        /* ASSERT */
        assertThat(orderStatus).isInstanceOf(AcompanhamentoResponseDTO.class);
        assertThat(orderStatus.getTotal()).isGreaterThan(BigDecimal.ZERO);
        assertThat(orderStatus.getStatus()).isInstanceOfAny(StatusPedidoDTO.class);
        assertThat(orderStatus.getPedido().getId()).isEqualTo(pedido.getId());
        orderStatus.getPedido().getItensPedido().forEach(it -> {
            assertThat(it.getIdPedido()).isEqualTo(pedido.getId());
            assertThat(it.getProduto().getCategoria()).isIn(CategoriaDTO.values());
            assertThat(it.getId()).isNotZero();
        });

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("USECASE - Atualiza status do pedido")
    void pedidoStatusExecute() {

        /* ARRANGE */
        PedidoEntity pedido = pedido();

        /* ACT */

        Pedido byId = pedidoGateway.findById(pedido.getId());

        /* ASSERT */
        assertThat(byId).isInstanceOf(Pedido.class);
        assertThat(byId.getId()).isEqualTo(pedido.getId()); ;
    }

    @Test
    @DisplayName("USECASE - Mensagem sobre andamento do pedido e atendimento")
    void smsExecute() {
        String MESSAGE_RECEBIDO = "______________________________\n" +
                "\n" +
                "Pedido Em preparação.\n" +
                "\n" +
                "Em instantes será concluído. \n" +
                "\n" +
                "Somente aguarde, obrigado.\n" +
                "\n" +
                "______________________________\n" +
                "\n";

        chainRecebido = new AcompanhamentoChainRecebido(acompanhamentoGateway);

        /* ACT */
        String sms = chainRecebido.sms(Status.EM_PREPARACAO);

        /* ASSERT */
        assertThat(sms).isNotBlank();
        assertThat(sms).isEqualTo(MESSAGE_RECEBIDO);
        System.out.println(sms);
    }


    private PedidoEntity pedido(){
        PedidoEntity pedidoDomain = new PedidoEntity();
        pedidoDomain.setIdCliente(1L);
        pedidoDomain.setStatus(Status.RECEBIDO);
        pedidoDomain.setDataPedido(new Date());
        pedidoDomain.setTotal(new BigDecimal(23.90));

        ItemEntity item = new ItemEntity();
        item.setIdProduto(1L);
        item.setIdPedido(1L);
        item.setNome("Batata Frita");
        item.setDescricao("Batata Frita média");
        item.setImg("http://google.com/img");
        item.setPreco(new BigDecimal(7.90));
        item.setCategoria(Categoria.ACOMPANHAMENTO);
        pedidoDomain.setItensProdutos(new HashSet<>(List.of(item)));
        return pedidoRepository.saveAndFlush(pedidoDomain);
    }

}