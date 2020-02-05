package biz.middleware.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DemoAuthenticationProviderImpl implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String principal = Objects.toString(authentication.getPrincipal(), null);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>(1);
        String role = "ROLE_user";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        authorityList.add(authority);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                name, principal, authorityList);

        log.debug("authentication {}, {}, {}", name, principal, token);

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
