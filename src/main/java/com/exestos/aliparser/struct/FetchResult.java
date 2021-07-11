package com.exestos.aliparser.struct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchResult {
    public List<Item> results;
}
