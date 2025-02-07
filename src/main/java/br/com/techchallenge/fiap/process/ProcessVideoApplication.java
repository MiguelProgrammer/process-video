/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class ProcessVideoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ProcessVideoApplication.class, args);
        System.out.println("_______________ - Process Video Application - _______________");

    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadFactory(runnable -> new Thread(runnable, "virtual-thread-" + System.nanoTime()));
        taskExecutor.setCorePoolSize(10); // You can adjust the size depending on your needs
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.initialize();
        return taskExecutor;
    }
}