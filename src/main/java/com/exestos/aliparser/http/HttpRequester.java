package com.exestos.aliparser.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

public class HttpRequester implements Callable<StringBuffer> {

    private final URL url;

    public HttpRequester(URL url) {
        this.url = url;
    }

    @Override
    public StringBuffer call() throws Exception {
        URLConnection con = url.openConnection();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }
    }
}
