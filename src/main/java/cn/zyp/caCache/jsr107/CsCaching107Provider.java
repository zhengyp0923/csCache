package cn.zyp.caCache.jsr107;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 创建 配置 获取 管理多个CacheManager
 */
public class CsCaching107Provider implements CachingProvider {
    private static final String DEFAULT_URI_STRING = "urn:X-cscache:jsr107-default-config";

    private static final URI URI_DEFAULT;

    private final Map<ClassLoader, ConcurrentMap<URI, CacheManager>> cacheManagers = new WeakHashMap<ClassLoader, ConcurrentMap<URI, CacheManager>>();

    static {
        try {
            URI_DEFAULT = new URI(DEFAULT_URI_STRING);
        } catch (URISyntaxException e) {
            throw new javax.cache.CacheException(e);
        }
    }

    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        uri = uri == null ? getDefaultURI() : uri;
        classLoader = classLoader == null ? getDefaultClassLoader() : classLoader;
        properties = properties == null ? new Properties() : cloneProperties(properties);

        ConcurrentMap<URI, CacheManager> cacheManagersByURI = cacheManagers.get(classLoader);

        if (cacheManagersByURI == null) {
            cacheManagersByURI = new ConcurrentHashMap<URI, CacheManager>();
        }

        CacheManager cacheManager = cacheManagersByURI.get(uri);

        if (cacheManager == null) {
            cacheManager = new CsCaching107Manager(this,classLoader,uri,properties);

            cacheManagersByURI.put(uri, cacheManager);
        }

        if (!cacheManagers.containsKey(classLoader)) {
            cacheManagers.put(classLoader, cacheManagersByURI);
        }
        return cacheManager;
    }

    public ClassLoader getDefaultClassLoader() {
        return getClass().getClassLoader();
    }

    public URI getDefaultURI() {
        return URI_DEFAULT;
    }

    public Properties getDefaultProperties() {
        return new Properties();
    }

    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
        return getCacheManager(uri, classLoader, getDefaultProperties());
    }

    public CacheManager getCacheManager() {
        return getCacheManager(getDefaultURI(), getDefaultClassLoader(), null);
    }

    public void close() {

    }

    public void close(ClassLoader classLoader) {

    }

    public void close(URI uri, ClassLoader classLoader) {

    }

    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }
    private static Properties cloneProperties(Properties properties) {
        Properties clone = new Properties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            clone.put(entry.getKey(), entry.getValue());
        }
        return clone;
    }
}
