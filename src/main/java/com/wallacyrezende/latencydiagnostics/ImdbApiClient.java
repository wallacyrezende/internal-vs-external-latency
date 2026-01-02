package com.wallacyrezende.latencydiagnostics;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Component
public class ImdbApiClient {

    private final RestClient restClient;

    public ImdbApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Observed(name = "external.imdb.movies.latency", contextualName = "IMDB get movies")
    public List<IMDBMovieDTO> getMovies() {
        return Optional.ofNullable(
                restClient.get()
                        .uri(uriBuilder ->
                                uriBuilder.path("/titles")
                                        .queryParam("types", "MOVIE")
                                        .build()
                        )
                        .retrieve()
                        .body(ImdbTitlesResponse.class)
                )
                .map(ImdbTitlesResponse::titles)
                .orElse(List.of());
    }

    public record ImdbTitlesResponse(List<IMDBMovieDTO> titles) {}

    public record IMDBMovieDTO(String primaryTitle, Integer startYear) {}
}
