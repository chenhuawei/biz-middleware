package biz.middleware.excel.util;

import biz.middleware.excel.core.ExcelParser;
import biz.middleware.excel.core.ExcelParserFactory;
import biz.middleware.excel.model.Mapping;
import biz.middleware.excel.model.Section;
import biz.middleware.excel.model.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExcelUtil {

    private static ExcelParserFactory excelParserFactory = new ExcelParserFactory();

    public static <T> List<T> parseSimpleList(Workbook workbook, Class<T> clazz, InputStream inputStream)
            throws ExecutionException, IOException, InvalidFormatException {
        String xml = ExcelModelUtil.workbook2xml(workbook);
        ExcelParser excelParser = excelParserFactory.getExcelParser(xml);
        return excelParser.parseList(clazz, inputStream);
    }

    public static <T> List<T> parseSimpleList(Section section, Class<T> clazz, InputStream inputStream)
            throws InvalidFormatException, ExecutionException, IOException {
        Workbook workbook = ExcelModelUtil.wrap(section, clazz);
        return parseSimpleList(workbook, clazz, inputStream);
    }

    public static <T> List<T> parseSimpleList(List<Mapping> mappings, Class<T> clazz, InputStream inputStream)
            throws InvalidFormatException, ExecutionException, IOException {
        Workbook workbook = ExcelModelUtil.wrap(mappings, clazz);
        return parseSimpleList(workbook, clazz, inputStream);
    }
}
