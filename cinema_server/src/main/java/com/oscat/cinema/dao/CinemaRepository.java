package com.oscat.cinema.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oscat.cinema.dto.SearchCinemaForBook;
import com.oscat.cinema.entity.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

	@Query("SELECT DISTINCT NEW com.oscat.cinema.dto.SearchCinemaForBook(c.cinemaId, c.cinemaName)"
			+ " FROM Cinema c " +
	        "JOIN c.screeningRooms sr " +
	        "JOIN sr.showTimes st " +
	        "WHERE st.movie.movieId = :movieId AND "
	        + "st.showDateAndTime >= :startDate AND st.showDateAndTime <= :endDate ")
	List<SearchCinemaForBook> findCinemasByMovieId(@Param("movieId") UUID movieId, 
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate);
	
}
