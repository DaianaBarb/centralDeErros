package com.mballen.curso.boot.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resourceName) {
		super("Resource:"+resourceName+"Not found");
	}
}
