package br.ce.treinamento.locadora.negocio;

import java.util.Calendar;
import java.util.List;

import br.ce.treinamento.locadora.dao.LocacaoDao;
import br.ce.treinamento.locadora.entidades.Filme;
import br.ce.treinamento.locadora.entidades.Locacao;
import br.ce.treinamento.locadora.entidades.Usuario;
import br.ce.treinamento.locadora.exceptions.LocadoraException;

public class Locadora {
	
	private LocacaoDao locacaoDao;
	private SPCService spcService;
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException {
		validarCamposObrigatorios(usuario, filmes);
		
		if(spcService.obterDebito(usuario)) {
			throw new LocadoraException("Usuario com pendencia no SPC");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		
		Calendar dataLocacao = Calendar.getInstance();
		locacao.setDataLocacao(dataLocacao.getTime());
		locacao.setValor(calcularValorLocacao(filmes, dataLocacao));
		locacao.setDataRetorno(calcularDataEntrega(filmes).getTime());
		
		for(Filme filme: filmes) {
			filme.setEstoque(filme.getEstoque() - 1);
		}
		
		locacaoDao.salvar(locacao);
		
		return locacao;
	}

	private Calendar calcularDataEntrega(List<Filme> filmes) {
		//Entrega no dia seguinte, exceto quando o dia seguinte eh domingo... nesse caso, a entrega fica para segunda
		Calendar dataEntrega = Calendar.getInstance();
		dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		if(filmes.size() >= 4) {
			dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(dataEntrega.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			dataEntrega.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dataEntrega;
	}

	private void validarCamposObrigatorios(Usuario usuario, List<Filme> filmes) throws LocadoraException {
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
	}

	private Double calcularValorLocacao(List<Filme> filmes, Calendar dataLocacao) {
		int contadorDeFilmes = 0;
		Double valorLocacao = 0d;
		
		for(Filme filme: filmes) {
			contadorDeFilmes++;
			if(dataLocacao.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				Double precoFilme = null;
				switch (contadorDeFilmes) {
					case 4: precoFilme = filme.getPrecoLocacao() * 0.75; break;
					case 5: precoFilme = filme.getPrecoLocacao() * 0.50; break;
					case 6: precoFilme = filme.getPrecoLocacao() * 0.25; break;
					case 7: precoFilme = 0d; break;
					default: precoFilme = filme.getPrecoLocacao(); break;
				}
				valorLocacao += precoFilme;
			} else {
				valorLocacao += filme.getPrecoLocacao();
			}
		}
		return valorLocacao;
	}

	public void notificarAtrasados() {
		List<Locacao> locacoes = locacaoDao.obterLocacoesPendentes();
		Calendar dataAtual = Calendar.getInstance();
		for(Locacao locacao: locacoes) {
			if(dataAtual.getTime().after(locacao.getDataRetorno())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}

	public void setLocacaoDao(LocacaoDao locacaoDao) {
		this.locacaoDao = locacaoDao;
	}

	public void setSpcService(SPCService spcService) {
		this.spcService = spcService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
