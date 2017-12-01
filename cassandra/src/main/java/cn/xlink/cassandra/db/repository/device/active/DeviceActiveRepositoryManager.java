package cn.xlink.cassandra.db.repository.device.active;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class DeviceActiveRepositoryManager {
    private DeviceActiveRepositoryManager() {
    }

    private static final DeviceActiveRepositoryManager singleton = new DeviceActiveRepositoryManager();

    public static final DeviceActiveRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session) {
        repositoryMap.put(ScopeType.Device, new DeviceActiveByDeviceIdRepository(session));
        repositoryMap.put(ScopeType.Product, new DeviceActiveByProductdRepository(session));
        repositoryMap.put(ScopeType.Corp, new DeviceActiveByCorpIdRepository(session));
    }

    private final Map<ScopeType, SuperDeviceActiveRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperDeviceActiveRepository getRepository(ScopeType type) {
        return repositoryMap.get(type);
    }

}
