package br.com.gvendas.gestaovendas.dto.categoria;

import br.com.gvendas.gestaovendas.entidades.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoria Referenciada DTO")
public class CategoriaReferenceDTO {

	@Schema(description = "Código")
	private Long codigo;

	public CategoriaReferenceDTO() {
	}

	public CategoriaReferenceDTO(Long codigo) {
		this.codigo = codigo;
	}

	public Categoria converterParaCategoria() {
		return new Categoria(codigo);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
