package br.ce.treinamento.matchers;

import static br.ce.treinamento.util.DataUtil.obterDataZerada;

import java.util.Calendar;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DataSemHoraMatcher extends TypeSafeMatcher<Calendar>{

	private Calendar outraData;

	public DataSemHoraMatcher(Calendar outraData) {
		this.outraData = outraData;
	}
	
	@Override
	public void describeTo(Description desc) {
		desc.appendText("No mesmo dia");
	}

	@Override
	protected boolean matchesSafely(Calendar data) {
		return obterDataZerada(data).equals(obterDataZerada(outraData));
	}

}
