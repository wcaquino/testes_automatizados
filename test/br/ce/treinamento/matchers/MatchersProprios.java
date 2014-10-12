package br.ce.treinamento.matchers;

import java.util.Calendar;

public class MatchersProprios {

	public static DataSemHoraMatcher mesmoDiaQue(Calendar calendar) {
		return new DataSemHoraMatcher(calendar);
	}
	
	public static DataComDiferencaVariavelMatcher hojeComDiferencaDe(int diferenca, int tipoCalendar) {
		return new DataComDiferencaVariavelMatcher(diferenca, tipoCalendar);
	}
	
	public static DataComDiferencaVariavelMatcher hojeComDiferencaDias(int diferenca) {
		return new DataComDiferencaVariavelMatcher(diferenca, Calendar.DAY_OF_MONTH);
	}
	
	public static DataComDiferencaVariavelMatcher hojeComDiferencaMeses(int diferenca) {
		return new DataComDiferencaVariavelMatcher(diferenca, Calendar.MONTH);
	}
	
	public static DataComDiferencaVariavelMatcher hojeComDiferencaAnos(int diferenca) {
		return new DataComDiferencaVariavelMatcher(diferenca, Calendar.YEAR);
	}
	
	public static VerificarMesmaDataMatcher data(String data) {
		return new VerificarMesmaDataMatcher(data);
	}
}
