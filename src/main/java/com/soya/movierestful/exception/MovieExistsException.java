package com.soya.movierestful.exception;

public class MovieExistsException extends RuntimeException{
	
	public MovieExistsException(String message) {
		super(message);
	}
}
