package biz.middleware.sharding.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class DatePreciseShardingAlgorithmImpl implements PreciseShardingAlgorithm<Date> {

    private static final String DEFAULT_PATTERN = "yyyy_MM_dd";

    private Properties properties = new Properties();


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        Date date = preciseShardingValue.getValue();
        String pattern = StringUtils.defaultIfBlank(properties.getProperty("pattern"), DEFAULT_PATTERN);
        String suffix = DateFormatUtils.format(date, pattern);
        String result = String.format("%s_%s", preciseShardingValue.getLogicTableName(),
                suffix);
        log.debug("doSharding {} => {}", date, result);
        return result;
    }


}
