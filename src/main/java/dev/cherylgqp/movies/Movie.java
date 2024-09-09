package dev.cherylgqp.movies;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "movies") // to tell the framework that this class represents each document in the movies
// collection
@Data
/*
 * Combines several other Lombok annotations to reduce boilerplate code.
 * It is a shortcut for:
 * 
 * @ToString: Generates a toString() method.
 * 
 * @EqualsAndHashCode: Generates equals() and hashCode() methods.
 * 
 * @Getter: Generates getters for all fields.
 * 
 * @Setter: Generates setters for all non-final fields.
 * 
 * @RequiredArgsConstructor: Generates a constructor with required (final)
 * fields.
 */
@AllArgsConstructor
/*
 * Generates a constructor that takes one argument for every field in the class.
 * This includes all fields, regardless of whether they are final or not.
 */
@NoArgsConstructor
/*
 * Generates a no-arguments (default) constructor.
 * Useful when you need to create an instance of a class without setting any
 * fields initially.
 */

public class Movie {
    @Id
    /*
     * to let the framework know that this property should be treated as the unique
     * identifier for each movie in the database
     */
    private ObjectId id; // id of the movie
    private String imdbId; // imdb Id of the movie
    private String title; // movie title
    private String releaseDate; // movie release date
    private String trailerLink; // url for movie trailer
    private List<String> genres; // is a list of strings since there can be multiple genres
    private List<String> backdrops; // movie backdrops used in the FE side
    private List<String> poster; // movie backdrops used in the FE side

    @DocumentReference // this field references another document called review
    /*
     * this will cause the DB to store only the IDs of the review and the reviews
     * will be in a seperate collection
     * ie. manual reference rs
     * eg. Instead of embedding the entire review document, @DocumentReference
     * stores
     * only the review ID in the movie document. When you access the review field,
     * Spring Data MongoDB fetches the full review document based on the stored ID.
     */

    private List<Review> reviewIds; // matches the reviewIds in mongodb

    // one to many relationship -> one movie has many reviews
}
