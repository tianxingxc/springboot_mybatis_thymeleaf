package com.tx.dao;

import com.tx.model.Column;
import com.tx.model.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TableDao {

    List<Table> queryTable(@Param("tableNames") List<String> tableNames);

    List<Column> queryColumn(@Param("tableName") String tableName);
}
