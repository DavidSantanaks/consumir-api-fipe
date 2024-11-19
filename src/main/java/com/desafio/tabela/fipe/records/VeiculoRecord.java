package com.desafio.tabela.fipe.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VeiculoRecord(
                            @JsonAlias("Valor")String valor,
                            @JsonAlias("Marca")String marca,
                            @JsonAlias("Modelo")String modelo,
                            @JsonAlias("AnoModelo")String anoModelo,
                            @JsonAlias("Combustivel")String combustivel,
                            @JsonAlias("CodigoFipe")String codigoFipe,
                            @JsonAlias("MesReferencia")String mesReferencia) {
}
