package br.ce.treinamento.locadora.utils;

import java.util.Calendar;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;

import br.ce.treinamento.util.DataUtil;

public class ReplacementUtils {

	public static ReplacementDataSet obterReplacementDeDatas(IDataSet dataSet) {
		//Fazendo os Replacements
		ReplacementDataSet dataSetReplaced = new ReplacementDataSet(dataSet);
		
		Calendar calendar = Calendar.getInstance();
		calendar = DataUtil.obterDataZerada(calendar);
		dataSetReplaced.addReplacementObject("hoje", calendar.getTime());
		
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar = DataUtil.obterDataZerada(calendar);
		dataSetReplaced.addReplacementObject("amanha", calendar.getTime());
		
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		calendar = DataUtil.obterDataZerada(calendar);
		dataSetReplaced.addReplacementObject("ontem", calendar.getTime());
		
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar = DataUtil.obterDataZerada(calendar);
		dataSetReplaced.addReplacementObject("2diasAtras", calendar.getTime());
		
		return dataSetReplaced;
	}
}
