package biz.middleware.sharding.mapper;

import org.apache.ibatis.annotations.Param;

public interface CreateTableMapper {

    void createTableLike(@Param("dstTableName") String dstTableName,
                         @Param("srcTableName") String srcTableName);

    void dropTable(@Param("tableName") String tableName);
}
