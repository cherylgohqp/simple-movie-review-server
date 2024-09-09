package dev.cherylgqp.movies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    /*
     * This service class uses the repository class to talk to the database and gets
     * the info regarding the movies and returns to the API layer
     */
    @Autowired
    /*
     * auto instantiate/initialize the MovieRepository class so you dont have to
     * initialize it with constructors etc.
     */
    private MovieRepository movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId) {
        // Optional lets Java know that you might return null if Id doesnt exist
        return movieRepository.findMovieByImdbId(imdbId);
    }
}
