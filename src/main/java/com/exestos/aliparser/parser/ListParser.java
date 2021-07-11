package com.exestos.aliparser.parser;

import com.exestos.aliparser.http.HttpRequester;
import com.exestos.aliparser.struct.FetchResult;
import com.exestos.aliparser.struct.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListParser {

    private final int limit;

    public ListParser(int limit) {
        this.limit = limit;
    }

    public List<Item> getDataList() throws Exception {
        String requestString = "https://gpsfront.aliexpress.com/getRecommendingResults.do?widget_id=5547572&limit=10&offset={{offset}}&productIds2Top=&postback=1";
        List<URL> requests = new ArrayList<>();
        for (int offset = 0; offset < limit; offset += 10) {
            requests.add(new URL(
                    requestString.replaceAll("\\{\\{offset}}", String.valueOf(offset)))
            );
        }
        List<Item> results = new ArrayList<>();
        requests.stream().parallel().forEach(url -> {
            HttpRequester httpRequester = new HttpRequester(url);
            try {
                String response = httpRequester.call().toString();
                ObjectMapper objectMapper = new ObjectMapper();
                results.addAll(objectMapper.readValue(response, FetchResult.class).results);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return results;
    }
}
