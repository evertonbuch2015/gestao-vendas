package br.com.gvendas.gestaovendas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gvendas.gestaovendas.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{

	List<Produto> findByCategoriaCodigo(Long codigoCategoria);
	Optional<Produto> findByDescricao(String descricao);	
}
