package br.ce.treinamento.locadora.builders;

import br.ce.treinamento.locadora.entidades.Filme;

public class FilmeBuilder {

	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder umFilme(){
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme("Senhor dos aneis", 10, 4.0);
		return builder;
	}
	
	public Filme criar(){
		return filme;
	}
}
