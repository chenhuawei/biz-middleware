package biz.middleware.sharding;

import biz.middleware.sharding.config.BizMiddlewareShardingTablesConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BizMiddlewareShardingTablesConfig.class)

public class TestBizMiddlewareShardingApplication {

}

