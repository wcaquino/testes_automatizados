package br.ce.treinamento.matchers;

import java.util.Calendar;

public class MatchersProprios {

	public static DataSemHoraMatcher mesmoDiaQue(Calendar calendar) {
		return new DataSemHoraMatcher(calendar);
	}
}
