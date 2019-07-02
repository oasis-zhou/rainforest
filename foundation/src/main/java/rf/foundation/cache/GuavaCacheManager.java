package rf.foundation.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CacheManagerImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@Service
public class GuavaCacheManager {

    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(1000) // 设置缓存的最大容量
            .expireAfterAccess(10, TimeUnit.MINUTES) // 在10分钟内未访问则过期
            .concurrencyLevel(Runtime.getRuntime().availableProcessors()) // 设置并发级别为cpu核心数
            .recordStats() // 开启缓存统计
            .build();

    public void putCache(String key, Object data) {
        cache.put(key, data);
    }

    public Object getCacheByKey(String key) {
        return cache.getIfPresent(key);
    }

}
