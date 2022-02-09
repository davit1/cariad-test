package com.example.cariadtest.models;

import java.util.ArrayList;
import java.util.List;

/*
 * This class is used to represent the data we will retrieve
 * from the services, that will be called.
 */
public class NumbersList {

    private List<String> strings;

    public NumbersList() {
        strings = new ArrayList<>();
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public String toString() {
        return "NumbersList{" +
                "strings=" + strings +
                '}';
    }
}
