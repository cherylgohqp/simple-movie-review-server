// package dev.cherylgqp.movies;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.RequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.assertj.core.api.Assert;
// import org.hamcrest.collection.IsCollectionWithSize;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.util.List;
// import java.util.Optional;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ContextConfiguration(classes = MoviesApplication.class)
// public class ReviewControllerTest {
//     private MockMvc mockMvc;
// 	@Mock
//     ReviewService reviewService;
// 	@InjectMocks
//     private ReviewController reviewController;
// 	@Mock private ReviewRepository reviewRepository;

//     @BeforeEach
//     public void setup() {

//         // this must be called for the @Mock annotations above to be processed
//         // and for the mock service to be injected into the controller under
//         // test.
//         MockitoAnnotations.openMocks(this);
//         this.mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
//     }

//     @Test
// 	public void testCreateReview() {
// 		Movie movie1 = TestUtil.getMovie1();
// 		Mockito.when(reviewService.createReview(Mockito.any(Review.class))).thenReturn(movie1);
		
// 		Review review1 = reviewService.createReview("Test Review 1", "testImdbId1");
// 		Assert.assertEquals(movie1.getReviewIds(), review1.getId());
// 	}
// }


package dev.cherylgqp.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation.ExecutableUpdate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import org.bson.types.ObjectId;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MoviesApplication.class)
public class ReviewControllerTest {
    private MockMvc mockMvc;
    private MockMvc mockMvc2;

    @Mock
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    MovieService movieService;

    @InjectMocks
    private ReviewController reviewController;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        this.mockMvc2 = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void testCreateReview() throws Exception {
        List<Movie> movies = TestUtil.createMovieListForTesting();
        Mockito.when(movieService.allMovies()).thenReturn(movies);
        String reviewBody = "Great movie!";
        String imdbId = "testImdbId1";
        ObjectId reviewId = new ObjectId("507f1f77bcf86cd799439011");
        Review newReview = new Review(reviewBody);
        newReview.setId(reviewId);

        when(reviewRepository.insert(any(Review.class))).thenReturn(newReview);
        when(mongoTemplate.update(eq(Movie.class)))
                .thenReturn(Mockito.mock(ExecutableUpdate.class));

        when(reviewService.createReview(reviewBody, imdbId)).thenReturn(newReview);

        mockMvc.perform(post("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reviewBody\":\"" + reviewBody + "\",\"imdbId\":\"" + imdbId + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(reviewBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isMap())
                .andDo(MockMvcResultHandlers.print());

        verify(reviewService).createReview(reviewBody, imdbId);

        // // Get the movie
        // mockMvc2.perform(get("/api/v1/movies/" + imdbId))
        // .andExpect(status().isOk())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.reviewIds").isArray())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.reviewIds").value(hasItem(reviewId.toString())))
        // .andDo(MockMvcResultHandlers.print());
    
    }
    
}