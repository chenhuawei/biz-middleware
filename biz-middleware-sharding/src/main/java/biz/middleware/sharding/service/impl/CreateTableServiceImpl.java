package biz.middleware.sharding.service.impl;

import biz.middleware.sharding.mapper.CreateTableMapper;
import biz.middleware.sharding.service.CreateTableService;

import javax.annotation.Resource;

public class CreateTableServiceImpl implements CreateTableService {

    @Resource
    private CreateTableMapper createTableMapper;

    @Override
    public void createTableLike(String dstTableName, String srcTableName) {
        createTableMapper.createTableLike(dstTableName, srcTableName);
    }

    @Override
    public void dropTable(String tableName) {
        createTableMapper.dropTable(tableName);
    }

}
