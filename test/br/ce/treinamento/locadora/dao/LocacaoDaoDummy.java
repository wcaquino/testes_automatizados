package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Locacao;

public class LocacaoDaoDummy implements LocacaoDao {

	@Override
	public void save(Locacao locacao) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void edit(Locacao locacao) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Locacao find(Long locacaoId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Locacao locacao) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Locacao> listALL() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Locacao> obterLocacoesAtrasados() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
