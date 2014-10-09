package br.ce.treinamento.locadora.negocio;

import br.ce.treinamento.locadora.entidades.Usuario;

public interface EmailService {

	void notificarAtraso(Usuario usuario);

}
