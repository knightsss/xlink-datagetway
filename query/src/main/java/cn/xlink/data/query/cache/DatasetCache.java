package cn.xlink.data.query.cache;

import cn.xlink.data.metadata.datasetMetadata.DatasetMetadata;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ghold on 2017/12/3.
 */
public class DatasetCache {
    private Map<String, DatasetCacheStore> datasetMap;
    private static DatasetCache cache = new DatasetCache();

    private DatasetCache() {
        this.datasetMap = new HashMap<>();
    }

    public static DatasetCache getInstance() {
        if (cache == null) {
            cache = new DatasetCache();
        }
        return cache;
    }

    public void init(DatasetMetadataService datasetService) {
        for (DatasetMetadata item: datasetService.findAll()) {
            datasetMap.put(item.getName(), new DatasetCacheStore(item));
        }
    }

    public DatasetCacheStore getDataset(String key) {
        return datasetMap.getOrDefault(key, null);
    }
}
