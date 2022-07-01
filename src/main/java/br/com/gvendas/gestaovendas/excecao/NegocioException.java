package br.com.gvendas.gestaovendas.excecao;

public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 4429069137234572463L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}
}
