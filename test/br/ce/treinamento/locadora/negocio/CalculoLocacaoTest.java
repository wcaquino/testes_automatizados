package br.ce.treinamento.locadora.negocio;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoLocacaoTest {

	private String cenario;
	private List<Filme> filmes;
	private Double valor;
	private Locadora locadora;
	
	private static Filme filme1 = new Filme("Lord of the rings: The Fellowship of the ring", 5, 4.0);
	private static Filme filme2 = new Filme("Lord of the rings: The Two Towers", 5, 4.0);
	private static Filme filme3 = new Filme("Lord of the rings: The Return of the King", 5, 4.0);
	private static Filme filme4 = new Filme("The Hobbit: There and back again", 5, 5.0);
	private static Filme filme5 = new Filme("The Hobbit: Smaug Desolation", 5, 5.0);
	private static Filme filme6 = new Filme("The Hobbit: 5 exercitos", 5, 5.0);
	private static Filme filme7 = new Filme("Lua de Cristal", 1, 1.0);
	
	@Parameters(name= "{index}: Valor da locacao para {0}={2}")
	public static Collection<Object[]> taxasFilmes(){
		return Arrays.asList(new Object[][] {
		         { "4 Filmes", Arrays.asList(filme1, filme2, filme3, filme4), 15.75 },
		         { "5 Filmes", Arrays.asList(filme1, filme2, filme3, filme4, filme5), 18.25 },
		         { "6 Filmes", Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 19.5 },
		         { "7 Filmes", Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 19.5 },
		      });
	}
	
	@Before
	public void setup(){
		locadora = new Locadora();
	}
	
	public CalculoLocacaoTest(String cenario, List<Filme> filmes, Double valor) {
		this.filmes = filmes;
		this.valor = valor;
		this.cenario = cenario;
	}

	@Test
	public void testarValoresDeLocacao() throws LocadoraException{
		Locacao locacao = locadora.alugarFilme(new Usuario("Maria"), filmes);
		Assert.assertEquals(cenario, valor, locacao.getValor(), 0.001);
	}
}
