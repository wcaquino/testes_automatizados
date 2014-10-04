package br.ce.treinamento.locadora.negocio;

import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;

public class LocadoraTest {

	public void testarAluguel(){
		//Cenario
		Locadora locadora = new Locadora();
		Usuario usuario = new Usuario("Joseh");
		Filme filme = new Filme("GodFather", 5, 5.0);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		//Validacao
		System.out.println(locacao.getValor());
		System.out.println(locacao.getDataLocacao());
		System.out.println(locacao.getDataRetorno());
		
		System.out.println(locacao.getValor().equals(5.0)? "Ok!" : "Erro no valor da locacao"); 
		System.out.println(locacao.getDataLocacao().getDate() == 4? "Ok!" : "Erro na data da locacao"); 
		System.out.println(locacao.getDataRetorno().getDate() == 6? "Ok!" : "Erro na data do retorno da locacao"); 
	}
	
	public static void main(String[] args) {
		new LocadoraTest().testarAluguel();
	}
}
