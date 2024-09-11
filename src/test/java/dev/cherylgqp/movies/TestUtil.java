package dev.cherylgqp.movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtil {

    public static Movie getMovie1() {
		Movie movie1 = new Movie();
		movie1.setImdbId("testImdbId1");;
        movie1.setTitle("Test Movie 1");
        movie1.setReleaseDate("2023-09-11");
        movie1.setGenres(Arrays.asList("Action", "Adventure"));
        movie1.setBackdrops(Arrays.asList("backdrop-1", "backdrop-2"));   
		return movie1;
	}

    public static Movie getMovie2() {
		Movie movie2 = new Movie();
		movie2.setImdbId("testImdbId2");;
        movie2.setTitle("Test Movie 2");
        movie2.setReleaseDate("2023-10-11");
        movie2.setGenres(Arrays.asList("Romance", "Adventure"));
        movie2.setBackdrops(Arrays.asList("backdrop-1", "backdrop-2"));   
		return movie2;
	}

    public static List<Movie> createMovieListForTesting() {
		List<Movie> movies = new ArrayList<>();
		movies.add(getMovie1());
        movies.add(getMovie2());
        return movies;
	}
}
