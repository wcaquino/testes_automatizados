package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

public interface LocacaoDao {

	void salvar(Locacao locacao) throws LocadoraException;

	List<Locacao> obterLocacoesPendentes();
}
