package br.ce.treinamento.locadora.negocio;

import java.util.Calendar;
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
		Calendar dataLocacao = Calendar.getInstance();
		locacao.setDataLocacao(dataLocacao.getTime());
		
		int contador = 0;
		for(Filme filme: filmes) {
			contador++;
			if(dataLocacao.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				Double precoFilme = null;
				switch (contador) {
					case 4: precoFilme = filme.getPrecoLocacao() * 0.75; break;
					case 5: precoFilme = filme.getPrecoLocacao() * 0.50; break;
					case 6: precoFilme = filme.getPrecoLocacao() * 0.25; break;
					case 7: precoFilme = 0d; break;
					default: precoFilme = filme.getPrecoLocacao(); break;
				}
				locacao.setValor(locacao.getValor() + precoFilme);
			} else {
				locacao.setValor(locacao.getValor() + filme.getPrecoLocacao());
			}
		}

		//Entrega no dia seguinte, exceto quando o dia seguinte eh domingo... nesse caso, a entrega fica para segunda
		Calendar dataEntrega = Calendar.getInstance();
		dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		if(filmes.size() >= 4) {
			dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(dataEntrega.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		locacao.setDataRetorno(dataEntrega.getTime());
		for(Filme filme: filmes) {
			filme.setEstoque(filme.getEstoque() - 1);
		}
		
		return locacao;
	}
}
