package biz.middleware.sharding.support;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class LocalDateTimeRangeShardingAlgorithm implements RangeShardingAlgorithm<LocalDateTime> {

    private static final String DEFAULT_PATTERN = "yyyy_MM_dd";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<LocalDateTime> shardingValue) {
        Range<LocalDateTime> range = shardingValue.getValueRange();

        Set<String> set = new HashSet<>();
        LocalDateTime lower = null;
        if (range.hasLowerBound()) {
            lower = range.lowerEndpoint();
            String suffix = lower.format(formatter);
            String tableName = String.format("%s_%s", shardingValue.getLogicTableName(),
                    suffix);
            set.add(tableName);
        }
        LocalDateTime upper = null;
        if (range.hasUpperBound()) {
            upper = range.upperEndpoint();
            String suffix = upper.format(formatter);
            String tableName = String.format("%s_%s", shardingValue.getLogicTableName(),
                    suffix);
            set.add(tableName);
        }

        log.debug("doSharding [{},{}] => {}", lower, upper, set);
        return set;
    }
}
