package br.ce.treinamento.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.locadora.entidades.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

	public Usuario save(Usuario usuario) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO USUARIO VALUES (null, ?, ?, ?)");
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(builder.toString());
		int count = 1;
		stmt.setString(count++, usuario.getNome());
		stmt.setString(count++, usuario.getEmail());
		stmt.setLong(count++, usuario.getCpf());
		stmt.executeUpdate();
			
		builder = new StringBuilder();
		builder.append("SELECT id FROM usuario WHERE cpf = ?");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, usuario.getCpf());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		usuario.setId(rs.getLong("id"));
		return usuario;
	}

	@Override
	public Usuario edit(Usuario usuario) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE usuario SET nome = ?, email = ?, cpf = ? where id = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		int count = 1;
		stmt.setString(count++, usuario.getNome());
		stmt.setString(count++, usuario.getEmail());
		stmt.setLong(count++, usuario.getCpf());
		stmt.setLong(count++, usuario.getId());
		stmt.executeUpdate();
		return usuario;
	}

	@Override
	public Usuario find(Long usuarioId) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM usuario WHERE id = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, usuarioId);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()) 
			return null;
		Usuario usuario = new Usuario();
		usuario.setId(rs.getLong("id"));
		usuario.setNome(rs.getString("nome"));
		usuario.setEmail(rs.getString("email"));
		usuario.setCpf(rs.getLong("cpf"));
		return usuario;
	}

	@Override
	public void remove(Usuario usuario) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM usuario WHERE ID = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, usuario.getId());
		stmt.executeUpdate();
	}

	@Override
	public List<Usuario> listALL() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM usuario");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		List<Usuario> lista = new ArrayList<Usuario>();
		while(rs.next()){
			Usuario usuario = new Usuario();
			usuario.setId(rs.getLong("id"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setCpf(rs.getLong("cpf"));
			lista.add(usuario);
		}
		return lista;
	}
	
	public void printAll() throws Exception{
		List<Usuario> usuarios = listALL();
		for(Usuario usuario: usuarios) {
			System.out.println(usuario);
		}
	}
}
