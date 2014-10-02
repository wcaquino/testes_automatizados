package br.ce.treinamento.testes.acoes.negocio;

import br.ce.treinamento.testes.acoes.entidades.Acao;
import br.ce.treinamento.testes.acoes.entidades.Carteira;
import br.ce.treinamento.testes.acoes.entidades.Investidor;

public class Broker {

	public Double obterCapital(Investidor investidor){
		Double capital = investidor.getSaldo();
		
		Carteira carteira = investidor.getCarteira();
		Double valorAcao = carteira.getPrecoMedioCompra() * carteira.getQuantidade();
		
		return capital + valorAcao;
	}
	
	public void comprarAcao(Investidor comprador, Investidor vendedor, Acao acao, Long quantidade){
		Double valorNegociado = acao.getPrecoAtual() * quantidade;
		
		Carteira carteiraComprador = comprador.getCarteira();
		Carteira carteiraVendedor = vendedor.getCarteira();
		
		//Atualizando quantidades
		carteiraComprador.setQuantidade(carteiraComprador.getQuantidade() + quantidade);
		carteiraVendedor.setQuantidade(carteiraVendedor.getQuantidade() - quantidade);
		
		//Realizando o pagamento
		comprador.setSaldo(comprador.getSaldo() - valorNegociado);
		vendedor.setSaldo(vendedor.getSaldo() + valorNegociado);
	}
}
