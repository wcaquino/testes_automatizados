package br.ce.treinamento.locadora.dao;

import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.util.List;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.locadora.entidades.Usuario;

public class UsuarioDaoDbUnit {

	private UsuarioDaoImpl usuarioDao;

	protected IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection conn = new DatabaseConnection(ConnectionFactory.getConnection());
		conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		return conn;
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("input.xml"));
	}
	
	@Before
	public void setUp() throws Exception {
		usuarioDao = new UsuarioDaoImpl();
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}	
	
	@After
	public void tearDown() throws Exception{
		usuarioDao.printAll();
	}
	
	@Test
	public void testSalvar() throws Exception{
		Usuario usuario = umUsuario().criar();
		Usuario usuario2 = usuarioDao.save(usuario);
		assertThat(usuario2.getId(), is(notNullValue()));
	}

	@Test
	public void testAlterar() throws Exception{
		Usuario usuario = umUsuario().comId(1L).comNome("Joaquim").criar();
		usuarioDao.edit(usuario);
		
		Usuario usuarioPersistido = usuarioDao.find(1L);
		assertThat(usuarioPersistido.getNome(), is("Joaquim"));
	}
	
	@Test
	public void testExcluir() throws Exception {
		Usuario usuario = umUsuario().comId(1L).criar();
		usuarioDao.remove(usuario);
		
		Usuario usuarioPersistido = usuarioDao.find(1L);
		assertThat(usuarioPersistido, is(nullValue()));
	}
	
	@Test
	public void testRetrieveAll() throws Exception{
		List<Usuario> usuarios = usuarioDao.listALL();

		Usuario usuario = umUsuario().comId(1L).comNome("Jose").comEmail("jose@email.com").comCpf(123456L).criar();
		
		assertThat(usuarios, hasSize(2));
		assertThat(usuarios, hasItems(usuario));
	}
}
