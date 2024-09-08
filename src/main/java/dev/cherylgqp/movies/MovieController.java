package dev.cherylgqp.movies;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movies") // root of api

// API Layer -> concerns about getting the req from the user and returning the
// response
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        // uses a service class and delegating the fetching of all the movies from the
        // database and returning it to the API layer
        // API Layer doesnt know what is going on in the service class.
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}") // To search a movie by ID
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        /*
         * note: @PathVariable lets the framework knows that we are passing the info we
         * get from getMapping as a PathVariable and that we want to convert that to an
         * ObjectId id
         */
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

}
