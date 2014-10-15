package br.ce.treinamento.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;

public class LocacaoDaoImpl implements LocacaoDao{
	FilmeDao filmeDao = new FilmeDaoImpl();
	
	@Override
	public void save(Locacao locacao) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO locacao VALUES (null, ?, ?, ?, ?, ?)");
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(builder.toString());
		int count = 1;
		stmt.setLong(count++, locacao.getUsuario().getId());
		stmt.setDate(count++, new java.sql.Date(locacao.getDataLocacao().getTime()));
		stmt.setDate(count++, new java.sql.Date(locacao.getDataRetorno().getTime()));
		stmt.setDouble(count++, locacao.getValor());
		stmt.setInt(count++, locacao.getStatus());
		stmt.executeUpdate();
			
		builder = new StringBuilder();
		builder.append("SELECT id FROM locacao order by id desc");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		locacao.setId(rs.getLong(1));
		
		builder = new StringBuilder();
		builder.append("INSERT INTO filmes_locacao values (?, ?)");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		for(Filme filme: locacao.getFilmes()) {
			stmt.setLong(1, locacao.getId());
			stmt.setLong(2, filme.getId());
			stmt.executeUpdate();
		}
	}

	@Override
	public void edit(Locacao locacao) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Locacao find(Long locacaoId) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM locacao WHERE id = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, locacaoId);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()) 
			return null;
		Locacao locacao = new Locacao();
		locacao.setId(rs.getLong("id"));
		locacao.setDataLocacao(rs.getDate("data_locacao"));
		locacao.setDataRetorno(rs.getDate("data_retorno"));
		locacao.setValor(rs.getDouble("valor"));
		locacao.setStatus(rs.getInt("status"));
		rs.close();
		stmt.close();
		
		List<Filme> filmes = new ArrayList<Filme>();
		builder = new StringBuilder();
		builder.append("SELECT * FROM filmes_locacao WHERE id_locacao = ?");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, locacaoId);
		rs = stmt.executeQuery();
		while(rs.next()) {
			Long idFilme = rs.getLong("id_filme");
			filmes.add(filmeDao.find(idFilme));
		}
		locacao.setFilmes(filmes);
		return locacao;
	}

	@Override
	public void remove(Locacao locacao) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM filmes_locacao WHERE ID_locacao = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, locacao.getId());
		stmt.executeUpdate();

		builder = new StringBuilder();
		builder.append("DELETE FROM locacao WHERE ID = ?");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, locacao.getId());
		stmt.executeUpdate();
	}

	@Override
	public List<Locacao> listALL() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT id FROM locacao");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		List<Locacao> lista = new ArrayList<Locacao>();
		while(rs.next()){
			Long idLocacao = rs.getLong("id");
			lista.add(find(idLocacao));
		}
		return lista;
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT id FROM locacao where status = 0");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		List<Locacao> lista = new ArrayList<Locacao>();
		while(rs.next()){
			Long idLocacao = rs.getLong("id");
			lista.add(find(idLocacao));
		}
		return lista;
	}
	
	@Override
	public List<Locacao> obterLocacoesAtrasados() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT id FROM locacao where data_retorno < ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setDate(1, new java.sql.Date(new Date().getTime()));
		ResultSet rs = stmt.executeQuery();
		List<Locacao> lista = new ArrayList<Locacao>();
		while(rs.next()){
			Long idLocacao = rs.getLong("id");
			lista.add(find(idLocacao));
		}
		return lista;
	}

}
