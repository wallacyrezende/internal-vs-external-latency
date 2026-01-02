package com.wallacyrezende.latencydiagnostics;

import com.wallacyrezende.latencydiagnostics.ImdbApiClient.IMDBMovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final ImdbApiClient imdbApiClient;
    private static final int INTERNAL_LATENCY_MS = 900;

    public MovieService(ImdbApiClient imdbApiClient) {
        this.imdbApiClient = imdbApiClient;
    }

    public List<IMDBMovieDTO> getMovies() {
        simulateInternalLatency();
        return imdbApiClient.getMovies();
    }

    private void simulateInternalLatency() {
        try {
            Thread.sleep(INTERNAL_LATENCY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
