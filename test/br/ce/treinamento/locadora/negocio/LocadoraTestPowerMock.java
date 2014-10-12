package br.ce.treinamento.locadora.negocio;

import static br.ce.treinamento.locadora.builders.FilmeBuilder.umFilme;
import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;
import static br.ce.treinamento.matchers.MatchersProprios.data;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
	}
	
	@Test
	public void deveRetornarNaQuartaAoAlugarUmFilmeNaTerca() throws Exception{
		//Cenario
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtil.obterData("07/10/2014"));
		Usuario usuario = umUsuario().criar();
		List<Filme> filmes = Arrays.asList(umFilme().criar());
		
		//Acao
		Locacao locacao = locadora.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(locacao.getDataRetorno(), is(data("08/10/2014")));
	}
}
