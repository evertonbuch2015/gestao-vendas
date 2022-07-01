package br.com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;

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

import br.com.gvendas.gestaovendas.entidades.Cliente;
import br.com.gvendas.gestaovendas.servico.ClienteServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cliente")
@RestController
@RequestMapping("cliente")
public class ClienteControlador {

	@Autowired
	private ClienteServico servico;

	public ClienteControlador() {

	}

	@Operation(summary = "Listar")
	@GetMapping()
	public List<Cliente> listarTodas() {
		return servico.listarTodos();
	}

	@Operation(summary = "Listar por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long codigo) {
		Optional<Cliente> optional = servico.buscarPorCodigo(codigo);

		return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build();
	}

	@Operation(summary = "Salvar")
	@PostMapping
	public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = servico.salvar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}

	@Operation(summary = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = servico.atualizar(codigo, cliente);
		return ResponseEntity.ok(clienteSalvo);
	}

	@Operation(summary = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		servico.deletar(codigo);
	}
}
