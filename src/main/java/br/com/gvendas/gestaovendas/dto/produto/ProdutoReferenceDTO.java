package br.com.gvendas.gestaovendas.dto.produto;

import br.com.gvendas.gestaovendas.entidades.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Produto Referenciado DTO")
public class ProdutoReferenceDTO {

	@Schema(description = "Código")
	private Long codigo;

	public ProdutoReferenceDTO() {
	}

	public ProdutoReferenceDTO(Long codigo) {
		this.codigo = codigo;
	}

	public Produto converterParaProduto() {
		return new Produto(codigo);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
