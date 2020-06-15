package biz.middleware.sharding.service.impl;

import biz.middleware.sharding.entity.TPlData;
import biz.middleware.sharding.entity.TStackAccomp;
import biz.middleware.sharding.service.CreateTableService;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("biz.middleware.sharding.mapper")
//@TestPropertySource("application-local.properties")
@ActiveProfiles("local")
public class CreateTableServiceImplTest {

    @Resource
    private CreateTableService createTableService;

    @Resource
    private TStackAccompServiceImpl stackAccompService;

    @Resource
    private TPlDataServiceImpl plDataService;

    @Test
    public void createTableLike() {

        createTableService.createTableLike("t_stack_accomp_2020_06_03", "t_stack_accomp");
        createTableService.dropTable("t_stack_accomp_2020_06_03");
    }

    @Test
//    @TestTransaction
//    @Transactional
    public void test() {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            ch.qos.logback.classic.Logger logger = loggerContext.getLogger("biz.middleware.sharding.mapper");
            logger.setLevel(Level.DEBUG);

            logger = loggerContext.getLogger("ShardingSphere");
            logger.setLevel(Level.DEBUG);

            createTableService.createTableLike("t_stack_accomp_2020_06_02",
                    "t_stack_accomp");

            createTableService.createTableLike("t_stack_accomp_2020_06_03",
                    "t_stack_accomp");

            //createTableService.dropTable("t_stack_accomp_2020_06_03");

            Date date = DateUtils.parseDate("2020-06-03 13:33:00", "yyyy-MM-dd HH:mm:ss");
                    //LocalDateTime.of(2020, 6, 3, 13, 33);
            TStackAccomp entity = new TStackAccomp();
            entity.setCreateTime(new Date());
            entity.setEquipmentId(1);
            entity.setLineId(1);
            entity.setStackId(1);
            entity.setOutTime(date);

            stackAccompService.save(entity);


            date = DateUtils.parseDate("2020-06-02 13:33:00", "yyyy-MM-dd HH:mm:ss");
//            date = LocalDateTime.of(2020, 6, 2, 13, 33);
            TStackAccomp entity2 = new TStackAccomp();
            entity2.setCreateTime(new Date());
            entity2.setEquipmentId(1);
            entity2.setLineId(1);
            entity2.setStackId(1);
            entity2.setOutTime(date);
            stackAccompService.save(entity2);

        LambdaQueryWrapper<TStackAccomp> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(TStackAccomp::getOutTime, date);
        wrapper.last("limit 10");
        List<TStackAccomp> list = stackAccompService.list(wrapper);
        Assert.assertNotNull(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testId() {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            ch.qos.logback.classic.Logger logger = loggerContext.getLogger("biz.middleware.sharding.mapper");
            logger.setLevel(Level.DEBUG);

            logger = loggerContext.getLogger("ShardingSphere");
            logger.setLevel(Level.DEBUG);

            createTableService.createTableLike("t_pl_data_0",
                    "t_pl_data");

            createTableService.createTableLike("t_pl_data_1",
                    "t_pl_data");

            //createTableService.dropTable("t_stack_accomp_2020_06_03");

            Date date = DateUtils.parseDate("2020-06-03 13:33:00", "yyyy-MM-dd HH:mm:ss");
            //LocalDateTime.of(2020, 6, 3, 13, 33);
            TPlData entity = new TPlData();
            entity.setCreateTime(new Date());
            entity.setEquipmentId(1);
            entity.setLineId(1);
            entity.setCheckTime(date);
            entity.setCreateTime(new Date());
            entity.setDataId(9999);
            entity.setId(9999999L);
            plDataService.save(entity);


            date = DateUtils.parseDate("2020-06-02 13:33:00", "yyyy-MM-dd HH:mm:ss");
//            date = LocalDateTime.of(2020, 6, 2, 13, 33);
            TPlData entity2 = new TPlData();
            entity2.setCreateTime(new Date());
            entity2.setEquipmentId(1);
            entity2.setLineId(1);
            entity2.setCheckTime(date);
            entity2.setCreateTime(new Date());
            entity2.setId(10000000L);
            entity2.setDataId(9999);
            plDataService.save(entity2);

            LambdaQueryWrapper<TPlData> wrapper = new LambdaQueryWrapper<>();
            wrapper.ge(TPlData::getId, 1000L);
            wrapper.last("limit 10");
            List<TPlData> list = plDataService.list(wrapper);
            Assert.assertNotNull(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}