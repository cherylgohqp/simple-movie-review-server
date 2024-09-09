package dev.cherylgqp.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


//this is the main file to run the appolication
@SpringBootApplication
@RestController // another annotation to let the framework knows that this class is actually a
				// Rest API controller and not just another class
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	// commented code is just a quick demo to test if our endpoint is working
	// @GetMapping("/") // it lets the frameework knows that this method here is a
	// get endpoint
	// public String apiRoot() {
	// return "Hello World!";
	// }
	
}
