package br.com.gvendas.gestaovendas.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gvendas.gestaovendas.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByNome(String nome);
}
