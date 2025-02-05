package br.com.techchallenge.fiap.process.config.beans;
/*
 * Copyright (c) 2024. MiguelProgrammer
 */

import br.com.techchallenge.fiap.process.adapter.gateways.ProcessamentoGateway;
import br.com.techchallenge.fiap.process.core.usecase.processamento.ProcessamentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ProcessamentoUseCase configBeanAcompanhamento(ProcessamentoGateway processamentoGateway) {
        return new ProcessamentoUseCase(processamentoGateway);
    }

}
