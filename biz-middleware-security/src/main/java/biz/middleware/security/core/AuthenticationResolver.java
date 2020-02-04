package biz.middleware.security.core;

import org.springframework.security.core.Authentication;

import java.io.InputStream;

public interface AuthenticationResolver {
    Authentication resolve(InputStream inputStream);
}
