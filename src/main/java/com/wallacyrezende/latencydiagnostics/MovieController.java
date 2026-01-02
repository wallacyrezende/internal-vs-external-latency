package com.wallacyrezende.latencydiagnostics;

import com.wallacyrezende.latencydiagnostics.ImdbApiClient.IMDBMovieDTO;
import io.micrometer.observation.annotation.Observed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Observed(name = "service.movies.endpoint.latency", contextualName = "GET /movies")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IMDBMovieDTO> movies() {
        return movieService.getMovies();
    }
}
