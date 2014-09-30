package br.ce.treinamento.locadora.negocio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
	private List<Filme> filmes;
	
	@Rule
	public ExpectedException excecaoEsperada = ExpectedException.none();
	
	@Before
	public void setup(){
		locadora = new Locadora();
		usuario = new Usuario("Joseh");
		filmes = new ArrayList<Filme>();
	}
	
	@Test
	public void deveTerDataRetornoAmanhaAoAlugarFilme() throws LocadoraException{
		//Cenario
		Filme filme = new Filme("GodFather", 5, 5.0);
		filmes.add(filme);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
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
		filmes.add(filme);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Validacao
		Assert.assertEquals(5.0, locacao.getValor(), 0.001);
	}
	
	@Test
	public void deveReduzirEstoqueAoAlugarFilme() throws LocadoraException {
		//Cenario
		Filme filme = new Filme("E o tempo levou", 3, 1.50);
		filmes.add(filme);
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertEquals(2, locacao.getFilmes().get(0).getEstoque().intValue());
	}
	
	@Test
	public void deveLancarExcecaoQuandoAlugarFilmeComEstoqueMinimo() {
		//Cenario
		Filme filme = new Filme("Matrix", 0, 4.0);
		filmes.add(filme);
		
		//Acao
		try {
			locadora.alugarFilme(usuario, filmes);
			
		//Verificacao
			Assert.fail("Uma excecao deveria ter sido lancada na linha anterior");
		} catch (LocadoraException e) {
			Assert.assertEquals("Nao eh possivel alugar filme que nao estah no estoque", e.getMessage());
		}
	}
	
	@Test
	public void deveLancarExcecaoQuandoAlugarFilmeSemFilmeDefinido() throws LocadoraException{
		//Cenario
		excecaoEsperada.expect(LocadoraException.class);
		excecaoEsperada.expectMessage("O Filme nao pode estar vazio");
		
		//Acao
		locadora.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void deveAdicionarUmDiaNaEntregaAoAlugar4Filmes() throws LocadoraException {
		//Cenario
		Filme filme1 = new Filme("Iron Man", 2, 4.0);
		Filme filme2 = new Filme("Capitao America", 1, 3.0);
		Filme filme3 = new Filme("Thor", 1, 2.50);
		Filme filme4 = new Filme("The Avengers", 10, 5.0);
		filmes = Arrays.asList(filme1, filme2, filme3, filme4);
		
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		Calendar dataEsperada = Calendar.getInstance();
		dataEsperada.add(Calendar.DAY_OF_MONTH, 2);
		
		Calendar dataRetorno = Calendar.getInstance();
		dataRetorno.setTime(locacao.getDataRetorno());
		
		Assert.assertEquals(dataEsperada.get(Calendar.DAY_OF_MONTH), dataRetorno.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void deveDar25PercDescontoAoAlugar4FilmesNaSegunda() throws LocadoraException {
		Filme filme1 = new Filme("Lord of the rings: The Fellowship of the ring", 5, 4.0);
		Filme filme2 = new Filme("Lord of the rings: The Two Towers", 5, 4.0);
		Filme filme3 = new Filme("Lord of the rings: The Return of the King", 5, 4.0);
		Filme filme4 = new Filme("The Hobbit: There and back again", 5, 5.0);
		filmes = Arrays.asList(filme1, filme2, filme3, filme4);
		
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(15.75, locacao.getValor(), 0.001);
	}
	
	@Test
	public void deveDar50PercDescontoAoAlugar5FilmesNaSegunda() throws LocadoraException {
		Filme filme1 = new Filme("Lord of the rings: The Fellowship of the ring", 5, 4.0);
		Filme filme2 = new Filme("Lord of the rings: The Two Towers", 5, 4.0);
		Filme filme3 = new Filme("Lord of the rings: The Return of the King", 5, 4.0);
		Filme filme4 = new Filme("The Hobbit: There and back again", 5, 5.0);
		Filme filme5 = new Filme("The Hobbit: Smaug Desolation", 5, 5.0);
		filmes = Arrays.asList(filme1, filme2, filme3, filme4, filme5);
		
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(18.25, locacao.getValor(), 0.001);
	}
	
	@Test
	public void deveDar75PercDescontoAoAlugar6FilmesNaSegunda() throws LocadoraException {
		Filme filme1 = new Filme("Lord of the rings: The Fellowship of the ring", 5, 4.0);
		Filme filme2 = new Filme("Lord of the rings: The Two Towers", 5, 4.0);
		Filme filme3 = new Filme("Lord of the rings: The Return of the King", 5, 4.0);
		Filme filme4 = new Filme("The Hobbit: There and back again", 5, 5.0);
		Filme filme5 = new Filme("The Hobbit: Smaug Desolation", 5, 5.0);
		Filme filme6 = new Filme("The Hobbit: 5 exercitos", 5, 5.0);
		Filme filme7 = new Filme("Lua de Cristal", 1, 1.0);
		filmes = Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7);
		
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(19.5, locacao.getValor(), 0.001);
	}
}
