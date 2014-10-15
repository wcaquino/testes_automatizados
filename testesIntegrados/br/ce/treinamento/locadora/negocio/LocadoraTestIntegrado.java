package br.ce.treinamento.locadora.negocio;

import static br.ce.treinamento.locadora.builders.FilmeBuilder.umFilme;
import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Calendar;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import br.ce.treinamento.locadora.conn.ConnectionFactory;
import br.ce.treinamento.util.DataUtil;

public class LocadoraTestIntegrado {

	private Locadora locadora;
	
	protected IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection conn = new DatabaseConnection(ConnectionFactory.getConnection());
		conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		return conn;
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("inputLocadora.xml"));
	}
	
	@Before
	public void setUp() throws Exception {
		locadora = new Locadora();
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}	
	
	@Test
	public void deveAlugarFilme() throws Exception{
		locadora.alugarFilme(umUsuario().comId(1L).criar(), Arrays.asList(umFilme().criar()));
		
		//Expectativa
		IDataSet dadosEsperados = new FlatXmlDataSet(new FlatXmlProducer(new InputSource("baseEsperadaAlugarFilme.xml")));
		ITable tabelaLocacaoEsperada = dadosEsperados.getTable("locacao");
		ITable tabelaLocacaoEsperadaFiltrada = DefaultColumnFilter.excludedColumnsTable(tabelaLocacaoEsperada, new String[] {"id"});
		ITable tabelaFilmesLocacaoEsperada = dadosEsperados.getTable("filmes_locacao");
		ITable tabelaFilmesLocacaoEsperadaFiltrada = DefaultColumnFilter.excludedColumnsTable(tabelaFilmesLocacaoEsperada, new String[] {"id_locacao"});
		
		//Banco
		IDataSet dadosBanco = getConnection().createDataSet();
		ITable tabelaLocacaoBanco = dadosBanco.getTable("locacao");
		ITable tabelaLocacaoBancoFiltrada = DefaultColumnFilter.excludedColumnsTable(tabelaLocacaoBanco, new String[] {"id"});
		ITable tabelaFilmesLocacaoBanco = dadosBanco.getTable("filmes_locacao");
		ITable tabelaFilmesLocacaoBancoFiltrada = DefaultColumnFilter.excludedColumnsTable(tabelaFilmesLocacaoBanco, new String[] {"id_locacao"});
		
		Assertion.assertEquals(tabelaLocacaoEsperadaFiltrada, tabelaLocacaoBancoFiltrada);
		Assertion.assertEquals(tabelaFilmesLocacaoEsperadaFiltrada, tabelaFilmesLocacaoBancoFiltrada);
	}
}
