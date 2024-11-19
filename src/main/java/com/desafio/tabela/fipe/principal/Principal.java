package com.desafio.tabela.fipe.principal;

import com.desafio.tabela.fipe.records.DadosRecord;
import com.desafio.tabela.fipe.records.ModelosRecord;
import com.desafio.tabela.fipe.records.VeiculoRecord;
import com.desafio.tabela.fipe.service.ConsumirApi;
import com.desafio.tabela.fipe.service.ConverteDados;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    public void Menu(){
        Scanner ler = new Scanner(System.in);
        ConsumirApi api = new ConsumirApi();
        ConverteDados converso = new ConverteDados();

        String linkApi = "https://parallelum.com.br/fipe/api/v1/";
        System.out.println("""
				 Bem vindo a tabela FIPE!
				 Aqui vai encontrar informacoes a respeito do veiculo que procura ;)
				 Informe abaixo.
				 1- Carro
				 2- Moto
				 3- Caminhao
				 """);
        System.out.print("Sua escolha:");
        String resposta = ler.nextLine().toLowerCase();
        if(resposta.contains("carr")){
            String s = linkApi + "carros/marcas";
            linkApi = s;
        }

        if(resposta.contains("mot")){
            String s = linkApi + "motos/marcas";
            linkApi = s;
        }

        if(resposta.contains("camin")){
            String s = linkApi + "caminhoes/marcas";
            linkApi = s;
        }


        String json = api.obterDados(linkApi);
        List<DadosRecord> resultadoMarca = converso.obterLista(json, DadosRecord.class);
        resultadoMarca.stream().sorted(Comparator.comparing(DadosRecord::codigo)).forEach(e -> {
                        String mensagem = MessageFormat.format("""
                            Marca: {0}
                            Codigo: {1}
                            """, e.nome(), e.codigo());
                        System.out.println(mensagem);
                    });

        System.out.print("Informe o codigo da marca: ");
        String codigoMarca = ler.nextLine();
        linkApi = linkApi+"/"+codigoMarca+"/modelos/";
        json = api.obterDados(linkApi);
        ModelosRecord resultadosModelosMarcas = converso.obterDados(json, ModelosRecord.class);
        resultadosModelosMarcas.modelos().stream().forEach(e -> {
            String mensagem = MessageFormat.format("""
                            Modelo: {0}
                            Codigo: {1}
                            """, e.nome(), e.codigo());
            System.out.println(mensagem);
        });

        System.out.print("Informe um trecho do modelo:");
        String trechoModelo = ler.nextLine();
        List<DadosRecord> modelosFiltrados = resultadosModelosMarcas.modelos().stream().filter(e -> e.nome().toLowerCase().contains(trechoModelo)).collect(Collectors.toList());
        modelosFiltrados.forEach(e -> {
           String messagem = MessageFormat.format("""
                   Nome: {0}
                   Codigo: {1}
                   """,e.nome(),e.codigo());
           System.out.println(messagem);
       });

        System.out.print("Informe o codigo do modelo: ");
        String respostaModelo = ler.nextLine();
        linkApi = linkApi + respostaModelo + "/anos/";

        json = api.obterDados(linkApi);
        List<DadosRecord> anos = converso.obterLista(json,DadosRecord.class);

        List<VeiculoRecord> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var endAnos = linkApi+ anos.get(i).codigo();
            json = api.obterDados(endAnos);
            VeiculoRecord  veicTemp = converso.obterDados(json,VeiculoRecord.class);
            veiculos.add(veicTemp);
        }

        System.out.println("Todos os veiculos encontrados");
        veiculos.forEach(System.out::println);

    }
}
