package br.com.gvendas.gestaovendas.dto.venda;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.gvendas.gestaovendas.dto.cliente.ClienteReferenceDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Venda Requisição DTO")
public class VendaRequestDTO {

	@Schema(description = "Data")
	@NotNull(message = "Data")
	private LocalDate data;

	@Schema(description = "Cliente")
	@NotNull(message = "Cliente")	
	private ClienteReferenceDTO cliente;

	@Schema(description = "Itens da Venda")
	@NotNull(message = "Itens Venda")
	@NotEmpty(message = "Itens Venda")
	@Valid	
	private List<ItemVendaRequestDTO> itens;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public ClienteReferenceDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteReferenceDTO cliente) {
		this.cliente = cliente;
	}

	public List<ItemVendaRequestDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemVendaRequestDTO> itens) {
		this.itens = itens;
	}

}
