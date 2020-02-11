package biz.middleware.security.config;

import biz.middleware.security.core.AuthenticationProviderProxy;
import biz.middleware.security.core.AuthenticationResolver;
import biz.middleware.security.core.CustomAuthenticationProcessingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Slf4j
public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    public CustomWebSecurityConfigurer() {
        super(false);
    }

    @Resource
    private BizMiddlewareSecurityProperties bizMiddlewareSecurityProperties;

    @Resource
    private AuthenticationProviderProxy authenticationProvider;

    @Resource
    private AuthenticationResolver authenticationResolver;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.logout().logoutSuccessHandler((HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) -> {
            log.debug("{} logout success", Objects.isNull(authentication) ? "guest" : authentication.getPrincipal());
        });
        //.addLogoutHandler();
        http.formLogin().disable();

        CustomAuthenticationProcessingFilter filter = restfulAuthenticationProcessingFilter();
        http.addFilterAfter(filter, FilterSecurityInterceptor.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider);
    }

    private CustomAuthenticationProcessingFilter restfulAuthenticationProcessingFilter() throws Exception {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(
                bizMiddlewareSecurityProperties.getLoginPath(), bizMiddlewareSecurityProperties.getLoginMethod());

        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(
                matcher, authenticationResolver);

        filter.setAuthenticationSuccessHandler(
                new ForwardAuthenticationSuccessHandler(bizMiddlewareSecurityProperties.getAuthenticationSuccessForwardUrl()));
        filter.setAuthenticationFailureHandler(
                new ForwardAuthenticationFailureHandler(bizMiddlewareSecurityProperties.getAuthenticationFailureForwardUrl()));
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }
}
