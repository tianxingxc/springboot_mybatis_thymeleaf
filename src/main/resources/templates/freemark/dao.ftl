package ${packageName}.dao;

import ${basePackageName}.common.OffsetBean;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import ${packageName}.model.${table.className};

@Mapper
public interface ${table.className}Dao {

    Integer add${table.className}(${table.className} ${table.instanceName});

    List<${table.className}> list${table.className}(@Param("${table.instanceName}") ${table.className} ${table.instanceName}, @Param("offsetBean") OffsetBean offsetBean);

    ${table.className} get${table.className}ByPrimaryKey(<#list table.primaryKeys as column>@Param("${column.attrName}") ${column.attrType} ${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);

    Integer del${table.className}ByPrimaryKey(<#list table.primaryKeys as column>@Param("${column.attrName}") ${column.attrType} ${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);

    Integer update${table.className}(${table.className} ${table.instanceName});

    Integer total${table.className}(@Param("${table.instanceName}") ${table.className} ${table.instanceName});

}
