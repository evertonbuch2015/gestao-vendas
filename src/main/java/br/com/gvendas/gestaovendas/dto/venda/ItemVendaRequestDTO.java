package br.com.gvendas.gestaovendas.dto.venda;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.gvendas.gestaovendas.dto.produto.ProdutoReferenceDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item Venda Requisição DTO")
public class ItemVendaRequestDTO {

	@NotNull(message = "Quantidade")
	@Min(value = 1, message = "Quantidade")
	@Schema(description = "Quantidade")
	private Integer quantidade;

	@NotNull(message = "Preço Vendido")
	@Schema(description = "Preço Vendido")
	private BigDecimal precoVendido;

	@NotNull(message = "Produto")
	@Schema(description = "Produto")
	private ProdutoReferenceDTO produto;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoVendido() {
		return precoVendido;
	}

	public void setPrecoVendido(BigDecimal precoVendido) {
		this.precoVendido = precoVendido;
	}

	public ProdutoReferenceDTO getProduto() {
		return produto;
	}

	public void setProduto(ProdutoReferenceDTO produto) {
		this.produto = produto;
	}

}
