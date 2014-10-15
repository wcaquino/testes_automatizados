package br.ce.treinamento.locadora.export;

import java.io.FileOutputStream;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import br.ce.treinamento.locadora.conn.ConnectionFactory;

public class ExportBanco {

	protected IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection conn = new DatabaseConnection(ConnectionFactory.getConnection());
		conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		return conn;
	}
	
	public void exportarBanco() throws SQLException, Exception{
		IDataSet fullDataSet = getConnection().createDataSet();
		FileOutputStream xmlStream = new FileOutputStream("bancoFlatExportado.xml");
		FlatXmlDataSet.write(fullDataSet, xmlStream);
	}
	
	public static void main(String[] args) throws SQLException, Exception {
		new ExportBanco().exportarBanco();
	}
}
