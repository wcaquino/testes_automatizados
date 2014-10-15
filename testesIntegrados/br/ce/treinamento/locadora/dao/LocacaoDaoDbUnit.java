package br.ce.treinamento.locadora.dao;

import static org.hamcrest.CoreMatchers.is;
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
import org.junit.Before;
import org.junit.Test;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.utils.ReplacementUtils;

public class LocacaoDaoDbUnit {

	private LocacaoDao locacaoDao;

	protected IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection conn = new DatabaseConnection(ConnectionFactory.getConnection());
		conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		return conn;
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("inputLocacoesPendentes.xml"));
	}
	
	@Before
	public void setUp() throws Exception {
		locacaoDao = new LocacaoDaoImpl();
		IDataSet dataSetReplaced = ReplacementUtils.obterReplacementDeDatas(getDataSet());
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSetReplaced);
	}	
	
	@Test
	public void deveFiltrarLocacoesAtrasadas() throws Exception{
		List<Locacao> locacoesAtrasados = locacaoDao.obterLocacoesAtrasados();
		
		assertThat(locacoesAtrasados, hasSize(1));
		assertThat(locacoesAtrasados.iterator().next().getFilmes().iterator().next().getNome(), is("Senhor dos aneis"));
	}
}
