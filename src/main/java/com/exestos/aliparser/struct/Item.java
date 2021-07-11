package com.exestos.aliparser.struct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @JsonProperty("productTitle")
    public String title;
    @JsonProperty("minPrice")
    public String price;
    @JsonProperty("oriMinPrice")
    public String noDiscountPrice;
    @JsonProperty("productDetailUrl")
    public String detail;
    @JsonProperty("productImage")
    public String image;
    @JsonProperty("productPositiveRate")
    public String rate;
    @JsonProperty("productAverageStar")
    public String stars;
}
