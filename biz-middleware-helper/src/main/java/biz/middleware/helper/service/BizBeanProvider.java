package biz.middleware.helper.service;

import java.util.Collection;

public interface BizBeanProvider {

    <T> Collection<T> getBeans(Class<T> clazz);


}
