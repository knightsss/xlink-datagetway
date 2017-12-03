package cn.xlink.data.query.cache;

import cn.xlink.data.core.utils.DataType;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadata;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataFieldEntity;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataJoinEntity;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataSourceEntity;

import java.util.*;

/**
 * Created by Ghold on 2017/12/3.
 */
public class DatasetCacheStore {
    private String engine;
    private Map<String, String> sourceMap = new HashMap<>();
    private DatasetMetadataSourceEntity defaultSource;
    private Map<String, DatasetMetadataFieldEntity> fieldMap = new HashMap<>();
    private Map<String, DatasetMetadataFieldEntity> refMap = new HashMap<>();
    private Map<String, DatasetMetadataFieldEntity> timeMap = new HashMap<>();
    private DatasetMetadataFieldEntity defaultTime;
    private List<String> preFilters = new ArrayList<>();
    private List<DatasetMetadataJoinEntity> joins = new ArrayList<>();

    public DatasetCacheStore() {
    }

    public DatasetCacheStore(DatasetMetadata dataset) {
        this.engine = dataset.getEngine();
        // 设置source
        if (dataset.getSources() != null && dataset.getSources().size() > 0) {
            for (DatasetMetadataSourceEntity source: dataset.getSources()) {
                if (defaultSource == null) defaultSource = source;
                sourceMap.put(source.getName(), source.getSource());
            }
        }

        // 设置joins
        joins = dataset.getJoins();

        // 由于数据集的定义不基于企业或产品，而且这些分租的信息直接带入查询条件的中fitler并不友好。因此在数据集中预设一些过滤字段
        if (dataset.getFilters() != null && dataset.getFilters().size() > 0) {
            for (DatasetMetadataFieldEntity filter : dataset.getFilters()) {
                preFilters.add(filter.getName());
            }
        }

        // 时间字段在数据平台是必须的，单独处理的原因是，SQL对于时间的过滤需要特殊的格式，如必须加入Timestamp保留字
        if (dataset.getTimes() != null && dataset.getTimes().size() > 0) {
            for (DatasetMetadataFieldEntity entity : dataset.getTimes()) {
                if (defaultTime == null) defaultTime = entity;
                timeMap.put(entity.getName(), entity);
            }
        }

        for (DatasetMetadataFieldEntity entity: dataset.getFields()) {
            DataType dataType = DataType.fromType(entity.getType());
            switch (dataType) {
                case Datapoint:
                    // Datapoint类型为引用类型
                    refMap.put(entity.getName(), entity);
                    break;
                default:
                    fieldMap.put(entity.getName(), entity);
            }
        }
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public DatasetMetadataSourceEntity getDefaultSource() {
        return defaultSource;
    }

    public void setDefaultSource(DatasetMetadataSourceEntity defaultSource) {
        this.defaultSource = defaultSource;
    }

    public Map<String, String> getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(Map<String, String> sourceMap) {
        this.sourceMap = sourceMap;
    }

    public Map<String, DatasetMetadataFieldEntity> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, DatasetMetadataFieldEntity> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, DatasetMetadataFieldEntity> getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(Map<String, DatasetMetadataFieldEntity> timeMap) {
        this.timeMap = timeMap;
    }

    public DatasetMetadataFieldEntity getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(DatasetMetadataFieldEntity defaultTime) {
        this.defaultTime = defaultTime;
    }

    public List<DatasetMetadataJoinEntity> getJoins() {
        return joins;
    }

    public void setJoins(List<DatasetMetadataJoinEntity> joins) {
        this.joins = joins;
    }

    public Map<String, DatasetMetadataFieldEntity> getRefMap() {
        return refMap;
    }

    public void setRefMap(Map<String, DatasetMetadataFieldEntity> refMap) {
        this.refMap = refMap;
    }

    public List<String> getPreFilters() {
        return preFilters;
    }

    public void setPreFilters(List<String> preFilters) {
        this.preFilters = preFilters;
    }
}
