package biz.middleware.sharding.config;

import biz.middleware.sharding.service.CreateTableService;
import biz.middleware.sharding.service.impl.CreateTableServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("biz.middleware.sharding.mapper")
public class BizMiddlewareShardingTablesConfig {

    @Bean
    public CreateTableService createTableService() {
        return new CreateTableServiceImpl();
    }
}
