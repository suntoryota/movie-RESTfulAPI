package com.soya.movierestful.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorMessageConfig {

    @Value("${error.message}")
    private String errorMessage; // Spring will inject this value from properties
}


