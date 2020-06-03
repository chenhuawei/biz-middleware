package biz.middleware.sharding.service;

public interface CreateTableService {

    void createTableLike(String dstTableName, String srcTableName);

    void dropTable(String tableName);
}
