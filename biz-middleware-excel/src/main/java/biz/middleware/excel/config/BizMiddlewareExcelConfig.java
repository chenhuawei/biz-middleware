package biz.middleware.excel.config;

import biz.middleware.excel.web.ExcelMessageConverter;
import biz.middleware.excel.web.ExportExcelFilter;
import biz.middleware.excel.web.ExportExcelHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class BizMiddlewareExcelConfig {

//    @Bean
//    public ExportExcelMappingHandlerMapping exportExcelMappingHandlerMapping() {
//        ExportExcelMappingHandlerMapping handlerMapping = new ExportExcelMappingHandlerMapping();
//        handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1100);
//        return handlerMapping;
//    }
    @Bean
    public ExportExcelFilter exportExcelFilter() {
        return new ExportExcelFilter();
    }
    @Bean
    public ExcelMessageConverter excelMessageConverter() {
        return new ExcelMessageConverter();
    }

    @Bean
    public ExportExcelHandler exportExcelHandler() {
        return new ExportExcelHandler();
    }
}
