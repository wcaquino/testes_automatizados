package br.ce.treinamento.matchers;

import static br.ce.treinamento.util.DataUtil.obterDataZerada;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DataComDiferencaVariavelMatcher extends TypeSafeMatcher<Date>{

	private int tipoCalendar;
	private int diferenca;

	public DataComDiferencaVariavelMatcher(int diferenca, int tipoCalendar) {
		this.tipoCalendar = tipoCalendar;
		this.diferenca = diferenca;
	}
	
	
	@Override
	public void describeTo(Description description) {
		description.appendText("Uma data com diferenca");
	}

	@Override
	protected boolean matchesSafely(Date item) {
		Calendar calendar = Calendar.getInstance();
		calendar = obterDataZerada(calendar);
		calendar.add(tipoCalendar, diferenca);
		
		return calendar.getTime().equals(obterDataZerada(item));
	}

}
