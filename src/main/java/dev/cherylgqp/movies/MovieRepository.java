package dev.cherylgqp.movies;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository // so that the framework knows that this class is a repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    /*
     * This repository class is like a database access layer for our api. It does
     * the job actually talking to the database and getting the data back.
     */

    Optional<Movie> findMovieByImdbId(String imdbId); // Springboot datamongodb is intelligent enough to know what this
                                                      // method does
}
