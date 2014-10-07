package br.ce.treinamento.util;

import java.util.Calendar;
import java.util.Date;

public class DataUtil {

	public static Date obterDataZerada(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar = obterDataZerada(calendar);
		return calendar.getTime();
	}
	
	public static Calendar obterDataZerada(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
}
