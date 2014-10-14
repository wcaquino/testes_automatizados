package br.ce.treinamento.locadora.dao;

import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.locadora.entidades.Usuario;

public class UsuarioDaoDbUnit extends DatabaseTestCase {

	private UsuarioDaoImpl usuarioDao;

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection conn = new DatabaseConnection(ConnectionFactory.getConnection());
		conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		return conn;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("input.xml"));
	}
	
	@Override
	protected void setUp() throws Exception {
		usuarioDao = new UsuarioDaoImpl();
		super.setUp();
	}	
	
	public void testSalvar() throws Exception{
		Usuario usuario = umUsuario().criar();
		Usuario usuario2 = usuarioDao.save(usuario);
		assertThat(usuario2.getId(), is(notNullValue()));
		
		usuarioDao.printAll();
	}

	public void testAlterar() throws Exception{
		Usuario usuario = umUsuario().comId(1L).comNome("Joaquim").criar();
		usuarioDao.edit(usuario);
		
		Usuario usuarioPersistido = usuarioDao.find(1L);
		assertThat(usuarioPersistido.getNome(), is("Joaquim"));

		usuarioDao.printAll();
	}
	
	public void testExcluir() throws Exception {
		Usuario usuario = umUsuario().comId(1L).criar();
		usuarioDao.remove(usuario);
		
		Usuario usuarioPersistido = usuarioDao.find(1L);
		assertThat(usuarioPersistido, is(nullValue()));
		
		usuarioDao.printAll();
	}
}
