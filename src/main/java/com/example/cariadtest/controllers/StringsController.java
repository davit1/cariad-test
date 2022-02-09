package com.example.cariadtest.controllers;

import com.example.cariadtest.models.NumbersList;
import com.example.cariadtest.services.StringsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * The single controller of the web service.
 * Accepts only http GET method on /strings endpoint.
 * Checks for params called 'u'. If no params, returns
 * the empty NumbersList object right away
 * otherwise sends the request through to the stringsService
 */
@RestController
@RequestMapping("strings")
public class StringsController {

    private static final Logger logger = LoggerFactory.getLogger(StringsController.class);

    @Autowired
    private StringsService stringsService;

    @GetMapping
    public NumbersList returnStrings(@RequestParam(required = false) List<String> u) {
        if (u == null) {
            logger.info("Got no parameters, returning an object with an empty list");
            return new NumbersList();
        } else {
            return stringsService.processURLs(u);
        }
    }

}
