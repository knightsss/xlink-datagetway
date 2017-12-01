package cn.xlink.cassandra.db.repository.user.auth;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class UserAuthRepositoryManager {
    private UserAuthRepositoryManager() {
    }

    private static final UserAuthRepositoryManager singleton = new UserAuthRepositoryManager();

    public static final UserAuthRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session){
        repositoryMap.put(ScopeType.User,new UserAuthByUserIdRepository(session));
        repositoryMap.put(ScopeType.Corp,new UserAuthByCorpIdRepository(session));
    }

    private final Map<ScopeType,SuperUserAuthRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperUserAuthRepository getRepository(ScopeType type){
        return repositoryMap.get(type);
    }

}
