package biz.middleware.security.config;

import lombok.Data;

import java.util.List;

@Data
public class BizMiddlewareSecurityProperties {
    private List<String> ignoringList;
    private String loginPath = "/login";
    private String loginMethod = "POST";
    private String authorityPrefix = "ROLE_";
    private String loginSuccessAuthority = "user";
    private String authenticationSuccessForwardUrl = "/self";
    private String authenticationFailureForwardUrl = "/error";

}
