package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

public class LocacaoDaoDummy implements LocacaoDao {

	@Override
	public void salvar(Locacao locacao) throws LocadoraException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() {
		// TODO Auto-generated method stub
		return null;
	}

}
