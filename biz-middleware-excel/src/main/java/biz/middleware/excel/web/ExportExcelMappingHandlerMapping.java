package biz.middleware.excel.web;

import biz.middleware.excel.annotations.ExportExcelMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

@Slf4j
public class ExportExcelMappingHandlerMapping extends RequestMappingHandlerMapping {

    private Method doExport;

    protected void detectHandlerMethods(Object handler) {
        log.info("detectHandlerMethods", handler);
        Class<?> handlerType = (handler instanceof String ?
                obtainApplicationContext().getType((String) handler) : handler.getClass());

        for (Method method : handlerType.getMethods()) {
            ExportExcelMapping mapper = AnnotatedElementUtils.findMergedAnnotation(
                    method, ExportExcelMapping.class);

            if (mapper != null) {

                RequestMappingInfo exportRequestMapping = createRequestMappingInfo(method);
                RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
                if (typeInfo != null) {
                    exportRequestMapping = exportRequestMapping.combine(typeInfo);
                }

                Method invocableMethod = AopUtils.selectInvocableMethod(method, handlerType);

                super.registerMapping(exportRequestMapping, handler,
                        invocableMethod);
            }
        }
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
    }

    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, javax.servlet.http.HttpServletRequest request) throws Exception {
        HandlerMethod method = super.lookupHandlerMethod(lookupPath, request);
        return method;
    }

    @Override
    protected String[] resolveEmbeddedValuesInPatterns(String[] patterns) {
        String[] resolved = super.resolveEmbeddedValuesInPatterns(patterns);
        String[] result = new String[resolved.length];
        for (int i = 0; i < resolved.length; i ++) {
            result[i] = resolved[i] + "/export/excel";
        }
        return result;
    }

}

