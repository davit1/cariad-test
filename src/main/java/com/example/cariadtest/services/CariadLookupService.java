package com.example.cariadtest.services;

import com.example.cariadtest.models.NumbersList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;


/*
 * This service is responsible for execution of
 * asynchronous requests to the services that have to be called.
 */
@Service
public class CariadLookupService {

    private static final Logger logger = LoggerFactory.getLogger(CariadLookupService.class);

    private final RestTemplate restTemplate;

    public CariadLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(100))
                .setReadTimeout(Duration.ofMillis(500))
                .build();
    }

    @Async
    public CompletableFuture<NumbersList> queryCariadService(String url) {
        logger.info("Querying " + url);
        try {
            NumbersList results = restTemplate.getForObject(url, NumbersList.class);
            return CompletableFuture.completedFuture(results);
        } catch (Exception e) {
            logger.error("Querying " + url + " will return an empty List of strings", e);
        }
        return CompletableFuture.completedFuture(new NumbersList());
    }

}
