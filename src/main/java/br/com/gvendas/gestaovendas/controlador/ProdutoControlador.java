package br.com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import br.com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import br.com.gvendas.gestaovendas.entidades.Produto;
import br.com.gvendas.gestaovendas.servico.ProdutoServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produto")
@RestController
@RequestMapping("/produto")
public class ProdutoControlador {

	@Autowired
	private ProdutoServico servico;

	public ProdutoControlador() {

	}

	@Operation(summary = "Listar")
	@GetMapping()
	public List<ProdutoResponseDTO> listarTodas() {
		return servico.listarTodos().stream().map(produto -> ProdutoResponseDTO.converterParaProdutoDTO(produto))
				.collect(Collectors.toList());
	}

	@Operation(summary = "Listar por id")
	@GetMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long codigo) {
		Optional<Produto> produto = servico.buscarPorCodigo(codigo);

		return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produto.get()))
				: ResponseEntity.notFound().build();
	}

	@Operation(summary = "Salvar")
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produto) {
		Produto produtoSalvo = servico.salvar(produto.converterParaProduto());
		return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterParaProdutoDTO(produtoSalvo));
	}

	@Operation(summary = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long codigo, @Valid @RequestBody ProdutoRequestDTO produto) {
		Produto ProdutoSalvo = servico.atualizar(codigo, produto.converterParaProduto());
		return ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(ProdutoSalvo));
	}

	@Operation(summary = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		servico.deletar(codigo);
	}
}