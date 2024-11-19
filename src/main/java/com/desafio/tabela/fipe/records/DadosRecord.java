package com.desafio.tabela.fipe.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosRecord(@JsonAlias("nome") String nome,
                          @JsonAlias("codigo") String  codigo) {
}
