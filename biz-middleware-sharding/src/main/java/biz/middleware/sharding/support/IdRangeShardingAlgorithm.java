package biz.middleware.sharding.support;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class IdRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Range<Long> range = shardingValue.getValueRange();
        Set<String> set = new HashSet<>();
        Long lower = null;
        if (range.hasLowerBound()) {
            lower = range.lowerEndpoint();
            String suffix = String.valueOf(lower / 10000000L);
            String tableName = String.format("%s_%s", shardingValue.getLogicTableName(),
                    suffix);
            set.add(tableName);
        }
        Long upper = null;
        if (range.hasUpperBound()) {
            upper = range.upperEndpoint();
            String suffix = String.valueOf(upper / 10000000L);
            String tableName = String.format("%s_%s", shardingValue.getLogicTableName(),
                    suffix);
            set.add(tableName);
        }

        log.debug("doSharding [{},{}] => {}", lower, upper, set);
        return set;
    }
}
