package com.example.cariadtest.runners;

import com.example.cariadtest.models.NumbersList;
import com.example.cariadtest.services.CariadLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/*
 * CariadRunner is executed at a startup of the service
 * just to show that the call is executed asynchronously.
 * Can be disabled by removing @Component annotation.
 */
//@Component
public class CariadRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CariadRunner.class);

    private final CariadLookupService cariadLookupService;

    public CariadRunner(CariadLookupService cariadLookupService) {
        this.cariadLookupService = cariadLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<NumbersList> r1 = cariadLookupService.queryCariadService("http://127.0.0.1:8090/primes");
        CompletableFuture<NumbersList> r2 = cariadLookupService.queryCariadService("http://127.0.0.1:8090/fibo");
        CompletableFuture<NumbersList> r3 = cariadLookupService.queryCariadService("http://127.0.0.1:8090/rand");

        CompletableFuture.allOf(r1,r2,r3).join();

        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + r1.get());
        logger.info("--> " + r2.get());
        logger.info("--> " + r3.get());
    }

}
