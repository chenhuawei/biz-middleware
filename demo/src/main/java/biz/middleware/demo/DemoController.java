package biz.middleware.demo;

import biz.middleware.excel.annotations.ExportExcelMapping;
import biz.middleware.security.model.UsernamePasswordLoginRequestModel;
import io.swagger.annotations.Api;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@Api
public class DemoController {


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UsernamePasswordLoginRequestModel loginRequestModel) {
        return "login";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public void logout() {
    }

    @RequestMapping(value = "/self", method = {RequestMethod.GET, RequestMethod.POST})
    @RolesAllowed("user")
    public String self() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Objects.toString(authentication.getPrincipal(), null);
        return username;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ExportExcelMapping("classpath:/excel/templates/list.xls")
    public List list() {
        return new ArrayList();
    }


    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    @ExportExcelMapping("classpath:/excel/templates/list.xls")
    public List list2(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        return Arrays.asList(pageNo, pageSize);
    }
}
