package biz.middleware.security.core;

import biz.middleware.security.config.BizMiddlewareSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    private BizMiddlewareSecurityProperties bizMiddlewareSecurityProperties;

    public DefaultAuthenticationProvider(BizMiddlewareSecurityProperties bizMiddlewareSecurityProperties) {
        this.bizMiddlewareSecurityProperties = bizMiddlewareSecurityProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String principal = Objects.toString(authentication.getPrincipal(), null);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>(1);
        String role = StringUtils.join(bizMiddlewareSecurityProperties.getAuthorityPrefix(),
                bizMiddlewareSecurityProperties.getLoginSuccessAuthority());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        authorityList.add(authority);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                name, principal, authorityList);

        logger.debug("authentication {}, {}, {}", name, principal, token);

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
