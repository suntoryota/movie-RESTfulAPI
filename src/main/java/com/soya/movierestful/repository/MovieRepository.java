package com.soya.movierestful.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.soya.movierestful.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer >{

	boolean existsByTitle(String title);

}
