package br.ce.treinamento.locadora.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

public class Locadora {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException {
		if(usuario == null) {
			throw new LocadoraException("O usuario nao pode estar vazio");
		}
		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("O Filme nao pode estar vazio");
		}
		for(Filme filme: filmes) {
			if(filme.getEstoque() <= 0) {
				throw new LocadoraException("Nao eh possivel alugar filme que nao estah no estoque");
			}
		}
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		for(Filme filme: filmes)
			locacao.setValor(locacao.getValor() + filme.getPrecoLocacao());

		//Entrega no dia seguinte, exceto quando o dia seguinte eh domingo... nesse caso, a entrega fica para segunda
		Calendar dataEntrega = Calendar.getInstance();
		dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		if(dataEntrega.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(filmes.size() >= 4) {
			dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		}
		locacao.setDataRetorno(dataEntrega.getTime());
		for(Filme filme: filmes) {
			filme.setEstoque(filme.getEstoque() - 1);
		}
		
		return locacao;
	}
}
