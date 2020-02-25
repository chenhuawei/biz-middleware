package biz.middleware.excel;

import biz.middleware.excel.model.*;
import biz.middleware.excel.util.ExcelModelUtil;
import biz.middleware.excel.util.ExcelUtil;
import lombok.Data;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilTest {

    @Data
    public static class Entity {
        private Integer id;
        private String name;
        private Double value;
    }

    @org.junit.jupiter.api.Test
    void parseList() throws InvalidFormatException, ExecutionException, IOException {
        List<Mapping> mappings = Arrays.asList(
                new Mapping(1, 1, "id"),
                new Mapping(1, 2, "name"),
                new Mapping(1, 3, "value")
        );

        Workbook workbook = ExcelModelUtil.wrap(mappings, Entity.class);
        System.out.println(ExcelModelUtil.workbook2xml(workbook));
        List<Entity> list = ExcelUtil.parseSimpleList(workbook, Entity.class, null);
        assertNull(list);
    }
    @Test
    public void testParseXml() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<workbook>\n" +
                "    <worksheet idx=\"0\">\n" +
                "        <section startRow=\"0\" endRow=\"0\"/>\n" +
                "        <loop startRow=\"1\" endRow=\"1\" items=\"__items__\" var=\"__item__\" varType=\"biz.middleware.excel.ExcelUtilTest$Entity\">\n" +
                "            <section startRow=\"1\" endRow=\"1\">\n" +
                "                <mapping row=\"1\" col=\"1\">__item__.id</mapping>\n" +
                "                <mapping row=\"1\" col=\"2\">__item__.name</mapping>\n" +
                "                <mapping row=\"1\" col=\"3\">__item__.value</mapping>\n" +
                "            </section>\n" +
                "            <loopbreakcondition>\n" +
                "                <rowcheck offset=\"0\">\n" +
                "                    <cellcheck offset=\"0\"/>\n" +
                "                </rowcheck>\n" +
                "            </loopbreakcondition>\n" +
                "        </loop>\n" +
                "    </worksheet>\n" +
                "</workbook>";
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        Workbook workbook = JAXB.unmarshal(bais, Workbook.class);
        assertNotNull(workbook);

    }
    @Test
    public void testXml() {

        Workbook workbook = new Workbook();
        workbook.setWorksheets(new ArrayList<>());

        Worksheet worksheet = new Worksheet();
        workbook.getWorksheets().add(worksheet);

        worksheet.setIndex(0);
        worksheet.setLoop(new Loop());
        worksheet.setSection(new Section());
        worksheet.getSection().setStartRow(1);
        worksheet.getSection().setMappings(new ArrayList<>());

        Mapping m1 = new Mapping();
        m1.setCol(1);
        m1.setRow(1);
        m1.setField("f1");
        worksheet.getSection().getMappings().add(m1);

        Mapping m2 = new Mapping();
        m2.setCol(2);
        m2.setRow(2);
        m2.setField("f2");
        worksheet.getSection().getMappings().add(m2);

        worksheet.getLoop().setLoopBreakCondition(new LoopBreakCondition());
        worksheet.getLoop().getLoopBreakCondition().setRowCheck(new RowCheck());
        worksheet.getLoop().getLoopBreakCondition().getRowCheck().setCellCheck(new CellCheck());
        worksheet.getLoop().getLoopBreakCondition().getRowCheck().getCellCheck().setOffset(1);
        worksheet.getLoop().getLoopBreakCondition().getRowCheck().setOffset(1);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JAXB.marshal(workbook, baos);
        String xml = baos.toString();

        System.out.println(xml);
    }
}