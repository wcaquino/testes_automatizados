package br.ce.treinamento.locadora.dao;

import static br.ce.treinamento.locadora.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ce.treinamento.locadora.entidades.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDaoTest {

	private UsuarioDao usuarioDao;
	private static Usuario usuarioParaTestes;
	
	@Before
	public void setup(){
		usuarioDao = new UsuarioDaoImpl();
	}
	
	@Test
	public void deve1SalvarUsuario() throws Exception{
		Usuario usuario = umUsuario().comCpf(new Date().getTime()).comEmail(new Date().getTime() + "@email.com").criar();
		Usuario usuarioSalvo = usuarioDao.save(usuario);
		assertThat(usuarioSalvo.getId(), is(notNullValue()));
		usuarioParaTestes = usuarioSalvo;
		usuarioDao.printAll();
	}
	
	@Test
	public void deve2AlterarUsuario() throws Exception {
		Usuario usuario = umUsuario().comCpf(new Date().getTime()).comEmail(new Date().getTime() + "@email.com").criar();
		Usuario usuarioASerAlterado = usuarioDao.save(usuario);
		usuarioASerAlterado.setCpf(new Date().getTime());
		
		usuarioDao.edit(usuarioASerAlterado);
		
		Usuario usuarioAlterado = usuarioDao.find(usuarioASerAlterado.getId());
		assertThat(usuarioAlterado.getCpf(), is(usuarioASerAlterado.getCpf()));
		
	}
	
	@Test
	public void deve3ExcluirUsuario() throws Exception{
		Usuario usuario = umUsuario().comCpf(new Date().getTime()).comEmail(new Date().getTime() + "@email.com").criar();
		Usuario usuarioASerExcluido = usuarioDao.save(usuario);
		
		usuarioDao.remove(usuarioASerExcluido);
		
		Usuario usuarioExcluido = usuarioDao.find(usuarioASerExcluido.getId());
		assertThat(usuarioExcluido, is(nullValue()));
		
	}
	
	@Test
	public void deve4ListarUsuarios() throws Exception{
		List<Usuario> usuarios = usuarioDao.listALL();
		
		assertThat(usuarios, hasSize(greaterThan(6)));
	}
}
