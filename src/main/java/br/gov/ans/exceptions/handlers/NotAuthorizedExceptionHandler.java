package br.gov.ans.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.ErrorMessage;

@Provider
public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException>{
	
	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;

	public Response toResponse(NotAuthorizedException ex) {
		logger.error(ex, ex);		
		 		
		return Response.status(Status.UNAUTHORIZED)
				.entity(new ErrorMessage(ex.getMessage(),String.valueOf(Status.UNAUTHORIZED.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
