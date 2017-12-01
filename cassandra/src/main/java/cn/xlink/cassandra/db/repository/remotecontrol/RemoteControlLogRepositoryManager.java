package cn.xlink.cassandra.db.repository.remotecontrol;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class RemoteControlLogRepositoryManager {
    private RemoteControlLogRepositoryManager() {
    }

    private static final RemoteControlLogRepositoryManager singleton = new RemoteControlLogRepositoryManager();

    public static final RemoteControlLogRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session) {
        repositoryMap.put(ScopeType.Device, new RemoteControlLogByDeviceIdRepository(session));
        repositoryMap.put(ScopeType.User, new RemoteControlLogByUserIdRepository(session));
    }

    private final Map<ScopeType, SuperRemoteControlLogRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperRemoteControlLogRepository getRepository(ScopeType type) {
        return repositoryMap.get(type);
    }

}
