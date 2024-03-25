package com.soya.movierestful.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soya.movierestful.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer >{

	boolean existsByTitle(String title);

}
