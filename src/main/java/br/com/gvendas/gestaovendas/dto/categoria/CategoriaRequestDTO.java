package br.com.gvendas.gestaovendas.dto.categoria;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.gvendas.gestaovendas.entidades.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoria Requisição DTO")
public class CategoriaRequestDTO {

	@Schema(description = "Nome")
	@NotBlank(message = "Nome")
	@Length(min = 3, max = 50, message = "Nome")
	private String nome;

	public CategoriaRequestDTO() {	
	}
	
	public CategoriaRequestDTO(String nome) {
		this.nome = nome;
	}
	
	public Categoria converterParaCategoria() {
		return new Categoria(nome);
	}
	
	public Categoria converterParaCategoria(Long codigo) {
		return new Categoria(codigo, nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
