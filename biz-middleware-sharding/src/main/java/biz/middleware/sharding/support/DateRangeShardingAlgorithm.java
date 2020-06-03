package biz.middleware.sharding.support;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    private static final String DEFAULT_PATTERN = "yyyy_MM_dd";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        Range<Date> range = shardingValue.getValueRange();
        String pattern = DEFAULT_PATTERN;
        Set<String> set = new HashSet<>();
        Date lower = null;
        if (range.hasLowerBound()) {
            lower = range.lowerEndpoint();
            String suffix = DateFormatUtils.format(lower, pattern);
            String tableName = String.format("%s_%s", shardingValue.getLogicTableName(),
                    suffix);
            set.add(tableName);
        }
        Date upper = null;
        if (range.hasUpperBound()) {
            upper = range.upperEndpoint();
            String suffix = DateFormatUtils.format(upper, pattern);
            String tableName = String.format("%s_%s", shardingValue.getLogicTableName(),
                    suffix);
            set.add(tableName);
        }

        log.debug("doSharding [{},{}] => {}", lower, upper, set);
        return set;
    }
}
