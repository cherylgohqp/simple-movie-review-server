package dev.cherylgqp.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
       /*
     * auto instantiate/initialize the MovieRepository class so you dont have to
     * initialize it with constructors etc.
     */
    private ReviewRepository reviewRepository;
    //Note for every new review, we need to add it to our database too, hence we need a reference to reviewrepository

    @Autowired
    private MongoTemplate mongoTemplate;
    /* sometimes the operation might be too complex to use a repository so you can create a template and use it to create a dynamic query and talk to the database to  */
    public Review createReview(String reviewBody, String imdbId) {
        /*first look for the movie with the given Imdbid, then create a new review and associate that review with the movie found */
        Review review = reviewRepository.insert(new Review(reviewBody)); //save the review on insert => .insert returns the data you just pushed into your db //creates a new review
        //repository works as a intermediary layer between the service class and the database!
        
        mongoTemplate.update(Movie.class) //perform an update call on the Movie class (since each movie has an array of reviewIds)
        .matching(Criteria.where("imdbId").is(imdbId)) //to find the movie where the imdbid of the movie in the database matches the imdbid that we have received from the user
        .apply(new Update().push("reviewIds").value(review)) //to apply this update to the database
        .first(); //to make sure that we are getting a single movie and we are updating that
        
        return review;
    }
}
