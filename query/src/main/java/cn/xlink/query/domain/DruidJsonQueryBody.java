package cn.xlink.query.domain;

import cn.xlink.query.builder.json.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Ghold on 2017/8/22.
 */
public class DruidJsonQueryBody {
    @JsonProperty("queryType")
    private String queryType;

    @JsonProperty("dataSource")
    private String dataSource;

    @JsonProperty("intervals")
    private List<String> intervals;

    @JsonProperty("granularity")
    private String granularity;

    @JsonProperty("dimensions")
    private List<Dimension> dimensions;

    @JsonProperty("filter")
    private DimFilter filter;

    @JsonProperty("PagingSpec")
    private cn.xlink.query.builder.json.PagingSpec PagingSpec;

    @JsonProperty("aggregations")
    private List<Aggregation> aggregations;

    @JsonProperty("postAggregations")
    private List<PostAggregation> postAggregations;

    @JsonProperty("limitSpec")
    private Limit limitSpec;

    @JsonProperty("having")
    private HavingSpec having;

    public DruidJsonQueryBody(){}

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<String> intervals) {
        this.intervals = intervals;
    }

    public DimFilter getFilter() {
        return filter;
    }

    public void setFilter(DimFilter filter) {
        this.filter = filter;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public PagingSpec getPagingSpec() {
        return PagingSpec;
    }

    public void setPagingSpec(PagingSpec pagingSpec) {
        PagingSpec = pagingSpec;
    }

    public List<Aggregation> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<Aggregation> aggregations) {
        this.aggregations = aggregations;
    }

    public List<PostAggregation> getPostAggregations() {
        return postAggregations;
    }

    public void setPostAggregations(List<PostAggregation> postAggregations) {
        this.postAggregations = postAggregations;
    }

    public Limit getLimitSpec() {
        return limitSpec;
    }

    public void setLimitSpec(Limit limitSpec) {
        this.limitSpec = limitSpec;
    }

    public HavingSpec getHaving() {
        return having;
    }

    public void setHaving(HavingSpec having) {
        this.having = having;
    }
}
