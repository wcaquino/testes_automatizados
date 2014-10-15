package br.ce.treinamento.locadora.negocio;

import static br.ce.treinamento.locadora.builders.FilmeBuilder.umFilme;
import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;
import static br.ce.treinamento.matchers.MatchersProprios.data;
import static br.ce.treinamento.matchers.MatchersProprios.mesmoDiaQue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.treinamento.locadora.dao.LocacaoDao;
import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.util.DataUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Locadora.class)
public class LocadoraTestPowerMock {

	@InjectMocks
	private Locadora locadora;
	
	@Mock
	private LocacaoDao locacaoDao;
	@Mock
	private SPCService spcService;
	@Mock
	private EmailService emailService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		locadora = PowerMockito.spy(locadora);
		
	}
	
	@Test
	@Ignore
	public void deveRetornarNaQuartaAoAlugarUmFilmeNaTerca() throws Exception{
		//Cenario
		Usuario usuario = umUsuario().criar();
		List<Filme> filmes = Arrays.asList(umFilme().criar());
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtil.obterData("07/10/2014"));
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(locacao.getDataRetorno(), is(data("08/10/2014")));
		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
	}
	
	@Test
	@Ignore
	public void deveRetornarNaSegundaAoAlugarQuatriFilmesNaSexta() throws Exception{
		//Cenario
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DataUtil.obterData("10/10/2014"));
		
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		Usuario usuario = umUsuario().criar();
		List<Filme> filmes = Arrays.asList(umFilme().criar(), umFilme().criar(), umFilme().criar(), umFilme().criar());
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(locacao.getDataRetorno(), is(data("13/10/2014")));
		
		PowerMockito.verifyStatic(Mockito.times(2));
		Calendar.getInstance();
	}
	
	@Test
	@Ignore
	public void deveEntregarHojeAoMockarParaEntregarHoje() throws Exception{
		//Cenario
		Usuario usuario = umUsuario().criar();
		List<Filme> filmes = Arrays.asList(umFilme().criar());
		
		PowerMockito.when(locadora.calcularDataEntrega(filmes)).thenReturn(Calendar.getInstance());
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		Calendar dataRetorno = Calendar.getInstance();
		dataRetorno.setTime(locacao.getDataRetorno());
		assertThat(dataRetorno, is(mesmoDiaQue(Calendar.getInstance())));
		
		Mockito.verify(locadora).calcularDataEntrega(filmes);
	}
	
	@Test
	@Ignore
	public void deveRetornarOValorQueOPowerMockMandarRetornar() throws Exception{
		//Cenario
		Usuario usuario = umUsuario().criar();
		List<Filme> filmes = Arrays.asList(umFilme().criar());
		
		PowerMockito.doReturn(1.0).when(locadora, "calcularValorLocacao", Mockito.any(List.class), Mockito.any(Calendar.class));
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(locacao.getValor(), is(1.0));
		
		PowerMockito.verifyPrivate(locadora).invoke( "calcularValorLocacao", Mockito.any(List.class), Mockito.any(Calendar.class));
	}
	
	@Test
	public void deveCalcularOValorDaLocacaoConsiderandoTodosOsFilmes() throws Exception{
		List<Filme> filmes = Arrays.asList(umFilme().criar());
		Calendar calendar = Calendar.getInstance();
		
		Double valor = (Double) Whitebox.invokeMethod(locadora, "calcularValorLocacao", filmes, calendar);
		
		assertThat(valor, is(4.0));
	}
}
