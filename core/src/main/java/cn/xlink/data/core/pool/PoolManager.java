package cn.xlink.data.core.pool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ghold on 2017/12/9.
 */
public class PoolManager {
    private Map<String, ConnectionPool> poolMap;
    private static PoolManager manager = new PoolManager();

    private PoolManager() {
        this.poolMap = new HashMap<>();
    }

    public static PoolManager getInstance() {
        if (manager == null) {
            manager = new PoolManager();
        }
        return manager;
    }

    public void register(String key, ConnectionPool pool) {
        this.poolMap.put(key, pool);
    }

    public ConnectionPool getPool(String key) {
        return poolMap.getOrDefault(key, null);
    }
}
