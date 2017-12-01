package cn.xlink.cassandra.db.repository.device.offline;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class DeviceOfflineRepositoryManager {
    private DeviceOfflineRepositoryManager() {
    }

    private static final DeviceOfflineRepositoryManager singleton = new DeviceOfflineRepositoryManager();

    public static final DeviceOfflineRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session){
        repositoryMap.put(ScopeType.Device,new DeviceOfflineByDeviceIdRepository(session));
        repositoryMap.put(ScopeType.Product,new DeviceOfflineByProductIdRepository(session));
        repositoryMap.put(ScopeType.Corp,new DeviceOfflineByCorpIdRepository(session));
    }

    private final Map<ScopeType,SuperDeviceOfflineRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperDeviceOfflineRepository getRepository(ScopeType type){
        return repositoryMap.get(type);
    }

}
