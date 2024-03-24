package com.soya.movierestful.exception;

public class MovieNotFoundException extends RuntimeException{

	public MovieNotFoundException(String message) {
		super(message);
	}
	
}
