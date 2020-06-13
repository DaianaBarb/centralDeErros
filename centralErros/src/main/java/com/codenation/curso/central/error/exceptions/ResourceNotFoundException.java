package com.codenation.curso.central.error.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resourceName) {
		super("Resource:"+resourceName+"Not found");
	}
}
