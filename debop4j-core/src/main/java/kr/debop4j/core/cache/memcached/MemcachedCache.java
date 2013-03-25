package kr.debop4j.core.cache.memcached;

import kr.debop4j.core.Guard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Memcached를 캐시 저장소로 사용하는 Cache
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 3. 25 오전 11:42
 */
@Slf4j
public class MemcachedCache implements Cache {

    public static final int EXP_TIME = 100000;

    @Getter
    private String name;
    @Getter
    private MemcachedClient nativeCache = null;

    protected MemcachedCache() {}

    public MemcachedCache(MemcachedClient client) {
        Guard.shouldNotBeNull(client, "client");

        this.name = "Memcached";
        this.nativeCache = client;
        if (log.isDebugEnabled())
            log.debug("MemcachedCache를 생성했습니다");
    }

    @Override
    public ValueWrapper get(Object key) {
        Guard.shouldNotBeNull(key, "key");

        GetFuture<Object> result = nativeCache.asyncGet(key.toString());

        SimpleValueWrapper wrapper = null;
        try {
            if (result.get() != null)
                wrapper = new SimpleValueWrapper(result.get());
        } catch (Exception ignored) {}
        return wrapper;
    }

    @Override
    public void put(Object key, Object value) {
        Guard.shouldNotBeNull(key, "key");
        if (!(key instanceof String)) {
            log.error("Invalid key type: " + key.getClass());
            return;
        }

        OperationFuture<Boolean> setOp = null;
//        ValueWrapper cached = get(key);
//        if (cached == null)
//            setOp = nativeCache.add(key.toString(), EXP_TIME, value);
//        else
//            setOp = nativeCache.replace(key.toString(), EXP_TIME, value);
        setOp = nativeCache.set(key.toString(), EXP_TIME, value);

        if (log.isInfoEnabled()) {
            if (setOp.getStatus().isSuccess()) {
                log.info("객체를 캐시 키[{}]로 저장했습니다.", key);
            } else {
                log.info("객체를 캐시 키[{}]로 저장하는데 실패했습니다. operation=[{}]", key, setOp);
            }
        }
    }

    @Override
    public void evict(Object key) {
        if (key != null)
            nativeCache.delete(key.toString());
    }

    @Override
    public void clear() {
        nativeCache.flush();
    }
}
