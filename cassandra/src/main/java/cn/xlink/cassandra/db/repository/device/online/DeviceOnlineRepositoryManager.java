package cn.xlink.cassandra.db.repository.device.online;

import com.datastax.driver.core.Session;

import cn.xlink.cassandra.core.type.ScopeType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class DeviceOnlineRepositoryManager {
    private DeviceOnlineRepositoryManager() {
    }

    private static final DeviceOnlineRepositoryManager singleton = new DeviceOnlineRepositoryManager();

    public static final DeviceOnlineRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session) {
        repositoryMap.put(ScopeType.Device, new DeviceOnlineByDeviceIdRepository(session));
        repositoryMap.put(ScopeType.Product, new DeviceOnlineByProductIdRepository(session));
        repositoryMap.put(ScopeType.Corp, new DeviceOnlineByCorpIdRepository(session));
    }

    private final Map<ScopeType, SuperDeviceOnlineRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperDeviceOnlineRepository getRepository(ScopeType type) {
        return repositoryMap.get(type);
    }

}
