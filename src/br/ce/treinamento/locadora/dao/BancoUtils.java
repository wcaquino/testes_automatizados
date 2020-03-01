package br.treinamento;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class BancoUtils {

	public static void registrosEntidade() throws Exception{
		EntidadeDAOInterface dao = new EntidadeDAO();
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder, Locale.getDefault());
		
		formatter.format("%1$2s | %2$30s | %3$15s | %4$2s | %5$20s | %6$10s | %7$10s | %8$10s \n", 
				"ID", "NOME", "NUM_DOC", "T", "EMAIL", "DT_INICIO", "DT_FIM", "DT_GRAV");
		builder.append("=====================================================================" +
						 "=================================================== \n");

		List<Entidade> entidades = dao.retrieveAll();
		for(Entidade entidade: entidades) {
			formatter.format("%1$2d | %2$30s | %3$15d | %4$2d | %5$20s | %6$10tF | %7$10tF | %8$10tF \n", 
					entidade.getId(), entidade.getNome(), entidade.getNumeroDocumento(), 
					entidade.getTipoDocumento(), entidade.getEmail(), entidade.getDataInicial(),
					entidade.getDataFinal(), entidade.getDataGravacao());
		}
		System.out.println(builder.toString());
	}
	
	public static void main(String[] args) throws Exception {
		registrosEntidade();
	}
}
