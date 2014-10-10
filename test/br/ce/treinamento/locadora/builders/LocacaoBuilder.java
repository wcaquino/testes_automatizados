package br.ce.treinamento.locadora.builders;

import static br.ce.treinamento.locadora.builders.FilmeBuilder.umFilme;
import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;
import static br.ce.treinamento.util.DataUtil.obterDataDiferencaDias;

import java.util.Arrays;
import java.util.Date;

import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;

public class LocacaoBuilder {

	private Locacao locacao;
	
	private LocacaoBuilder() {}
	
	public static LocacaoBuilder umaLocacao(){
		LocacaoBuilder builder = new LocacaoBuilder();
		builder.locacao = new Locacao();
		
		builder.locacao.setUsuario(umUsuario().criar());
		builder.locacao.setFilmes(Arrays.asList(umFilme().criar()));
		builder.locacao.setValor(4d);
		builder.locacao.setDataLocacao(new Date());
		builder.locacao.setDataRetorno(obterDataDiferencaDias(1).getTime());
		
		return builder;
	}
	
	public LocacaoBuilder comUsuario(Usuario usuario) {
		locacao.setUsuario(usuario);
		return this;
	}
	
	public LocacaoBuilder comDataLocacaoDiferencaHoje(int diferenca) {
		locacao.setDataLocacao(obterDataDiferencaDias(diferenca).getTime());
		return this;
	}
	
	public LocacaoBuilder comDataRetornoDiferencaHoje(int diferenca) {
		locacao.setDataRetorno(obterDataDiferencaDias(diferenca).getTime());
		return this;
	}
	
	public Locacao criar(){
		return locacao;
	}
}
