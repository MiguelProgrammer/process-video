package br.com.techchallenge.fiap.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessVideoApplication.class, args);
        System.out.println("_______________ - Process Video Application - _______________");
    }
}