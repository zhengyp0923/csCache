package cn.zyp.caCache.store.impl;

import cn.zyp.caCache.store.ValueHolder;

import java.util.Map;

public class LRUEntry<K, V extends ValueHolder<?>> implements Map.Entry<K, ValueHolder<?>> {
    private final K key;
    private ValueHolder<?> v;

    LRUEntry<K, ValueHolder<?>> pre;
    LRUEntry<K, ValueHolder<?>> next;

    public LRUEntry(K key, ValueHolder<?> v) {
        this.key = key;
        this.v = v;
    }

    public LRUEntry<K, ValueHolder<?>> getPre() {
        return pre;
    }

    public void setPre(LRUEntry<K, ValueHolder<?>> pre) {
        this.pre = pre;
    }

    public LRUEntry<K, ValueHolder<?>> getNext() {
        return next;
    }

    public void setNext(LRUEntry<K, ValueHolder<?>> next) {
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public ValueHolder<?> getValue() {
        return v;
    }

    public ValueHolder<?> setValue(ValueHolder<?> value) {
        ValueHolder<?> oldValue = this.v;
        this.v = value;
        return oldValue;
    }
}
