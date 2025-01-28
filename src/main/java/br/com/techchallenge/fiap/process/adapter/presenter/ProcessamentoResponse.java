/*
 * Copyright (c) 2024. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.adapter.presenter;

import org.apache.http.HttpStatus;

public class ProcessamentoResponse {

    private HttpStatus status;

    public ProcessamentoResponse(int scNoContent) {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(int scCreated) {

    }
}
