package br.ce.treinamento.locadora.dao;

import java.io.FileInputStream;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import br.ce.treinamento.locadora.conn.ConnectionFactory;

public class UsuarioDaoDbUnit extends DatabaseTestCase {

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
	
	public void test() throws Exception{
		UsuarioDao usuarioDao = new UsuarioDaoImpl();
		usuarioDao.printAll();
	}

}
