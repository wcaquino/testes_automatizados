package br.ce.treinamento.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public static Calendar obterDataDiferencaDias(int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, dias);
		return calendar;
	}
	
	public static Date obterData(String data) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Calendar obterCalendar(String data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(obterData(data));
		return calendar;
	}
}
