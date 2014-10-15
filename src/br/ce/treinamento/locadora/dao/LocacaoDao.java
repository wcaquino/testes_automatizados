package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Locacao;

public interface LocacaoDao {

	public void save(Locacao locacao) throws Exception;
	 
    public void edit(Locacao locacao) throws Exception;
 
    public Locacao find(Long locacaoId) throws Exception;
 
    public void remove(Locacao locacao) throws Exception;
 
    public List<Locacao> listALL() throws Exception;

    public List<Locacao> obterLocacoesPendentes() throws Exception;

	List<Locacao> obterLocacoesAtrasados() throws Exception;
}
