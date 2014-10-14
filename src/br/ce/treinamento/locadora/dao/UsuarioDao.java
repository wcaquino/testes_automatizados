package br.ce.treinamento.locadora.dao;

import java.util.List;

import br.ce.treinamento.locadora.entidades.Usuario;

public interface UsuarioDao {

    public Usuario save(Usuario Usuario) throws Exception;
 
    public Usuario edit(Usuario Usuario) throws Exception;
 
    public Usuario find(Long UsuarioId) throws Exception;
 
    public void remove(Usuario Usuario) throws Exception;
 
    public List<Usuario> listALL() throws Exception;
}
