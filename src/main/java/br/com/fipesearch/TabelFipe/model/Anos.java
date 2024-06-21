package br.com.fipesearch.TabelFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Anos(String codigo,
                   String nome) {
}
