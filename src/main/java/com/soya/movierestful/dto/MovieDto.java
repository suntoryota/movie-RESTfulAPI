package com.soya.movierestful.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
	
	@NotNull(message = "Title must not be null")
	@Size(min = 1, message = "Title must be at least 1 character long")
	private String 	title;
	
	@NotNull(message = "Description must not be null")
	@Size(min = 10, message = "Description must be at least 10 characters long")
	private String 	description; 
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Rating must be greater than 0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be less than or equal to 5")
	private Float 	rating; 
	
	
	private String 	image; 
	private LocalDateTime  created_at; 
	private LocalDateTime  updated_at; 
}
