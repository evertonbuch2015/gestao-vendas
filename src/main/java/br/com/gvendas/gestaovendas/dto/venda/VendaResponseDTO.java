package br.com.gvendas.gestaovendas.dto.venda;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Venda Retorno DTO")
public class VendaResponseDTO {

	@Schema(description = "Código")
	private Long codigo;

	@Schema(description = "Data")
	private LocalDate data;

	@Schema(description = "Cliente")
	private ClienteVendaDTO cliente;

	@Schema(description = "Itens")
	private List<ItemVendaResponseDTO> itemVendaResponseDTO;

	public VendaResponseDTO(Long codigo, LocalDate data, ClienteVendaDTO cliente,
			List<ItemVendaResponseDTO> itemVendaResponseDTO) {
		this.codigo = codigo;
		this.data = data;
		this.cliente = cliente;
		this.itemVendaResponseDTO = itemVendaResponseDTO;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public ClienteVendaDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVendaDTO cliente) {
		this.cliente = cliente;
	}

	public List<ItemVendaResponseDTO> getItemVendaResponseDTO() {
		return itemVendaResponseDTO;
	}

	public void setItemVendaResponseDTO(List<ItemVendaResponseDTO> itemVendaResponseDTO) {
		this.itemVendaResponseDTO = itemVendaResponseDTO;
	}

}
