package br.com.gvendas.gestaovendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EntityScan(basePackages = {"br.com.gvendas.gestaovendas.entidades"})
@EnableJpaRepositories(basePackages = {"br.com.gvendas.gestaovendas.repositorio"})
@ComponentScan(basePackages = {"br.com.gvendas.gestaovendas.servico", "br.com.gvendas.gestaovendas.controlador", "br.com.gvendas.gestaovendas.excecao"})
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gestão de Vendas", version = "2.0", description = "Sistema de Gestão de Vendas!"))
public class GestaoVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVendasApplication.class, args);
	}

}
