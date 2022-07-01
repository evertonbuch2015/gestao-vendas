package br.com.gvendas.gestaovendas.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import br.com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import br.com.gvendas.gestaovendas.servico.VendaServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaControlador {

	@Autowired
	private VendaServico vendaServico;

	@Operation(summary = "Listar Vendas por Cliente")
	@GetMapping("/cliente/{codigoCliente}")
	public List<VendaResponseDTO> listarVendasPorCliente(@PathVariable Long codigoCliente) {
		return vendaServico.listarVendasPorCliente(codigoCliente);
	}

	@Operation(summary = "Listar Venda por Código")
	@GetMapping("/{codigoVenda}")
	public VendaResponseDTO listarVendaPorCodigo(@PathVariable Long codigoVenda) {
		return vendaServico.listarVendaPorCodigo(codigoVenda);
	}
	
	@Operation(summary = "Salvar Venda")
	@PostMapping
	public ResponseEntity<VendaResponseDTO> salvar(@Valid @RequestBody VendaRequestDTO vendaRequestDTO){	
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaServico.salvar(vendaRequestDTO));
	}
	
	@Operation(summary = "Deletar Venda")
	@DeleteMapping("/{codigoVenda}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigoVenda) {
		vendaServico.deletar(codigoVenda);
	}
	
}
