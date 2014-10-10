
package com.nostra13.universalimageloader.cache.memory.impl;

import java.util.Collection;

import com.nostra13.universalimageloader.cache.memory.MemoryCache;

/**
 * @author luzemin
 */
public class UnLimitedMemoryCache implements MemoryCache{

    @Override
    public boolean put(String key, CacheEntry entry) {
        return false;
    }

    @Override
    public CacheEntry get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {
        
    }

    @Override
    public Collection<String> keys() {
        return null;
    }

    @Override
    public void clear() {
        
    }

}
