package br.ce.treinamento.locadora.negocio;

import org.junit.Assert;
import org.junit.Test;

import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exception.LocadoraException;

public class LocadoraTest {

	@Test
	public void testarAlugarFilme() throws LocadoraException{
		Usuario usuario = new Usuario("José");
		Filme filme = new Filme("Senhor dos aneis", 2, 5.0);
		
		Locadora locadora = new Locadora();
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		Assert.assertEquals(5.0, locacao.getValor(), 0.000001);
		Assert.assertEquals(3, locacao.getDataRetorno().getDate());
	}
	
	@Test
	public void deveReduzirEstoqueAoAlugarUmFilme() throws LocadoraException{
		Usuario usuario = new Usuario("José");
		Filme filme = new Filme("Butterfly effect", 5, 2.50);
		
		Locadora locadora = new Locadora();
		Locacao locacao = locadora.alugarFilme(usuario, filme);
		
		Assert.assertEquals(Integer.valueOf(4), locacao.getFilme().getEstoque());
	}
	
	@Test(expected=LocadoraException.class)
	public void deveDarExcecaoAoAlugarFilmeSemEstoque() throws LocadoraException{
		Usuario usuario = new Usuario("José");
		Filme filme = new Filme("Butterfly effect", 0, 2.50);
		
		Locadora locadora = new Locadora();
		Locacao locacao = locadora.alugarFilme(usuario, filme);
	}
}
