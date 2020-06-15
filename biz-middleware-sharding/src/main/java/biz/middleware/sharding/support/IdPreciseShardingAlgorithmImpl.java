package biz.middleware.sharding.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Properties;

@Slf4j
public class IdPreciseShardingAlgorithmImpl implements PreciseShardingAlgorithm<Long> {


    private Properties properties = new Properties();


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long id = preciseShardingValue.getValue();

        String result = String.format("%s_%s", preciseShardingValue.getLogicTableName(),
                id / 10000000L);
        log.debug("doSharding {} => {}", id, result);
        return result;
    }


}
