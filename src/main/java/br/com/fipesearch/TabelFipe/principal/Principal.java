package br.com.fipesearch.TabelFipe.principal;

import br.com.fipesearch.TabelFipe.model.Anos;
import br.com.fipesearch.TabelFipe.model.ConsumoAPI;
import br.com.fipesearch.TabelFipe.model.Marcas;
import br.com.fipesearch.TabelFipe.model.Modelos;
import br.com.fipesearch.TabelFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;


public class Principal {

    private Scanner leitura = new Scanner(System.in);

    final String API_FIPE = "https://parallelum.com.br/fipe/api/v1/";
    final String MARCAS = "/marcas";
    private String tipoVeiculo;
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();

        String endereco = "";
        if(opcao.contains("carr")) {
            tipoVeiculo = "carros";
        }else if(opcao.contains("mot")) {
            tipoVeiculo = "motos";
        }else if(opcao.contains("caminh")) {
            tipoVeiculo = "caminhoes";
        }
        endereco = API_FIPE + tipoVeiculo + MARCAS;

        System.out.println(endereco);

        String json = consumo.obterDados(endereco);
//        System.out.println("json marcas: " + json);
        var marcas = conversor.obterLista(json, Marcas.class);
        marcas.stream()
                .sorted(Comparator.comparing(Marcas::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca:");
        opcao = leitura.nextLine();

        endereco = API_FIPE + tipoVeiculo + MARCAS + "/" + opcao + "/modelos";
        json = consumo.obterDados(endereco);
//        System.out.println("json: " + json);

        var modeloLista = conversor.obterDados(json, Modelos.class);
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Marcas::codigo))
                .forEach(System.out::println);

        System.out.println("Escolha o código do modelo desejado:");
        opcao = leitura.nextLine();

        endereco = endereco += "/" + opcao + "/anos";
        json = consumo.obterDados(endereco);
//        System.out.println("json: " + json);

        var anos = conversor.obterLista(json, Anos.class);
        anos.stream()
                .sorted(Comparator.comparing(Anos::codigo))
                .forEach(System.out::println);
    }
}
