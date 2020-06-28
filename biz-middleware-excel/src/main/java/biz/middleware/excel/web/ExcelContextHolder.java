package biz.middleware.excel.web;

public class ExcelContextHolder {

    private static ThreadLocal<ExportExcelContext> threadLocal = new ThreadLocal<>();

    public static ExportExcelContext newContext() {
        threadLocal.remove();
        ExportExcelContext context = new ExportExcelContext();
        threadLocal.set(context);
        return context;
    }

    public static ExportExcelContext getContext() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
