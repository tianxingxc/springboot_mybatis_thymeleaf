package ${packageName}.service.impl;

import ${basePackageName}.common.OffsetBean;

import java.util.List;
import ${packageName}.model.${table.className};
import ${packageName}.dao.${table.className}Dao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${packageName}.service.${table.className}Service;

@Service
public class ${table.className}ServiceImpl implements ${table.className}Service {

    @Resource
    private ${table.className}Dao ${table.instanceName}Dao;

    @Transactional
    @Override
    public Integer add${table.className}(${table.className} ${table.instanceName}) {
        return ${table.instanceName}Dao.add${table.className}(${table.instanceName});
    }

    @Transactional(readOnly = true)
    @Override
    public List<${table.className}> list${table.className}(${table.className} ${table.instanceName}, OffsetBean offsetBean) {
        return ${table.instanceName}Dao.list${table.className}(${table.instanceName}, offsetBean);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer total${table.className}(${table.className} ${table.instanceName}) {
        return ${table.instanceName}Dao.total${table.className}(${table.instanceName});
    }

    @Transactional(readOnly = true)
    @Override
    public ${table.className} get${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrType} ${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>) {
        return ${table.instanceName}Dao.get${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);
    }

    @Transactional
    @Override
    public Integer del${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrType} ${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>) {
        return ${table.instanceName}Dao.del${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);
    }

    @Transactional
    @Override
    public Integer update${table.className}(${table.className} ${table.instanceName}) {
        return ${table.instanceName}Dao.update${table.className}(${table.instanceName});
    }

}
