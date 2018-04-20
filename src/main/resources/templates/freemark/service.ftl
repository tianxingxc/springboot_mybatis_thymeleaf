package ${packageName}.service;

import ${basePackageName}.common.OffsetBean;

import java.util.List;
import ${packageName}.model.${table.className};

public interface ${table.className}Service {

    Integer add${table.className}(${table.className} ${table.instanceName});

    List<${table.className}> list${table.className}(${table.className} ${table.instanceName}, OffsetBean offsetBean);

    ${table.className} get${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrType} ${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);

    Integer del${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrType} ${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);

    Integer update${table.className}(${table.className} ${table.instanceName});

    Integer total${table.className}(${table.className} ${table.instanceName});

}
