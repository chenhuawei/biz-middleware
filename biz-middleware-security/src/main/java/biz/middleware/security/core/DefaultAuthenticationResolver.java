package biz.middleware.security.core;

import biz.middleware.security.model.UsernamePasswordLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class DefaultAuthenticationResolver implements AuthenticationResolver {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public Authentication resolve(InputStream inputStream) {
        try {
            UsernamePasswordLoginRequestModel model = objectMapper.readValue(inputStream, UsernamePasswordLoginRequestModel.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(model.getPassword(), model.getUsername());
            return token;
        } catch (IOException e) {
            log.error("parse json failed", e);
        }
        return null;
    }
}
