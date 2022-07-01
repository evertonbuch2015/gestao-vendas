package br.com.gvendas.gestaovendas.excecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String CONST_VALIDATION_NOT_BLANK = "NotBlank";
	private static final String CONST_VALIDATION_LENGHT = "Length";
	private static final String CONST_VALIDATION_MIN = "Min";
	private static final String CONST_VALIDATION_NOT_NULL = "NotNull";
	private static final String CONST_VALIDATION_NOT_EMPTY = "NotEmpty";

	public GestaoVendasExceptionHandler() {

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = gerarListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		String strUsuario = "Recurso não encontrado!";
		String strDev = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(strUsuario, strDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String strUsuario = "Recurso não encontrado!";
		String strDev = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(strUsuario, strDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		String strUsuario = ex.getMessage();
		String strDev = ex.getMessage();
		List<Erro> erros = Arrays.asList(new Erro(strUsuario, strDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private List<Erro> gerarListaErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<Erro>();
		bindingResult.getFieldErrors().forEach(fieldErro -> {
			String strUsuario = tratarMsgErroUsuario(fieldErro);
			String strDev = fieldErro.toString();
			erros.add(new Erro(strUsuario, strDev));
		});

		return erros;
	}

	private String tratarMsgErroUsuario(FieldError fieldErro) {
		if (fieldErro.getCode().equals(CONST_VALIDATION_NOT_BLANK)
				|| fieldErro.getCode().equals(CONST_VALIDATION_NOT_NULL)) {
			return fieldErro.getDefaultMessage().concat(" é obrigatório!");
		} else if (fieldErro.getCode().equals(CONST_VALIDATION_LENGHT)) {
			return fieldErro.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres!",
					fieldErro.getArguments()[2], fieldErro.getArguments()[1]));
		} else if (fieldErro.getCode().equals(CONST_VALIDATION_MIN)) {
			return fieldErro.getDefaultMessage()
					.concat(String.format(" deve ser maior ou igual a %s!", fieldErro.getArguments()[1]));
		} else if (fieldErro.getCode().equals(CONST_VALIDATION_NOT_EMPTY)) {
			return fieldErro.getDefaultMessage()
					.concat(String.format(" deve ser informado(a)!"));
		}

		return fieldErro.toString();
	}
}
