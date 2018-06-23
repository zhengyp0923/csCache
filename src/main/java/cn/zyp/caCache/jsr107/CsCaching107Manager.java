package cn.zyp.caCache.jsr107;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 创建、配置、获取、管理多个Cache
 */
public class CsCaching107Manager implements CacheManager {
    private final CsCaching107Provider cachingProvider;
    private final ClassLoader classLoader;
    private final URI uri;
    private final Properties props;
    private volatile boolean isClosed;
    private static Logger logger = LoggerFactory.getLogger(CsCaching107Manager.class);

    public CsCaching107Manager(CsCaching107Provider cachingProvider, ClassLoader classLoader, URI uri, Properties props) {
        this.cachingProvider = cachingProvider;
        this.classLoader = classLoader;
        this.uri = uri;
        this.props = props;
    }

    /**
     * 存储缓存的实例  可进行并发的对caches的读写
     */
    private final ConcurrentMap<String, CsCache107<?, ?>> caches = new ConcurrentHashMap<String, CsCache107<?, ?>>();

    public CachingProvider getCachingProvider() {
        return cachingProvider;
    }

    public URI getURI() {
        return uri;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Properties getProperties() {
        return props;
    }

    /**
     * 创建缓存    ConcurrentHashMap线程不安全的情况  可使用双检锁进行优化
     *
     * @param cacheName     缓存的名称
     * @param configuration
     * @param <K>
     * @param <V>
     * @param <C>
     * @return
     * @throws IllegalArgumentException
     */
    public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName, C configuration) throws IllegalArgumentException {
        if (isClosed()) {
            throw new IllegalStateException();
        }
        checkNotNull(cacheName, "cacheName");
        checkNotNull(configuration, "configuration");
        //根据cacheName获取客户端缓存实例
        CsCache107<?, ?> cache = caches.get(cacheName);
        if (cache == null) {
            synchronized (caches) {
                cache = caches.get(cacheName);
                if (cache == null) {
                    cache = new CsCache107<K, V>(cacheName, this, configuration);
                    caches.put(cache.getName(), cache);
                }
            }
            return (Cache<K, V>) cache;
        } else {
            throw new CacheException(cacheName + " already exists");
        }
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param keyClasszz
     * @param valueClasszz
     * @param <K>
     * @param <V>
     * @return
     */
    synchronized public <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyClasszz, Class<V> valueClasszz) {
        if (isClosed()) {
            throw new IllegalStateException();
        }

        checkNotNull(keyClasszz, "keyType");
        checkNotNull(valueClasszz, "valueType");
        //获取缓存的实例
        CsCache107<?, ?> cache = caches.get(cacheName);
        if (cache == null) {
            return null;
        } else {
            //判断key value的类型传入的是否与设定的类型一致
            Configuration configuration = cache.getConfiguration(Configuration.class);
            if (configuration.getKeyType() != null && configuration.getKeyType().equals(keyClasszz)) {
                return (Cache<K, V>) cache;
            } else {
                throw new ClassCastException("key require type " + configuration.getKeyType());
            }
        }
    }

    public <K, V> Cache<K, V> getCache(String s) {

        return (Cache<K, V>) getCache(s, Object.class, Object.class);
    }

    public Iterable<String> getCacheNames() {
        return null;
    }

    public void destroyCache(String s) {

    }

    public void enableManagement(String s, boolean b) {

    }

    public void enableStatistics(String s, boolean b) {

    }

    public void close() {

    }

    public boolean isClosed() {
        return false;
    }

    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    private void checkNotNull(Object object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " can not be null");
        }
    }
}
