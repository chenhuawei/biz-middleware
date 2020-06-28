package biz.middleware.excel.web;

import biz.middleware.excel.annotations.ExportExcelMapping;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
public class ExportExcelHandler {

    @Around("@annotation(biz.middleware.excel.annotations.ExportExcelMapping)")
    public Object export(ProceedingJoinPoint joinPoint) throws Throwable {


        Object[] parameterValues = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        ExportExcelMapping mapping = methodSignature.getMethod().getAnnotation(ExportExcelMapping.class);


        Map request = new HashMap();
        for (int i = 0; i < parameterNames.length; i ++) {
            String parameterName = parameterNames[i];
            request.put(parameterName, parameterValues[i]);
        }

        ExportExcelContext context = ExcelContextHolder.getContext();
        if (context == null) {
            throw new RuntimeException("not found export excel context");
        }
        context.setMapping(mapping);
        context.setRequest(request);

        Object result = joinPoint.proceed();

        return result;
    }


}
