package biz.middleware.security.core;

import biz.middleware.security.config.BizMiddlewareSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Resource;
import java.util.Map;

public class AuthenticationProviderProxy implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationProviderProxy.class);

    private AuthenticationProvider authenticationProvider;

    private final Object authenticationProviderMonitor = new Object();


    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private BizMiddlewareSecurityProperties bizMiddlewareSecurityProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authenticationProvider != null) {
            return authenticationProvider.authenticate(authentication);
        }
        synchronized (authenticationProviderMonitor) {
            if (authenticationProvider == null) {
                authenticationProvider = getAuthenticationProviderDefaultIfNotFound();
            }
        }
        return authenticationProvider.authenticate(authentication);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        if (authenticationProvider != null) {
            return authenticationProvider.supports(authentication);
        }
        synchronized (authenticationProviderMonitor) {
            if (authenticationProvider == null) {
                authenticationProvider = getAuthenticationProviderDefaultIfNotFound();
            }
        }
        return authenticationProvider.supports(authentication);
    }

    private AuthenticationProvider getAuthenticationProviderDefaultIfNotFound() {
        AuthenticationProvider authenticationProvider = null;
        Map<String, AuthenticationProvider> providerMap = applicationContext.getBeansOfType(AuthenticationProvider.class);
        for (Map.Entry<String, AuthenticationProvider> entry: providerMap.entrySet()) {
            AuthenticationProvider provider = entry.getValue();
            if (provider instanceof AuthenticationProviderProxy) {
                continue;
            }
            if (authenticationProvider == null) {
                authenticationProvider = provider;
            } else {
                throw new RuntimeException("the AuthenticationProvider is conflict [" + authenticationProvider.getClass().getName() + ", " + provider.getClass().getName() +"]");
            }
        }
        if (authenticationProvider == null) {
            logger.warn("not found authenticationProvider, use default");
            authenticationProvider = new DefaultAuthenticationProvider(bizMiddlewareSecurityProperties);
        } else {
            logger.info("use " + authenticationProvider.getClass().getName());
        }
        return authenticationProvider;
    }
}
