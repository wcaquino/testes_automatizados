package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Filme;

public interface FilmeDao {

	public Filme save(Filme filme) throws Exception;
	 
    public Filme edit(Filme filme) throws Exception;
 
    public Filme find(Long filmeId) throws Exception;
 
    public void remove(Filme filme) throws Exception;
 
    public List<Filme> listALL() throws Exception;
}
