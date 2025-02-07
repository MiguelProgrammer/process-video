/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.core.domain.document;

import br.com.techchallenge.fiap.process.core.domain.enums.Status;
import org.bson.types.Binary;

public class Document {

    private Integer id;
    private String nome;
    private Binary file;
    private Status status;

    public Document() {
        this.status = Status.RECEBIDO;
    }

    public Document(Integer id, String nome, Binary file) {
        this.id = id;
        this.nome = nome;
        this.file = file;
        this.status = Status.RECEBIDO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Binary getFile() {
        return file;
    }

    public void setFile(Binary file) {
        this.file = file;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
