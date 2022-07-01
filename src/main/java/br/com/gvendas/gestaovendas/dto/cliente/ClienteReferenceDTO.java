package br.com.gvendas.gestaovendas.dto.cliente;

import br.com.gvendas.gestaovendas.entidades.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cliente Referenciado DTO")
public class ClienteReferenceDTO {

	@Schema(description = "Código")
	private Long codigo;

	public ClienteReferenceDTO() {
	}

	public ClienteReferenceDTO(Long codigo) {
		this.codigo = codigo;
	}

	public Cliente converterParaCliente() {
		return new Cliente(codigo);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
