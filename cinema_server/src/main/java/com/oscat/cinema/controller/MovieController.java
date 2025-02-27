package com.oscat.cinema.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oscat.cinema.dao.MovieRepository;
import com.oscat.cinema.entity.Movie;

import jakarta.transaction.Transactional;

@RestController
public class MovieController {

	@Autowired
	private MovieRepository movRepo;

	@PostMapping("/movie/add")
	public Movie postAddMovie(@RequestBody Movie mov) {

		return movRepo.save(mov);

	}

	@PostMapping("/movie/addList")
	public List<Movie> addListMovie(@RequestBody List<Movie> MovieList) {
		return movRepo.saveAll(MovieList);
	}

	@GetMapping("movie/byName/{movieName}")
	public ResponseEntity<?> findBymovieName(@PathVariable String movieName) {
		Optional<Movie> optional = movRepo.findBymovieName(movieName);

		if (optional.isPresent()) {
			Movie result = optional.get();
			return new ResponseEntity<Movie>(result, null, HttpStatus.OK);
		}

		return new ResponseEntity<String>("沒有這筆資料", null, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/movies")
	public List<Movie> findAllMovie() {
		return movRepo.findAll();
	}

	@DeleteMapping("/movie/delete")
	public String deleteMovieById(@RequestParam UUID movieId) {
		Optional<Movie> optional = movRepo.findById(movieId);

		if (optional.isEmpty()) {
			return "無此筆資料";
		}

		movRepo.deleteById(movieId);

		return "已刪除";
	}

	@Transactional
	@PutMapping("/movie/update")
	public String updateMovieById(@RequestParam("movieId") UUID movieId, @RequestParam("newName") String newName,
			@RequestParam("newType") String newType, @RequestParam("newMovieStatus") String newMovieStatus) {
		Optional<Movie> optional = movRepo.findById(movieId);

		if (optional.isPresent()) {
			Movie movie = optional.get();
			movie.setMovieName(newName);
			movie.setMovieType(newType);
			movie.setMovieStatus(newMovieStatus);
			return "修改完成";
		}

		return "無此筆資料";
	}

	@GetMapping("/movie/page/{pageNumber}")
	public Page<Movie> findMovieByPage(@PathVariable Integer pageNumber) {
		PageRequest pgb = PageRequest.of(pageNumber - 1, 2, Sort.Direction.ASC, "movieId");
		Page<Movie> page = movRepo.findAll(pgb);
		return page;

	}

	@GetMapping("/movie/name")
	public List<Movie> findMovieByNameNativeQuery(@RequestParam("n") String movieName) {
		return movRepo.findMovieByNameNativeQuery(movieName);
	}

	@GetMapping("/movie/nameLike")
	public List<Movie> findMovieByNameLike(@RequestParam("n") String name) {
		return movRepo.findMovieByNameLike(name);
	}

}
