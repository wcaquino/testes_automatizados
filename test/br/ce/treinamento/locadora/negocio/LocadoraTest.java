package br.ce.treinamento.locadora.negocio;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

public class LocadoraTest {

	@Test
	public void deveTerDataRetornoAmanhaAoAlugarFilme() throws LocadoraException{
		//Cenario
		Locadora locadora = new Locadora();
		Usuario usuario = new Usuario("Joseh");
		Filme filme = new Filme("GodFather", 5, 5.0);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		//Validacao
		Assert.assertTrue(locacao.getValor().equals(5.0));
		
		Calendar calendarDataRetorno = Calendar.getInstance();
		calendarDataRetorno.setTime(locacao.getDataRetorno());
		
		Assert.assertTrue(calendarDataRetorno.get(Calendar.DAY_OF_MONTH) == 6);
	}
	
	@Test
	public void deveReduzirEstoqueAoAlugarFilme() {
		//Cenario
		Locadora locadora = new Locadora();
		Usuario usuario = new Usuario("Joao");
		Filme filme = new Filme("E o tempo levou", 0, 1.50);
		
		try {
			//Acao
			Locacao locacao = locadora.alugarFilme(usuario, filme);
			
			//Verificacao
			Assert.assertTrue(locacao.getFilme().getEstoque() == 2);
		} catch (LocadoraException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
}
