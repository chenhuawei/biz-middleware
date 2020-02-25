package biz.middleware.excel.core;

import biz.middleware.excel.ExcelConstants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.ReaderConfig;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelParser {

    String xml;
    XLSReader reader;

    ExcelParser(String xml) throws IOException, SAXException {
        this.xml = xml;
        XLSReader reader = ReaderBuilder.buildFromXML(new ByteArrayInputStream(xml.getBytes()));
        this.reader = reader;
    }

    public <T> List<T> parseList(Class<T> clazz, InputStream inputStream) throws IOException, InvalidFormatException {
        if (inputStream == null) {
            return null;
        }

        Map result = new HashMap();

        result.put(ExcelConstants.VAR_NAME_COLLECTION, new ArrayList<T>());
        XLSReadStatus status = reader.read(inputStream, result);
        if (status.isStatusOK()) {
            return (List<T>)result.get(ExcelConstants.VAR_NAME_COLLECTION);
        } else {
            throw new RuntimeException(join(status.getReadMessages()));
        }
    }

    String join(List messages) {
        StringBuilder sb = new StringBuilder();

        for (Object message : messages) {
            sb.append(message).append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
