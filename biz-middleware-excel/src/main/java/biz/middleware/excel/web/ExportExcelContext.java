package biz.middleware.excel.web;

import biz.middleware.excel.annotations.ExportExcelMapping;
import lombok.Data;

import java.util.Map;

@Data
public class ExportExcelContext {

    private ExportExcelMapping mapping;

    private Map request;

    private Boolean processed;

}
