package br.com.gvendas.gestaovendas.dto.produto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.gvendas.gestaovendas.dto.categoria.CategoriaReferenceDTO;
import br.com.gvendas.gestaovendas.entidades.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Produto Requisição DTO")
public class ProdutoRequestDTO {

	@Schema(description = "Descrição")
	@NotBlank(message = "Descrição")
	@Length(min = 3, max = 100, message = "Descrição")
	private String descricao;

	@Schema(description = "Quantidade")
	@NotNull(message = "Quantidade")
	private Integer quantidade;

	@Schema(description = "Preço Custo")
	@NotNull(message = "Preço Custo")
	private BigDecimal precoCusto;

	@Schema(description = "Preço Venda")
	@NotNull(message = "Preço Venda")
	private BigDecimal precoVenda;

	@Schema(description = "Observação")
	@Length(max = 500, message = "Observação")
	private String observacao;

	@Schema(description = "Categoria")
	@NotNull
	private CategoriaReferenceDTO categoria;

	public Produto converterParaProduto() {
		return new Produto(descricao, quantidade, precoCusto, precoVenda, observacao,
				categoria.converterParaCategoria());
	}
	
	public Produto converterParaProduto(Long codigo) {
		 Produto produto = new Produto(descricao, quantidade, precoCusto, precoVenda, observacao,
				categoria.converterParaCategoria());
		 produto.setCodigo(codigo);;
		 return produto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public CategoriaReferenceDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaReferenceDTO categoria) {
		this.categoria = categoria;
	}

}
