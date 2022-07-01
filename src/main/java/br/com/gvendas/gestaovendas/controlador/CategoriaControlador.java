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

import br.com.gvendas.gestaovendas.dto.categoria.CategoriaRequestDTO;
import br.com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import br.com.gvendas.gestaovendas.entidades.Categoria;
import br.com.gvendas.gestaovendas.servico.CategiraServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {

	@Autowired
	private CategiraServico servico;

	public CategoriaControlador() {

	}

	@Operation(summary = "Listar")
	@GetMapping()
	public List<CategoriaResponseDTO> listarTodas() {
		return servico.listarTodas().stream()
				.map(categoria -> CategoriaResponseDTO.converterParaCategoriaDTO(categoria))
				.collect(Collectors.toList());
	}

	@Operation(summary = "Listar por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long codigo) {
		Optional<Categoria> categoria = servico.buscarPorCodigo(codigo);

		return categoria.isPresent()
				? ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoria.get()))
				: ResponseEntity.notFound().build();
	}

	@Operation(summary = "Salvar")
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
		Categoria categoria = servico.salvar(categoriaRequestDTO.converterParaCategoria());
		return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponseDTO.converterParaCategoriaDTO(categoria));
	}

	@Operation(summary = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<CategoriaResponseDTO> atualizar(@Valid @PathVariable Long codigo, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
		Categoria categoria = servico.atualizar(codigo, categoriaRequestDTO.converterParaCategoria(codigo));
		return ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoria));
	}

	@Operation(summary = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		servico.deletar(codigo);
	}
}
