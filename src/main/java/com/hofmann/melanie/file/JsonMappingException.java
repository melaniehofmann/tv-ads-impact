/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.file;

@SuppressWarnings("serial")
public class JsonMappingException extends RuntimeException {

	public JsonMappingException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
