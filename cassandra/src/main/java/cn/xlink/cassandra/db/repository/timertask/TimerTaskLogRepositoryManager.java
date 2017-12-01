package cn.xlink.cassandra.db.repository.timertask;

import cn.xlink.cassandra.core.type.TimerTaskLogType;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/8/16.
 */
public class TimerTaskLogRepositoryManager {
    private TimerTaskLogRepositoryManager() {
    }

    private static final TimerTaskLogRepositoryManager singleton = new TimerTaskLogRepositoryManager();

    public static final TimerTaskLogRepositoryManager instance() {
        return singleton;
    }

    public final void init(Session session) {
        repositoryMap.put(TimerTaskLogType.Default, new DefaultTimerTaskLogRepository(session));
        repositoryMap.put(TimerTaskLogType.Owner, new TimerTaskLogByOwnerRepository(session));
        repositoryMap.put(TimerTaskLogType.Status, new TimerTaskLogByStatusRepository(session));
    }

    private final Map<TimerTaskLogType, SuperTimerTaskLogRepository> repositoryMap = new ConcurrentHashMap<>();

    public final SuperTimerTaskLogRepository getRepository(TimerTaskLogType type) {
        return repositoryMap.get(type);
    }

}
