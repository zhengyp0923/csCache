package cn.zyp.caCache.jsr107;

import cn.zyp.caCache.CsCache;
import cn.zyp.caCache.store.impl.LRUDataStore;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 缓存实例  key value
 */
public class CsCache107<K, V> implements Cache<K, V> {
    //    private CsCache<K,V> csCache=new CsCache<K, V>(new BasicDataStore<K, V>());
    private CsCache<K, V> csCache = new CsCache<K, V>(new LRUDataStore<K, V>(2));
    private String cacheName;
    private CsCaching107Manager caching107Manager;
    private Configuration<K, V> configuration;

    private volatile boolean isClosed;

    public CsCache107(String cacheName, CsCaching107Manager caching107Manager, Configuration<K, V> configuration) {
        this.cacheName = cacheName;
        this.caching107Manager = caching107Manager;
        this.configuration = configuration;
        isClosed = false;
    }

    public V get(K k) {
        return csCache.get(k);
    }

    public void put(K k, V v) {
        csCache.put(k, v);
    }

    public void clear() {
        csCache.clear();
    }

    public boolean remove(K k) {
        csCache.remove(k);
        return true;
    }

    public Map<K, V> getAll(Set<? extends K> set) {
        return null;
    }

    public boolean containsKey(K k) {
        return false;
    }

    public void loadAll(Set<? extends K> set, boolean b, CompletionListener completionListener) {

    }


    public V getAndPut(K k, V v) {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {

    }

    public boolean putIfAbsent(K k, V v) {
        return false;
    }


    public boolean remove(K k, V v) {
        return false;
    }

    public V getAndRemove(K k) {
        return null;
    }

    public boolean replace(K k, V v, V v1) {
        return false;
    }

    public boolean replace(K k, V v) {
        return false;
    }

    public V getAndReplace(K k, V v) {
        return null;
    }

    public void removeAll(Set<? extends K> set) {

    }

    public void removeAll() {

    }


    public <C extends Configuration<K, V>> C getConfiguration(Class<C> aClass) {
        return null;
    }

    public <T> T invoke(K k, EntryProcessor<K, V, T> entryProcessor, Object... objects) throws EntryProcessorException {
        return null;
    }

    public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> set, EntryProcessor<K, V, T> entryProcessor, Object... objects) {
        return null;
    }

    public String getName() {
        return cacheName;
    }

    public CacheManager getCacheManager() {
        return caching107Manager;
    }

    public void close() {

    }

    public boolean isClosed() {
        return false;
    }

    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    public void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    }

    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    }

    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
