package br.ce.treinamento.locadora.negocio;

import java.util.Calendar;
import java.util.Date;

public class Relogio {

	public Date obterDataAtual(){
		return new Date();
	}
	
	public Calendar obterCalendarAtual(){
		return Calendar.getInstance();
	}
}
