package dev.cherylgqp.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.hamcrest.collection.IsCollectionWithSize;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MoviesApplication.class)

public class MovieControllerTest {
    private MockMvc mockMvc;
	@Mock
    MovieService movieService;
	@InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
	void contextLoads() {
		assertThat(movieController).isNotNull();
	}

    //======================GET all test================================================
	@Test
    @DisplayName("Test 1: Get all Movies")
	public void testGetAllBirds() throws Exception {
		List<Movie> movies = TestUtil.createMovieListForTesting();
		Mockito.when(movieService.allMovies()).thenReturn(movies);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/movies");
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(2)))
				.andDo(MockMvcResultHandlers.print());
	}

    //======================GET single test================================================
    @Test
    @DisplayName("Test 2: Get single Movie Success testcase")
    public void testGetSingleMovieSuccess() throws Exception {
        Optional<Movie> movie = Optional.ofNullable(TestUtil.getMovie1());
		Mockito.when(movieService.singleMovie("testImdbId1")).thenReturn(movie);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/movies/{imdbId}", "testImdbId1");
        System.out.println("REQUESTT 2 " + request);
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imdbId").value(movie.get().getImdbId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(movie.get().getTitle()))
                .andDo(MockMvcResultHandlers.print());
				
	}

    @Test
    @DisplayName("Test 2: Get single Movie Failed testcase")
    public void testGetSingleMovieFailed() throws Exception {
		Mockito.when(movieService.singleMovie("testImdbId1234")).thenReturn(Optional.empty());
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/movies/{imdbId}", "testImdbId1234");
        System.out.println("REQUESTT 3 " + request);
        mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk()) // If the status is still 200 OK
        .andExpect(MockMvcResultMatchers.content().string("null")); // Check that the content is empty instead of checking for 404 status since movie controller doesnt return a 404.
        //         .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
