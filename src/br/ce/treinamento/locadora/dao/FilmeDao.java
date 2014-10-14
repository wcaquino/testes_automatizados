package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Filme;

interface FilmeDao {

	Filme save(Filme filme) throws Exception;
	 
    Filme edit(Filme filme) throws Exception;
 
    Filme find(Long filmeId) throws Exception;
 
    void remove(Filme filme) throws Exception;
 
    List<Filme> listALL() throws Exception;
    
    void printAll() throws Exception;
}
