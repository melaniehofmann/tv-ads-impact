/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.file;

@SuppressWarnings("serial")
public class InvalidFileException extends RuntimeException {

	public InvalidFileException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
