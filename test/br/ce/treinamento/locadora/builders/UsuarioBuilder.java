package br.ce.treinamento.locadora.builders;

import br.ce.treinamento.locadora.entidades.Usuario;

public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder umUsuario(){
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario("Jose");
		builder.usuario.setCpf(12345678L);
		builder.usuario.setEmail("joseMaria@email.com");
		return builder;
	}
	
	public UsuarioBuilder comId(Long id) {
		usuario.setId(id);
		return this;
	}
	
	public UsuarioBuilder comNome(String nome) {
		usuario.setNome(nome);
		return this;
	}
	
	public UsuarioBuilder comEmail(String email) {
		usuario.setEmail(email);
		return this;
	}
	
	public UsuarioBuilder comCpf(Long cpf) {
		usuario.setCpf(cpf);
		return this;
	}
	
	public Usuario criar(){
		return usuario;
	}
}
