package cn.xlink.cassandra.db.repository.avs;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class AvsLogRepositoryManager {
    private AvsLogRepositoryManager() {
    }

    private static final AvsLogRepositoryManager singleton = new AvsLogRepositoryManager();

    public static final AvsLogRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session){
        repositoryMap.put(ScopeType.Device,new AvsLogByDeviceIdRepository(session));
        repositoryMap.put(ScopeType.Product,new AvsLogByProductIdRepository(session));
        repositoryMap.put(ScopeType.Corp,new AvsLogByCorpIdRepository(session));
    }

    private final Map<ScopeType,SuperAvsLogRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperAvsLogRepository getRepository(ScopeType type){
        return repositoryMap.get(type);
    }

}
