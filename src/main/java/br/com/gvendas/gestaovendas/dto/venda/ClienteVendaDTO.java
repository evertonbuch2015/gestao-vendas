package br.com.gvendas.gestaovendas.dto.venda;

import br.com.gvendas.gestaovendas.entidades.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cliente Venda Retorno DTO")
public class ClienteVendaDTO {

	@Schema(description = "Código")
	private Long codigo;

	@Schema(description = "Nome")
	private String nome;

	public ClienteVendaDTO() {

	}

	public ClienteVendaDTO(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public static ClienteVendaDTO converterParaClienteVendaDTO(Cliente cliente) {
		return new ClienteVendaDTO(cliente.getCodigo(), cliente.getNome());
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
