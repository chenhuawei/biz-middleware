package biz.middleware.excel.annotations;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ExportExcelMapping {

    String value() default "";

    String fileName() default "";

    String requestName() default "request";

    String resultName() default "result";
}
