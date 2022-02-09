package com.example.cariadtest.services;

import com.example.cariadtest.models.NumbersList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/*
 * The service that sends out all request that have to be sent out
 * and merges the results of all objects into one object that
 * is returned to the caller of the controller.
 */
@Service
public class StringsService {

    private static final Logger logger = LoggerFactory.getLogger(StringsService.class);

    private final CariadLookupService cariadLookupService;

    public StringsService(CariadLookupService cariadLookupService) {
        this.cariadLookupService = cariadLookupService;
    }

    /*
     * Get a List of urls and filters out all malformed URLs
     * It also removes duplicate URLs and then returns the new List.
     */
    private List<String> filterMalformedAndRepeatedURLs(List<String> urls) {
        return urls.stream().distinct().filter(url -> {
            try {
                new URL(url).toURI();
                return true;
            } catch (URISyntaxException | MalformedURLException e) {
                logger.info("Malformed url: " + url + " will be filtered out.");
                return false;
            }
        }).collect(Collectors.toList());
    }

    /*
     * Gets back the NumbersList object that is expected
     */
    public NumbersList processURLs(List<String> urls) {
        List<String> filteredUrlsList = filterMalformedAndRepeatedURLs(urls);
        // Trigger async requests to all URLs and save the CompletableFutures of them.
        List<CompletableFuture<NumbersList>> futures = new ArrayList<>();
        for (String url: filteredUrlsList) {
            futures.add(cariadLookupService.queryCariadService(url));
        }
        // Save all results of the CompletableFutures into a List
        List<NumbersList> numbersListLists = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        // List that will temporarily save all strings that our requests delivered
        List<String> resultList = new ArrayList<>();
        NumbersList resultObject = new NumbersList();
        // Fill the resultList with all strings that were delivered
        numbersListLists.stream().forEach(x -> resultList.addAll(x.getStrings()));
        // Sort resultList and delete all duplicate entries, set the result to strings attribute of resultObject.
        resultObject.setStrings(resultList.stream().distinct().sorted().collect(Collectors.toList()));
        return resultObject;
    }
}
