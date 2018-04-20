package com.tx.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 数据库表
 */
@Data
@ToString
public class Table {

    /** 数据库名 */
    private String databaseName;

    /** 表名 */
    private String tableName;

    /** 表注释 */
    private String comment;

    /** 主键列集合 */
    private List<Column> primaryKeys;

    /** 其他列集合 */
    private List<Column> otherColumns;

    private List<Column> allColumns;

    /** 类型名 */
    private String className;

    /** 实例名 */
    private String instanceName;

    /** 所在子包名 */
    private String subPackageName = "";

    /** 是否具有时间字段 */
    private Boolean hasDate = false;

    /** 是否具有数值类型字段 */
    private Boolean hasBigDecimal = false;

    private Date createTime;
    private Date updateTime;
}
