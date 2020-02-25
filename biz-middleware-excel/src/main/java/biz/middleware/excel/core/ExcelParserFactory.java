package biz.middleware.excel.core;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.jxls.reader.ReaderConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ExcelParserFactory extends CacheLoader<String, ExcelParser> {

    static {
        ReaderConfig.getInstance().setSkipErrors( true );
    }

    LoadingCache<String, ExcelParser> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1L, TimeUnit.MINUTES)
            .build(this);

    public ExcelParser getExcelParser(String xml) throws ExecutionException {
        ExcelParser parser = cache.get(xml);
        return parser;
    }

    @Override
    public ExcelParser load(String xml) throws Exception {
        return new ExcelParser(xml);
    }

}
