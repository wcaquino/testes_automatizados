package br.ce.treinamento.locadora.builders;

import br.ce.treinamento.locadora.entidades.Filme;

public class FilmeBuilder {

	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder umFilme(){
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setId(1L);
		builder.filme.setNome("Senhor dos aneis");
		builder.filme.setPrecoLocacao(4d);
		builder.filme.setEstoque(5);
		
		return builder;
	}
	
	public Filme criar(){
		return filme;
	}
}
