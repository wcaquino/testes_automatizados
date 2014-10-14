package br.ce.treinamento.locadora.conn;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static Connection conn;
	
	public static Connection getConnection() throws Exception{
//		if (conn == null){
			String localBanco = System.getProperty("user.dir") + File.separator + "banco" + File.separator + "dados";
			
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection(
					 "jdbc:hsqldb:file:"+localBanco, "sa", "");
//		}
		return conn;
	}
}
