package biz.middleware.security.config;

import biz.middleware.security.core.AuthenticationProviderProxy;
import biz.middleware.security.core.DefaultAuthenticationResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@Configuration
@Import(CustomWebSecurityConfigurer.class)
public class BizMiddlewareSecurityAutoConfiguration {

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "security")
    public BizMiddlewareSecurityProperties bizMiddlewareSecurityProperties() {
        return new BizMiddlewareSecurityProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationProviderProxy authenticationProvider() {
        AuthenticationProviderProxy authenticationProviderProxy = new AuthenticationProviderProxy();
        return authenticationProviderProxy;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAuthenticationResolver authenticationResolver() {
        return new DefaultAuthenticationResolver();
    }
}
