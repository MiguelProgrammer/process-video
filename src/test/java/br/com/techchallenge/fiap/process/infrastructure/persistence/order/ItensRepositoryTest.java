/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.persistence.order;

import br.com.techchallenge.fiap.process.core.domain.enums.Categoria;
import br.com.techchallenge.fiap.process.infrastructure.persistence.order.entities.ItemEntity;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ItensRepositoryTest {

    private AutoCloseable autoCloseable;

    @Mock
    private ItensRepository itensRepository;
    private ItemEntity itemEntity;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        itemEntity = new ItemEntity();
        itemEntity.setIdPedido(1L);
        itemEntity.setIdProduto(2L);
        itemEntity.setCategoria(Categoria.ACOMPANHAMENTO);
        itemEntity.setImg("http://google.com/im/test");
        itemEntity.setDescricao("Batata fritas média");
        itemEntity.setNome("Batata Frita");
        itemEntity.setPreco(new BigDecimal(7.00));
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("Salva um item")
    void save() {

        /* ARRANGE */
        when(itensRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);

        /* ACT */
        var itemSalvo = itensRepository.save(itemEntity);

        /* ASSERT */
        assertThat(itemSalvo).isNotNull().isInstanceOf(ItemEntity.class);
        assertThat(itemSalvo.getPreco()).isGreaterThan(BigDecimal.ZERO);
        assertThat(itemSalvo.getImg()).contains("http");
        assertThat(itemSalvo.getCategoria().equals(itemSalvo.getCategoria())).isTrue();
        verify(itensRepository, times(1)).save(any(ItemEntity.class));
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("Busca um item via id")
    void findByIdPedido() {

        /* ARRANGE */
        when(itensRepository.findByIdPedido(anyLong())).thenReturn(new HashSet<>(new ArrayList<>()));

        /* ACT */
        var itemDaBase = itensRepository.findByIdPedido(itemEntity.getIdPedido());

        /* ASSERT */
        itemDaBase.forEach(it -> {
            assertThat(it).isNotNull().isInstanceOf(ItemEntity.class);
            assertThat(it.getPreco()).isGreaterThan(BigDecimal.ZERO);
            assertThat(it.getImg()).contains("http");
            assertThat(it.getCategoria()).isIn(Arrays.stream(Categoria.values()).count());
        });
        verify(itensRepository, times(1)).findByIdPedido(anyLong());
    }


    @AfterEach
    void tearDown() {
    }
}