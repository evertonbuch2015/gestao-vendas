package br.com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.gvendas.gestaovendas.entidades.Cliente;
import br.com.gvendas.gestaovendas.excecao.NegocioException;
import br.com.gvendas.gestaovendas.repositorio.ClienteRepositorio;

@Service
public class ClienteServico {

	@Autowired
	private ClienteRepositorio repositorio;

	public ClienteServico() {

	}

	public List<Cliente> listarTodos() {
		return repositorio.findAll();
	}

	public Optional<Cliente> buscarPorCodigo(Long codigo) {
		return repositorio.findById(codigo);
	}

	public Cliente salvar(Cliente cliente) {
		validarDuplicidadeCliente(cliente);
		return repositorio.save(cliente);
	}

	public Cliente atualizar(Long codigo, Cliente cliente) {
		Cliente clienteSalvar = validarClienteExiste(codigo);
		validarDuplicidadeCliente(cliente);
		BeanUtils.copyProperties(cliente, clienteSalvar, "codigo");

		return repositorio.save(clienteSalvar);
	}

	public void deletar(Long codigo) {
		Cliente cliente = validarClienteExiste(codigo);
		repositorio.delete(cliente);
	}

	private Cliente validarClienteExiste(Long codigo) {
		Optional<Cliente> cliente = buscarPorCodigo(codigo);
		if (!cliente.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return cliente.get();
	}

	private void validarDuplicidadeCliente(Cliente cliente) {
		Optional<Cliente> clienteEncontrado = repositorio.findByNome(cliente.getNome());
		if (clienteEncontrado.isPresent() && cliente.getCodigo() != clienteEncontrado.get().getCodigo()) {
			throw new NegocioException(
					String.format("Cliente de nome %s já cadastrado no sistema!", cliente.getNome().toUpperCase()));
		}
	}
}
