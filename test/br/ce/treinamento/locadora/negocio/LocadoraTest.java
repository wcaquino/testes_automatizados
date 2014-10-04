package br.ce.treinamento.locadora.negocio;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

public class LocadoraTest {

	private Locadora locadora;
	private Usuario usuario;
	
	@Rule
	public ExpectedException excecaoEsperada = ExpectedException.none();
	
	@Before
	public void setup(){
		locadora = new Locadora();
		usuario = new Usuario("Joseh");
	}
	
	@Test
	public void deveTerDataRetornoAmanhaAoAlugarFilme() throws LocadoraException{
		//Cenario
		Filme filme = new Filme("GodFather", 5, 5.0);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		//Validacao
		Calendar calendarDataRetorno = Calendar.getInstance();
		calendarDataRetorno.setTime(locacao.getDataRetorno());
		
		Calendar dataRetornoEsperada = Calendar.getInstance();
		dataRetornoEsperada.add(Calendar.DAY_OF_MONTH, 1);
		
		Assert.assertEquals(calendarDataRetorno.get(Calendar.DAY_OF_MONTH), dataRetornoEsperada.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void deveCalcularPrecoAoAlugarFilme() throws LocadoraException{
		//Cenario
		Filme filme = new Filme("GodFather", 5, 5.0);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		//Validacao
		Assert.assertEquals(5.0, locacao.getValor(), 0.001);
	}
	
	@Test
	public void deveReduzirEstoqueAoAlugarFilme() throws LocadoraException {
		//Cenario
		Filme filme = new Filme("E o tempo levou", 3, 1.50);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		//Verificacao
		Assert.assertEquals(2, locacao.getFilme().getEstoque().intValue());
	}
	
	@Test
	public void deveLancarExcecaoQuandoAlugarFilmeComEstoqueMinimo() {
		//Cenario
		Filme filme = new Filme("Matrix", 0, 4.0);
		
		//Acao
		try {
			locadora.alugarFilme(usuario, filme);
			
		//Verificacao
			Assert.fail("Uma excecao deveria ter sido lancada na linha anterior");
		} catch (LocadoraException e) {
			Assert.assertEquals("Nao eh possivel alugar filme que nao estah no estoque", e.getMessage());
		}
	}
	
	@Test
	public void deveLancarExcecaoQuandoAlugarFilmeComFilmeNulo() throws LocadoraException{
		//Cenario
		Filme filme = null;
		excecaoEsperada.expect(LocadoraException.class);
		excecaoEsperada.expectMessage("O Filme nao pode estar vazio");
		
		//Acao
		locadora.alugarFilme(usuario, filme);
	}
}
