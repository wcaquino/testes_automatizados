package br.ce.treinamento.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.locadora.entidades.Filme;

public class FilmeDaoImpl implements FilmeDao {

	@Override
	public Filme save(Filme filme) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO filme VALUES (null, ?, ?, ?)");
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(builder.toString());
		int count = 1;
		stmt.setString(count++, filme.getNome());
		stmt.setInt(count++, filme.getEstoque());
		stmt.setDouble(count++, filme.getPrecoLocacao());
		stmt.executeUpdate();
			
		builder = new StringBuilder();
		builder.append("SELECT id FROM filme WHERE nome = ?");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setString(1, filme.getNome());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		filme.setId(rs.getLong(1));
		return filme;
	}

	@Override
	public Filme edit(Filme filme) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE filme SET nome = ?, estoque = ?, preco = ? where id = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		int count = 1;
		stmt.setString(count++, filme.getNome());
		stmt.setInt(count++, filme.getEstoque());
		stmt.setDouble(count++, filme.getPrecoLocacao());
		stmt.setLong(count++, filme.getId());
		stmt.executeUpdate();
		return filme;
	}

	@Override
	public Filme find(Long filmeId) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM filme WHERE id = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, filmeId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		Filme filme = new Filme();
		filme.setNome(rs.getString("nome"));
		filme.setEstoque(rs.getInt("estoque"));
		filme.setPrecoLocacao(rs.getDouble("preco"));
		return filme;
	}

	@Override
	public void remove(Filme filme) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM filme WHERE ID = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, filme.getId());
		stmt.executeUpdate();
	}

	@Override
	public List<Filme> listALL() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM usuario");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		List<Filme> lista = new ArrayList<Filme>();
		while(rs.next()){
			Filme filme = new Filme();
			filme.setNome(rs.getString("nome"));
			filme.setEstoque(rs.getInt("estoque"));
			filme.setPrecoLocacao(rs.getDouble("preco"));
			lista.add(filme);
		}
		return lista;
	}

}
