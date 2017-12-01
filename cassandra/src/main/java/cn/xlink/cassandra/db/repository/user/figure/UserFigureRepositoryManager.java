package cn.xlink.cassandra.db.repository.user.figure;

import cn.xlink.cassandra.core.type.ScopeType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class UserFigureRepositoryManager {
    private UserFigureRepositoryManager() {
    }

    private static final UserFigureRepositoryManager singleton = new UserFigureRepositoryManager();

    public static final UserFigureRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session){
        repositoryMap.put(ScopeType.User,new UserFigureByUserIdRepository(session));
        repositoryMap.put(ScopeType.Corp,new UserFigureByCorpIdRepository(session));
    }

    private final Map<ScopeType,SuperUserFigureRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperUserFigureRepository getRepository(ScopeType type){
        return repositoryMap.get(type);
    }

}
