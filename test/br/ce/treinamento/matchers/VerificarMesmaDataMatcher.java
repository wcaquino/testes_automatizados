package br.ce.treinamento.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.treinamento.util.DataUtil;

public class VerificarMesmaDataMatcher extends TypeSafeMatcher<Date> {

	String strDataEsperada;
	
	public VerificarMesmaDataMatcher(String data) {
		this.strDataEsperada = data;
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("");
	}

	@Override
	protected boolean matchesSafely(Date dataInformada) {
		Date dataEsperada = DataUtil.obterData(strDataEsperada);
		dataEsperada = DataUtil.obterDataZerada(dataEsperada);
		
		dataInformada = DataUtil.obterDataZerada(dataInformada);
		return dataEsperada.equals(dataInformada);
	}

}
