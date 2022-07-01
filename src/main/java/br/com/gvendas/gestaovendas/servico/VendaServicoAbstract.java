package br.com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.stream.Collectors;

import br.com.gvendas.gestaovendas.dto.venda.ClienteVendaDTO;
import br.com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import br.com.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import br.com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import br.com.gvendas.gestaovendas.entidades.ItemVenda;
import br.com.gvendas.gestaovendas.entidades.Venda;

public abstract class VendaServicoAbstract {

	protected VendaResponseDTO converterParaVendaResponseDTO(Venda venda, List<ItemVenda> itensVenda) {
		List<ItemVendaResponseDTO> itensVendaResponseDTOs = itensVenda.stream()
				.map(this::converterParaItemVendaResponseDTO).collect(Collectors.toList());

		return new VendaResponseDTO(venda.getCodigo(), venda.getData(),
				ClienteVendaDTO.converterParaClienteVendaDTO(venda.getCliente()), itensVendaResponseDTOs);
	}

	protected ItemVendaResponseDTO converterParaItemVendaResponseDTO(ItemVenda itemVenda) {
		return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
				itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
	}

	protected ItemVenda converterParaItemVenda(ItemVendaRequestDTO itemVendaRequestDTO, Venda venda) {
		return new ItemVenda(itemVendaRequestDTO.getQuantidade(), itemVendaRequestDTO.getPrecoVendido(),
				itemVendaRequestDTO.getProduto().converterParaProduto(), venda);
	}
}
