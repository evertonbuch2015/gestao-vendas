package br.com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.gvendas.gestaovendas.entidades.Categoria;
import br.com.gvendas.gestaovendas.excecao.NegocioException;
import br.com.gvendas.gestaovendas.repositorio.CategoriaRepositorio;

@Service
public class CategiraServico {

	@Autowired
	private CategoriaRepositorio repositorio;

	public CategiraServico() {

	}

	public List<Categoria> listarTodas() {
		return repositorio.findAll();
	}

	public Optional<Categoria> buscarPorCodigo(Long codigo) {
		return repositorio.findById(codigo);
	}

	public Categoria salvar(Categoria categoria) {
		validarCategoriaDuplicada(categoria);
		return repositorio.save(categoria);
	}

	public Categoria atualizar(Long codigo, Categoria categoria) {
		Categoria categoriaSalvar = validarCategoriaExiste(codigo);
		validarCategoriaDuplicada(categoria);
		BeanUtils.copyProperties(categoria, categoriaSalvar, "codigo");

		return repositorio.save(categoriaSalvar);
	}

	public void deletar(Long codigo) {
		repositorio.deleteById(codigo);
	}

	private Categoria validarCategoriaExiste(Long codigo) {
		Optional<Categoria> categoria = buscarPorCodigo(codigo);
		if (!categoria.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoria.get();
	}

	private void validarCategoriaDuplicada(Categoria categoria) {
		Categoria categoriaEncontrada = repositorio.findByNome(categoria.getNome());

		if (categoriaEncontrada != null && categoriaEncontrada.getCodigo() != categoria.getCodigo()) {
			throw new NegocioException(
					String.format("A Categoria %s já está cadastrada!", categoria.getNome().toUpperCase()));
		}
	}
}
