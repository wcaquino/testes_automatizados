package br.ce.treinamento.testes.acoes.entidades;

public class Carteira {

	private Acao acao;
	private Long quantidade;
	private Double precoMedioCompra;

	public Acao getAcao() {
		return acao;
	}
	
	public void setAcao(Acao acao) {
		this.acao = acao;
	}
	
	public Long getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double getPrecoMedioCompra() {
		return precoMedioCompra;
	}
	
	public void setPrecoMedioCompra(Double precoMedioCompra) {
		this.precoMedioCompra = precoMedioCompra;
	}
}
