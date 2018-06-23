package cn.zyp.test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

public class Test {
    public static void main(String[] args) {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        Cache<String, String> cache = cacheManager.createCache("123", new MutableConfiguration<String, String>());

        cache.put("345", "456");
        cache.put("244", "244");
        cache.put("123", "456");
        cache.put("12", "244");
        System.out.println(cache.get("345"));
        System.out.println(cache.get("244"));
        System.out.println(cache.get("123"));
        System.out.println(cache.get("12"));
    }
}
