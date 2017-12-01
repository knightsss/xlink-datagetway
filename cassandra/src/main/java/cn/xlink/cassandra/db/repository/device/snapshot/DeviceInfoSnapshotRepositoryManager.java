package cn.xlink.cassandra.db.repository.device.snapshot;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class DeviceInfoSnapshotRepositoryManager {
    private DeviceInfoSnapshotRepositoryManager() {
    }

    private static final DeviceInfoSnapshotRepositoryManager singleton = new DeviceInfoSnapshotRepositoryManager();

    public static final DeviceInfoSnapshotRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session){
        repositoryMap.put(ScopeType.Device,new DeviceInfoSnapshotByDeviceIdRepository(session));
        repositoryMap.put(ScopeType.Product,new DeviceInfoSnapshotByProductIdRepository(session));
        repositoryMap.put(ScopeType.Corp,new DeviceInfoSnapshotByCorpIdRepository(session));
    }

    private final Map<ScopeType,SuperDeviceInfoSnapshotRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperDeviceInfoSnapshotRepository getRepository(ScopeType type){
        return repositoryMap.get(type);
    }

}
