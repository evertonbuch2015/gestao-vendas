package br.com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.gvendas.gestaovendas.entidades.Produto;
import br.com.gvendas.gestaovendas.excecao.NegocioException;
import br.com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;

@Service
public class ProdutoServico {

	@Autowired
	private ProdutoRepositorio repositorio;

	@Autowired
	private CategiraServico categiraServico;

	public List<Produto> listarTodos() {
		return repositorio.findAll();
	}

	public List<Produto> listarPorCategoria(Long codigoCategoria) {
		return repositorio.findByCategoriaCodigo(codigoCategoria);
	}

	public Optional<Produto> buscarPorCodigo(Long codigo) {
		return repositorio.findById(codigo);
	}

	public Produto salvar(Produto produto) {
		validarDuplicidadeProduto(produto);
		validarCategoriaExiste(produto.getCategoria().getCodigo());
		return repositorio.save(produto);
	}

	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoSalvar = validarProdutoExiste(codigo);
		validarDuplicidadeProduto(produto);
		validarCategoriaExiste(produto.getCategoria().getCodigo());
		BeanUtils.copyProperties(produto, produtoSalvar, "codigo");

		return repositorio.save(produtoSalvar);
	}
	
	public void deletar(Long codigo) {
		Produto produto = validarProdutoExiste(codigo);
		repositorio.delete(produto);
	}

	public Produto validarProdutoExiste(Long codigo) {
		Optional<Produto> produto = buscarPorCodigo(codigo);
		if (!produto.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produto.get();
	}

	private void validarDuplicidadeProduto(Produto produto) {
		Optional<Produto> produtoEncontrado = repositorio.findByDescricao(produto.getDescricao());
		if (produtoEncontrado.isPresent() && produto.getCodigo() != produtoEncontrado.get().getCodigo()) {
			throw new NegocioException(String.format("Produto de nome %s já cadastrado no sistema!",
					produto.getDescricao().toUpperCase()));
		}
	}

	private void validarCategoriaExiste(Long codigoCategoria) {
		if (codigoCategoria == null) {
			throw new NegocioException("A Categoria do produto deve ser informada!");
		}

		if (!categiraServico.buscarPorCodigo(codigoCategoria).isPresent()) {
			throw new NegocioException(
					String.format("A Categoria de código %s informada não existe!", codigoCategoria.toString()));
		}
	}
}
