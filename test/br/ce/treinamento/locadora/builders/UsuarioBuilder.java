package br.ce.treinamento.locadora.builders;

import br.ce.treinamento.locadora.entidades.Usuario;

public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder umUsuario(){
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario("Jose");
		builder.usuario.setCpf(123456L);
		builder.usuario.setEmail("jose@email.com");
		return builder;
	}
	
	public UsuarioBuilder comNome(String nome) {
		usuario.setNome(nome);
		return this;
	}
	
	public Usuario criar(){
		return usuario;
	}
}
