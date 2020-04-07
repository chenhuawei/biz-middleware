package biz.middleware.helper.service.impl;

import biz.middleware.helper.service.BizBeanProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BizBeanProviderImpl extends CacheLoader<Class, Collection> implements BizBeanProvider {

    @Autowired
    private ApplicationContext applicationContext;

    LoadingCache<Class, Collection> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1L, TimeUnit.MINUTES)
            .build(this);

    @Override
    public <T> Collection<T> getBeans(Class<T> clazz) {
        try {
            return cache.get(clazz);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Object> load(Class type) throws Exception {
        Map<String, Object> beans = applicationContext.getBeansOfType(type);
        return beans.values();
    }
}
