package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Usuario;

interface UsuarioDao {

    Usuario save(Usuario Usuario) throws Exception;
 
    Usuario edit(Usuario Usuario) throws Exception;
 
    Usuario find(Long UsuarioId) throws Exception;
 
    void remove(Usuario Usuario) throws Exception;
 
    List<Usuario> listALL() throws Exception;
    
    void printAll() throws Exception;
}
