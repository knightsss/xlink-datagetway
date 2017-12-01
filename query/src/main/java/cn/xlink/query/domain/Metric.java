package cn.xlink.query.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ghold on 2017/8/23.
 */
public class Metric {
    private String name;
    private String type;
    private String displayName;

    public static final String METRIC_COUNT = "count";
    public static final String METRIC_AVG = "avg";
    public static final String METRIC_SUM = "sum";
    public static final String METRIC_DISTINCT = "distinct";

    private static final Set<String> metric = new HashSet<String>(Arrays.asList(METRIC_COUNT, METRIC_AVG, METRIC_SUM, METRIC_DISTINCT));
    private static final Set<String> jsonMetric = new HashSet<String>(Arrays.asList(METRIC_AVG, METRIC_SUM));

    public Metric() {
    }

    public Metric(String name, String type) {
        this.name = name;
        this.type = type;

        switch (type.toLowerCase()) {
            case METRIC_COUNT:
                if (name.equals("*")) {
                    displayName = "count_";
                } else {
                    displayName = "count_" + name.trim();
                }
                break;
            case METRIC_SUM:
            case METRIC_AVG:
                displayName = type.toLowerCase() + "_" + name.trim();
                break;
            case METRIC_DISTINCT:
                displayName = "distinct_count";
                break;
            default:
                displayName = "unknown";
        }
    }

    public boolean invalid(){
        return (name == null || name.equals("")
                || type == null || !metric.contains(type.toLowerCase()));
    }

    boolean useJson() {
        return !invalid() && jsonMetric.contains(type.toLowerCase());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
