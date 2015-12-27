package qcjlibrary.cache.base;


/***********************************************************************
 * Module:  Cache.java
 * Author:  Administrator
 * Purpose: Defines the Interface Cache
 ***********************************************************************/

/**
 * 所有缓存的接口，用单例模式管理缓存，这样比较好
 */
public interface Cache {
    /**
     * @param cacheType 缓存类型 獲取緩存
     */
    Object getTheData(String path);

    boolean addTheData(Object is, String path);

    int getCacheSize();

    boolean removeCache();
}