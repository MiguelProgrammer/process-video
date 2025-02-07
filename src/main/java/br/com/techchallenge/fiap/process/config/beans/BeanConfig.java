package br.com.techchallenge.fiap.process.config.beans;
/*
 * Copyright (c) 2024. MiguelProgrammer
 */

import br.com.techchallenge.fiap.process.adapter.gateways.ProcessamentoGateway;
import br.com.techchallenge.fiap.process.core.usecase.processamento.ProcessamentoUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class BeanConfig {

    @Bean
    public ProcessamentoUseCase configBeanAcompanhamento(ProcessamentoGateway processamentoGateway,
                                                         @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        return new ProcessamentoUseCase(processamentoGateway, taskExecutor);
    }
    
}
