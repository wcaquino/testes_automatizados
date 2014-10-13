package br.ce.treinamento.locadora.negocio;

import static br.ce.treinamento.matchers.MatchersProprios.data;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.ce.treinamento.locadora.builders.LocacaoBuilder;
import br.ce.treinamento.locadora.builders.UsuarioBuilder;
import br.ce.treinamento.locadora.dao.LocacaoDao;
import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exceptions.LocadoraException;
import br.ce.treinamento.util.DataUtil;

public class LocadoraTest {

	@Spy @InjectMocks
	private Locadora locadora;

	@Mock
	private LocacaoDao locacaoDao;
	@Mock
	private SPCService spcService;
	@Mock
	private EmailService emailService;
	@Mock
	private Relogio relogio;
	
	private Usuario usuario;
	private List<Filme> filmes;
	
	@Rule
	public ExpectedException excecaoEsperada = ExpectedException.none();
	
	@Before
	public void setup(){
		usuario = new Usuario("Joseh");
		filmes = new ArrayList<Filme>();
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deveTerDataRetornoAmanhaAoAlugarFilme() throws Exception{
		//Cenario
		Filme filme = new Filme("GodFather", 5, 5.0);
		filmes.add(filme);
		
		Mockito.when(relogio.obterCalendarAtual()).thenReturn(DataUtil.obterCalendar("07/10/2014"));
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Validacao
		assertThat(locacao.getDataRetorno(), is(data("08/10/2014")));
	}
	
	@Test
	public void deveCalcularPrecoAoAlugarFilme() throws Exception{
		//Cenario
		Filme filme = new Filme("GodFather", 5, 5.0);
		filmes.add(filme);
		
		Mockito.when(relogio.obterCalendarAtual()).thenReturn(DataUtil.obterCalendar("07/10/2014"));
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Validacao
		assertThat(locacao.getValor(), is(closeTo(5.0, 0.001)));
	}
	
	@Test
	public void deveReduzirEstoqueAoAlugarFilme() throws Exception {
		//Cenario
		Filme filme = new Filme("E o tempo levou", 3, 1.50);
		filmes.add(filme);
		
		Mockito.when(relogio.obterCalendarAtual()).thenCallRealMethod();
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(locacao.getFilmes().get(0).getEstoque().intValue(), is(2));
	}
	
	@Test
	public void deveLancarExcecaoQuandoAlugarFilmeComEstoqueMinimo() throws Exception {
		//Cenario
		Filme filme = new Filme("Matrix", 0, 4.0);
		filmes.add(filme);
		
		//Acao
		try {
			locadora.alugarFilme(usuario, filmes);
			
		//Verificacao
			fail("Uma excecao deveria ter sido lancada na linha anterior");
		} catch (LocadoraException e) {
//			Assert.assertEquals("Nao eh possivel alugar filme que nao estah no estoque", e.getMessage());
			assertThat(e.getMessage(), is(equalTo("Nao eh possivel alugar filme que nao estah no estoque")));
		}
	}
	
	@Test
	public void deveLancarExcecaoQuandoAlugarFilmeSemFilmeDefinido() throws Exception{
		//Cenario
		excecaoEsperada.expect(LocadoraException.class);
		excecaoEsperada.expectMessage("O Filme nao pode estar vazio");
		
		//Acao
		locadora.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void deveAdicionarUmDiaNaEntregaAoAlugar4Filmes() throws Exception {
		
		//Cenario
		Filme filme1 = new Filme("Iron Man", 2, 4.0);
		Filme filme2 = new Filme("Capitao America", 1, 3.0);
		Filme filme3 = new Filme("Thor", 1, 2.50);
		Filme filme4 = new Filme("The Avengers", 10, 5.0);
		filmes = asList(filme1, filme2, filme3, filme4);
		
		Mockito.when(relogio.obterCalendarAtual()).thenReturn(DataUtil.obterCalendar("07/10/2014"));
		
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		assertThat(locacao.getDataRetorno(), is(data("09/10/2014")));
	}
	
	@Test
	public void deveEntregarFilmeNaSegundaAoAlugar4FilmesNaSexta() throws Exception {
		
		//Cenario
		Filme filme1 = new Filme("Iron Man", 2, 4.0);
		Filme filme2 = new Filme("Capitao America", 1, 3.0);
		Filme filme3 = new Filme("Thor", 1, 2.50);
		Filme filme4 = new Filme("The Avengers", 10, 5.0);
		filmes = asList(filme1, filme2, filme3, filme4);
		
		Mockito.when(relogio.obterCalendarAtual()).thenReturn(DataUtil.obterCalendar("10/10/2014"));
		
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		assertThat(locacao.getDataRetorno(), is(data("13/10/2014")));
	}
	
	@Test
	public void naoDeveAlugarFilmesParaUsuarioComProblemasNoSPC() throws Exception{
		//Cenario
		Filme filme = new Filme("Truque de mestre", 5, 4.0);
		filmes = Arrays.asList(filme);
		
		Mockito.when(spcService.obterDebito(usuario)).thenReturn(true);
		
		//Acao
		try {
			locadora.alugarFilme(usuario, filmes);
			Assert.fail();
			//Verificacao
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is(equalTo("Usuario com pendencia no SPC")));
		}
		
	}

	@Test
	public void deveAlugarFilmesParaUsuarioSemProblemasNoSPC() throws Exception{
		//Cenario
		Filme filme = new Filme("Truque de mestre", 5, 4.0);
		filmes = Arrays.asList(filme);
		
		Mockito.when(spcService.obterDebito(usuario)).thenReturn(false);
		Mockito.when(relogio.obterCalendarAtual()).thenReturn(DataUtil.obterCalendar("07/10/2014"));
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao, is(not(nullValue())));
	}
	
	@Test
	public void deveLancarExcecaoAoAlugarFilmesParaUsuarioSemCPF() throws Exception{
		//Cenario
		Filme filme = new Filme("Truque de mestre", 5, 4.0);
		filmes = Arrays.asList(filme);
		
		Mockito.when(spcService.obterDebito(usuario)).thenThrow(new IllegalArgumentException("CPF vazio"));

		try {
			//Acao
			locadora.alugarFilme(usuario, filmes);
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("CPF vazio"));
		}

		Mockito.verifyZeroInteractions(locacaoDao);
	}
	
	@Test
	public void deveLancarExcecaoAoAlugarFilmesQuandoErroGeralNoSPC() throws Exception{
		//Cenario
		Filme filme = new Filme("Truque de mestre", 5, 4.0);
		filmes = Arrays.asList(filme);
		
		Mockito.when(spcService.obterDebito(usuario)).thenThrow(new Exception("Falha catastrofica"));
		
		try {
			//Acao
			locadora.alugarFilme(usuario, filmes);
		} catch(Exception e) {
			assertThat(e.getMessage(), is("Falha catastrofica"));
		}
		
		Mockito.verifyZeroInteractions(locacaoDao);
	}
	
	@Test
	public void deveNotificarUsuariosComLocacoesAtrasadas(){
		Locacao locacao = LocacaoBuilder.umaLocacao()
					.comUsuario(UsuarioBuilder.umUsuario().comNome("Jose").criar())
					.comDataLocacaoDiferencaHoje(-2)
					.comDataRetornoDiferencaHoje(-1)
				.criar(); 
		
		Locacao locacao2 = LocacaoBuilder.umaLocacao()
				.comUsuario(UsuarioBuilder.umUsuario().comNome("Maria").criar())
				.comDataLocacaoDiferencaHoje(-3)
				.comDataRetornoDiferencaHoje(-2)
			.criar(); 

		Locacao locacao3 = LocacaoBuilder.umaLocacao()
				.comUsuario(UsuarioBuilder.umUsuario().comNome("Josefa").criar())
				.comDataLocacaoDiferencaHoje(-1)
				.comDataRetornoDiferencaHoje(1)
			.criar(); 
		
		Locacao locacao4 = LocacaoBuilder.umaLocacao()
				.comUsuario(UsuarioBuilder.umUsuario().comNome("Jose").criar())
				.comDataLocacaoDiferencaHoje(-4)
				.comDataRetornoDiferencaHoje(-2)
			.criar(); 
		
		List<Locacao> locacoes = Arrays.asList(locacao, locacao2, locacao3, locacao4);
		
		Mockito.when(locacaoDao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		locadora.notificarAtrasados();
		
		Mockito.verify(emailService, times(2)).notificarAtraso(new Usuario("Jose"));
		Mockito.verify(emailService).notificarAtraso(new Usuario("Maria"));
		Mockito.verify(emailService, never()).notificarAtraso(new Usuario("Josefa"));
		
	}
	
	@Test
	public void deveCobrarZeroAoAlugarUsandoFidelidade() throws LocadoraException{
		Filme filme = new Filme("Truque de mestre", 5, 4.0);
		Filme filme2 = new Filme("The prestige", 0, 4.0);
		filmes = Arrays.asList(filme, filme2);
		
		Mockito.doNothing().when(locadora).validarCamposObrigatorios(usuario, filmes);
//		Mockito.when(locadora.calcularDataEntrega(filmes)).thenReturn(DataUtil.obterDataDiferencaDias(-1));
		Mockito.doReturn(DataUtil.obterDataDiferencaDias(-1)).when(locadora).calcularDataEntrega(filmes);
		
		locadora.LiberarAluguelComFidelidade(usuario, filmes);
		
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(locacaoDao).salvar(argCapt.capture());
		Locacao retorno = argCapt.getValue();
		
		assertThat(retorno.getUsuario(), is(usuario));
		assertThat(retorno.getValor(), is(0d));
		assertThat(retorno.getFilmes(), hasSize(2));
		assertThat(retorno.getFilmes(), hasItems(filme2, filme));
		assertThat(retorno.getFilmes(), IsIterableContainingInOrder.contains(filme, filme2));
	}
}
