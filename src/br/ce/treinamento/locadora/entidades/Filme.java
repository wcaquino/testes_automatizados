package br.ce.treinamento.locadora.entidades;

public class Filme {

	private String nome;
	private Integer estoque;
	private Double precoLocacao;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public Double getPrecoLocacao() {
		return precoLocacao;
	}
	public void setPrecoLocacao(Double precoLocacao) {
		this.precoLocacao = precoLocacao;
	}
}
