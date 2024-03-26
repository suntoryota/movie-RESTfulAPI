package com.soya.movierestful.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soya.movierestful.entity.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	 
  boolean existsByTitle(String title);

Optional<Movie> findByTitle(String title);

  
}

