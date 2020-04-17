package biz.middleware.helper.config;

import biz.middleware.helper.service.BizBeanProvider;
import biz.middleware.helper.service.TransactionalWrapper;
import biz.middleware.helper.service.impl.BizBeanProviderImpl;
import biz.middleware.helper.service.impl.TransactionalWrapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BizMiddlewareHelperAutoConfiguration {

    @Bean
    public BizBeanProvider bizBeanProvider() {
        return new BizBeanProviderImpl();
    }

    @Bean
    public TransactionalWrapper transactionalWrapper() {
        return new TransactionalWrapperImpl();
    }
}
