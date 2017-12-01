package cn.xlink.cassandra.db.repository.user.snapshot;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class UserInfoSnapshotRepositoryManager {
    private UserInfoSnapshotRepositoryManager() {
    }

    private static final UserInfoSnapshotRepositoryManager singleton = new UserInfoSnapshotRepositoryManager();

    public static final UserInfoSnapshotRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session){
        repositoryMap.put(ScopeType.User,new UseInfoSnapshotByUserIdRepository(session));
        repositoryMap.put(ScopeType.Corp,new UseInfoSnapshotByCorpIdRepository(session));
    }

    private final Map<ScopeType,SuperUserInfoSnapshotRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperUserInfoSnapshotRepository getRepository(ScopeType type){
        return repositoryMap.get(type);
    }

}
