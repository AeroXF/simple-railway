package com.fengyunjie.railway;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorConfiguration implements EmbeddedServletContainerCustomizer {

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
		container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error"));
		container.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY, "/error"));
		
	}

	
	

}
