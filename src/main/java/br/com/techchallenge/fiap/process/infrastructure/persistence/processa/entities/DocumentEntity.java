/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.infrastructure.persistence.processa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("prints")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {

    @Id
    @Field
    private Integer id;

    @Field
    private String nome;

    @Field
    private Binary file;

}
