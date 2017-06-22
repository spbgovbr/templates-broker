package br.gov.ans.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ErrorMessage;
import br.gov.ans.exceptions.WrappedException;
import br.gov.ans.utils.MessageUtils;

@Provider
public class WrappedExceptionHandler implements ExceptionMapper<WrappedException>{

	@Inject
	private Logger logger;
	
    @Inject
    private MessageUtils messages;
	
	@Context
	private HttpHeaders headers;
	
	public Response toResponse(WrappedException ex) {
		logger.error(ex.getEx(), ex.getEx());
		
		Status status = Status.INTERNAL_SERVER_ERROR;
		String message = messages.getMessage("erro.interno");

		if(ex.getEx() instanceof BusinessException){
			status = Status.BAD_REQUEST;
			message = ex.getEx().getMessage();
		}
		
		if(ex.getEx() instanceof ConstraintViolationException){
			status = Status.BAD_REQUEST;
			message = getViolacoes((ConstraintViolationException) ex.getEx());
		}
		
		return Response.status(status)
				.entity(new ErrorMessage(message,String.valueOf(status.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}

	public String getViolacoes(ConstraintViolationException ex){
		StringBuilder string = new StringBuilder(messages.getMessage("erro.validar"));
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		
		for(ConstraintViolation<?> violation : violations){
			string.append(violation.getPropertyPath()+": ");
			string.append(violation.getMessage()+", ");			
		}
		
		return StringUtils.left(string.toString(), (string.length() - 2));
	}
	
}
