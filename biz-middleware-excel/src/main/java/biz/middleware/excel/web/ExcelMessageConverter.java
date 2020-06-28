package biz.middleware.excel.web;

import biz.middleware.excel.annotations.ExportExcelMapping;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    private ResourceLoader loader = new DefaultResourceLoader();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    MediaType supportedMediaType = MediaType.valueOf("application/vnd.ms-excel");

    public ExcelMessageConverter() {
        super();
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return ExcelContextHolder.getContext() != null
                && !Boolean.TRUE.equals(ExcelContextHolder.getContext().getProcessed());
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return canWrite(mediaType);
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            ExportExcelContext exportExcelContext = ExcelContextHolder.getContext();
            if (exportExcelContext == null) {
                throw new RuntimeException("not found export excel context");
            }
            exportExcelContext.setProcessed(Boolean.TRUE);
            ExportExcelMapping mapping = exportExcelContext.getMapping();

            Context context = new Context();

            context.putVar(mapping.requestName(), exportExcelContext.getRequest());
            context.putVar(mapping.resultName(), o);

            String targetFileName = StringUtils.isEmpty(mapping.fileName()) ?
                    LocalDateTime.now().format(formatter) : mapping.fileName();

            Resource resource = loader.getResource(mapping.value());
            String fileName = resource.getFilename();
            String ext = fileName.substring(fileName.lastIndexOf('.'));

            HttpHeaders headers = outputMessage.getHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s",
                    URLEncoder.encode(targetFileName + ext, "UTF-8")));

            try (InputStream is = resource.getInputStream()) {

                Transformer transformer = JxlsHelper.getInstance()
                        .buildExpressionNotation("[[", "]]")
                        .createTransformer(is, outputMessage.getBody());
                JxlsHelper.getInstance().processTemplate(context, transformer);
            }
        } finally {
            ExcelContextHolder.clear();
        }
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public Object read(Type type, Class contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }


}
