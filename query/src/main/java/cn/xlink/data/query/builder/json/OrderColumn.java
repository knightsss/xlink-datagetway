package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class OrderColumn {
    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("dimensionOrder")
    private String dimensionOrder;

    public OrderColumn(String dimension, String direction) {
        this.dimension = dimension;
        this.direction = direction;
    }

    public OrderColumn(String dimension, String direction, String dimensionOrder) {
        this.dimension = dimension;
        this.direction = direction;
        this.dimensionOrder = dimensionOrder;
    }
}
