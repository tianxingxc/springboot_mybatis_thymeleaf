package com.tx.model;

import lombok.Data;
import lombok.ToString;

/**
 * 列属性
 */
@Data
@ToString
public class Column {


    /** 数据库名 */
    private String databaseName;

    /** 表名 */
    private String tableName;

    /** 列名 */
    private String columnName;

    /** 列注释 */
    private String comment;

    /** 数据类型 */
    private String dataType;

    /** 属性类型 */
    private String attrType;

    /** 列类型 */
    private String columnType;

    /** 对应属性名 */
    private String attrName;

    /** 属性首字母大写 */
    private String capitalAttrName;

    /** 索引键 */
    private String indexKey;

    /** 字段长度 */
    private Integer textLength;

    /** 字段扩展属性 如：auto_increment */
    private String extra;

    /** 是否可以为空 */
    private String isNullable;
}
