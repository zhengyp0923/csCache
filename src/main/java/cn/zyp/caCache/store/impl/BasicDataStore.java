package cn.zyp.caCache.store.impl;

import cn.zyp.caCache.store.DataStore;
import cn.zyp.caCache.store.StoreAccessException;
import cn.zyp.caCache.store.ValueHolder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BasicDataStore<K, V> implements DataStore<K, V> {
    ConcurrentMap<K, ValueHolder<V>> map = new ConcurrentHashMap<K, ValueHolder<V>>();


    public ValueHolder<V> get(K key) throws StoreAccessException {
        return map.get(key);
    }

    public PutStatus put(K key, V value) throws StoreAccessException {
        ValueHolder<V> v = new BasicValueHolder<V>(value);
        map.put(key, v);
        return PutStatus.PUT;
    }

    public ValueHolder<V> remove(K key) throws StoreAccessException {
        return map.remove(key);
    }

    public void clear() throws StoreAccessException {
        map.clear();
    }
}
