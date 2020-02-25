package biz.middleware.excel.util;

import biz.middleware.excel.ExcelConstants;
import biz.middleware.excel.model.*;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelModelUtil {

    public static Workbook xml2Workbook(String xml) {
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        Workbook workbook = JAXB.unmarshal(bais, Workbook.class);
        return workbook;
    }

    public static String workbook2xml(Workbook workbook) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JAXB.marshal(workbook, baos);
        String xml = baos.toString();
        return xml;
    }

    public static Workbook copy(Workbook workbook) {
        String xml = workbook2xml(workbook);
        Workbook copy = xml2Workbook(xml);
        return copy;
    }

    public static Workbook wrap(List<Mapping> mappings, Class<?> clazz) {
        Section section = new Section();
        section.setMappings(mappings);
        return wrap(section, clazz);
    }

    public static Workbook wrap(Section section, Class<?> clazz) {
        Workbook workbook = new Workbook();
        workbook.setWorksheets(new ArrayList<>());
        Worksheet worksheet = new Worksheet();
        workbook.getWorksheets().add(worksheet);
        worksheet.setIndex(0);
        worksheet.setSection(new Section());
        worksheet.getSection().setStartRow(0);
        worksheet.getSection().setEndRow(0);
        Loop loop = new Loop();
        worksheet.setLoop(loop);
        loop.setItems(ExcelConstants.VAR_NAME_COLLECTION);
        loop.setVarType(clazz.getName());
        loop.setVar(ExcelConstants.VAR_NAME_ITEM);
        loop.setStartRow(1);
        loop.setEndRow(1);
        loop.setLoopBreakCondition(new LoopBreakCondition());
        loop.getLoopBreakCondition().setRowCheck(new RowCheck());
        loop.getLoopBreakCondition().getRowCheck().setCellCheck(new CellCheck());
        loop.getLoopBreakCondition().getRowCheck().setOffset(0);
        loop.getLoopBreakCondition().getRowCheck().getCellCheck().setOffset(0);

        if (section.getStartRow() == null) {
            section.setStartRow(1);
        }
        if (section.getEndRow() == null) {
            section.setEndRow(1);
        }

        for (Mapping mapping : section.getMappings()) {
            mapping.setField(String.format("%s.%s", ExcelConstants.VAR_NAME_ITEM, mapping.getField()));
        }
        loop.setSection(section);
        return workbook;
    }
}
