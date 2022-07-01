package br.com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gvendas.gestaovendas.dto.cliente.ClienteReferenceDTO;
import br.com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import br.com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import br.com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import br.com.gvendas.gestaovendas.entidades.Cliente;
import br.com.gvendas.gestaovendas.entidades.ItemVenda;
import br.com.gvendas.gestaovendas.entidades.Produto;
import br.com.gvendas.gestaovendas.entidades.Venda;
import br.com.gvendas.gestaovendas.excecao.NegocioException;
import br.com.gvendas.gestaovendas.repositorio.ItemVendaRepositorio;
import br.com.gvendas.gestaovendas.repositorio.VendaRepositorio;

@Service
public class VendaServico extends VendaServicoAbstract {

	private VendaRepositorio vendaRepositorio;
	private ClienteServico clienteServico;
	private ItemVendaRepositorio itemVendaRepositorio;
	private ProdutoServico produtoServico;

	@Autowired
	public VendaServico(ClienteServico clienteServico, VendaRepositorio vendaRepositorio,
			ItemVendaRepositorio itemVendaRepositorio, ProdutoServico produtoServico) {
		this.clienteServico = clienteServico;
		this.vendaRepositorio = vendaRepositorio;
		this.itemVendaRepositorio = itemVendaRepositorio;
		this.produtoServico = produtoServico;
	}

	public List<VendaResponseDTO> listarVendasPorCliente(Long codigoCliente) {
		Cliente clienteValidado = validarClienteExiste(codigoCliente);
		List<Venda> vendas = vendaRepositorio.findByClienteCodigo(clienteValidado.getCodigo());

		return vendas.stream().map(venda -> converterParaVendaResponseDTO(venda,
				itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo()))).collect(Collectors.toList());
	}

	public VendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
		Venda venda = validarVendaExiste(codigoVenda);
		List<ItemVenda> itens = itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo());

		return converterParaVendaResponseDTO(venda, itens);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public VendaResponseDTO salvar(VendaRequestDTO vendaRequestDTO) {
		Cliente clienteValidado = validarClienteExiste(vendaRequestDTO.getCliente());
		validarProdutosExistem(vendaRequestDTO.getItens());

		Venda vendaSalva = vendaRepositorio.save(new Venda(vendaRequestDTO.getData(), clienteValidado));

		vendaRequestDTO.getItens().stream()
				.map(itemVendaRequestDTO -> converterParaItemVenda(itemVendaRequestDTO, vendaSalva))
				.forEach(itemVendaRepositorio::save);		

		return converterParaVendaResponseDTO(vendaSalva, itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo()));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void deletar(Long codigoVenda) {
		Venda venda = validarVendaExiste(codigoVenda);
		itemVendaRepositorio.deleteByVendaCodigo(venda.getCodigo());
		vendaRepositorio.deleteById(venda.getCodigo());
	}

	private void validarProdutosExistem(List<ItemVendaRequestDTO> itens) {
		itens.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExiste(item.getProduto().getCodigo());
			validarExisteEstoqueProduto(produto, item.getQuantidade());
		});
	}
	
	private void validarExisteEstoqueProduto(Produto produto, Integer qtdVenda) {
		if (produto.getQuantidade() < qtdVenda) {
			throw new NegocioException(String.format("O produto %s não possui estoque necessário para esta venda!", produto.getCodigo()));
		}
	}

	private Venda validarVendaExiste(Long codigoVenda) {
		Optional<Venda> venda = vendaRepositorio.findById(codigoVenda);
		if (!venda.isPresent()) {
			throw new NegocioException(String.format("Venda de código %s não encontrada!", codigoVenda));
		}

		return venda.get();
	}

	private Cliente validarClienteExiste(ClienteReferenceDTO clienteReferenceDTO) {
		if (clienteReferenceDTO == null) {
			throw new NegocioException("O Cliente deve ser informado!");
		}
		return validarClienteExiste(clienteReferenceDTO.getCodigo());
	}

	private Cliente validarClienteExiste(Long codigoCliente) {
		if (codigoCliente == null) {
			throw new NegocioException("O Cliente deve ser informado!");
		}

		Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigoCliente);
		if (!cliente.isPresent()) {
			throw new NegocioException(
					String.format("O Cliente de código %s informado não existe no cadastro!", codigoCliente));
		}
		return cliente.get();
	}
}
