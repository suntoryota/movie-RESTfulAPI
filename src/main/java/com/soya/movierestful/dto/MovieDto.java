package com.soya.movierestful.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "FILM_TBL")
public class MovieDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    
    @NotNull(message = "title is mandatory")
    @NotBlank(message = "title is mandatory")
    @NotEmpty(message = "title is mandatory")
	@Size(min = 1, message = "Title must be at least 1 character long")
    private String title;
    
    @NotNull(message = "description is mandatory")
    @NotBlank(message = "description is mandatory")
    @NotEmpty(message = "description is mandatory")
	@Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;
    

	@DecimalMin(value = "0.0", inclusive = false, message = "Rating must be greater than 0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be less than or equal to 5")
    private float rating;
    
    private String image;

    @JsonIgnore  
    private LocalDateTime created_at;
    @JsonIgnore 
    private LocalDateTime updated_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = this.created_at;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public String getCreatedAt() {
        return formatDateTime(created_at);
    }

    public String getUpdatedAt() {
        return formatDateTime(updated_at);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
