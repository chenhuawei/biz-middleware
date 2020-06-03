package biz.middleware.sharding.support;

import biz.middleware.sharding.service.CreateTableService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("application-local.properties")
class DatePreciseShardingAlgorithmImplTest {

    @Resource
    private CreateTableService createTableService;

    @Test
    public void doSharding() throws ParseException {
        DatePreciseShardingAlgorithmImpl algorithm = new DatePreciseShardingAlgorithmImpl();
        Date date = DateUtils.parseDate("2020-06-02 16:02:00", "yyyy-MM-dd HH:mm:ss");
        String tableName = algorithm.doSharding(Collections.emptyList(),
                new PreciseShardingValue<>("t_pl_data", "check_time", date));
        Assert.assertEquals("t_pl_data_2020_06_02", tableName);
    }



}