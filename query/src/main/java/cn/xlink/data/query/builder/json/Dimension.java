package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class Dimension {
    @JsonProperty("type")
    private String type;

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("outputName")
    private String outputName;

    @JsonProperty("outputType")
    private String outputType;

    public Dimension(String type, String dimension, String outputName, String outputType) {
        this.type = type;
        this.dimension = dimension;
        this.outputName = outputName;
        this.outputType = outputType;
    }
}
