package kr.debop4j.core.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Apache common pool의 Generic Pool (Jedis 의 JedisPool 를 참고하여 만들었음)
 *
 * @author sunghyouk.bae@gmail.com
 * @since 13. 4. 8. 오전 12:41
 */
@Slf4j
public abstract class AbstractPool<T> implements AutoCloseable {

    protected GenericObjectPool pool;

    protected AbstractPool() { }

    public AbstractPool(final GenericObjectPool.Config poolConfig, PoolableObjectFactory<T> factory) {
        pool = new GenericObjectPool<T>(factory, poolConfig);
    }

    /**
     * 풀에서 리소스를 얻습니다.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public T getResource() {
        if (log.isDebugEnabled())
            log.debug("Pool에서 resource 를 얻습니다...");
        try {

            T result = (T) pool.borrowObject();

            if (log.isDebugEnabled())
                log.debug("Pool에서 resource 를 얻었습니다. resource=[{}]", result);

            return result;

        } catch (Exception e) {
            log.error("Pool에서 리소스를 획득하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 재사용을 위해 풀에 리소스를 반환합니다.
     *
     * @param resource
     */
    public void returnResource(final T resource) {
        returnResourceObject(resource);
    }

    @SuppressWarnings("unchecked")
    protected void returnResourceObject(final Object resource) {
        if (log.isDebugEnabled())
            log.debug("Pool에 resource 를 반환합니다. resource=[{}]", resource);
        try {
            pool.returnObject(resource);
        } catch (Exception e) {
            log.error("리소스를 Pool로 반환하는데 실패했습니다. resource=" + resource, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 재사용할 수 없는 리소스는 폐기하도록 합니다.
     *
     * @param resource
     */
    public void returnBrokenResource(final T resource) {
        returnBrokenResourceObject(resource);
    }

    @SuppressWarnings("unchecked")
    protected void returnBrokenResourceObject(final Object resource) {
        try {
            pool.invalidateObject(resource);
        } catch (Exception e) {
            log.error("더이상 사용할 수 없는 리소스를 제거하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 풀을 제거합니다. 내부의 남아있는 모든 리소스를 제거합니다.
     */
    public void destroy() {
        if (log.isDebugEnabled())
            log.debug("Pool을 제거합니다...");
        try {
            pool.close();
            pool = null;
        } catch (Exception e) {
            log.error("pool을 제거하는데 실패했습니다.", e);
            throw new RuntimeException(e);
        }
        if (log.isDebugEnabled())
            log.debug("Pool을 제거했습니다.");
    }

    public void close() throws Exception {
        destroy();
    }
}