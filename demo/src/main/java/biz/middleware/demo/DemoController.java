package biz.middleware.demo;

import biz.middleware.security.model.UsernamePasswordLoginRequestModel;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@Api
public class DemoController {


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UsernamePasswordLoginRequestModel loginRequestModel) {
        return "login";
    }

    @RequestMapping(value = "/self", method = {RequestMethod.GET, RequestMethod.POST})
    @RolesAllowed("user")
    public String self() {
        return "self";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @RolesAllowed("admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @RolesAllowed("user")
    public String user() {
        return "user";
    }

    @RequestMapping(value = "/none", method = RequestMethod.GET)
    public String none() {
        return "none";
    }
}
