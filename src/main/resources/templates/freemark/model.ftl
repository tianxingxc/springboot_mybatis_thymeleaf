package ${packageName}.model;

import java.io.Serializable;
<#if table.hasDate>
import java.util.Date;
</#if>
<#if table.hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if table.comment?? >/** ${table.comment} */</#if>
public class ${table.className!} implements Serializable {

<#list table.allColumns as column>
    /**
     <#if column.comment??>* ${column.comment}</#if>
     <#if column.columnType??>* 数据库类型：${column.columnType}</#if>
     */
    private ${column.attrType} ${column.attrName};

</#list>

<#list table.allColumns as column>
    public void set${column.capitalAttrName}(${column.attrType} ${column.attrName}) {
        this.${column.attrName} = ${column.attrName};
    }

    public ${column.attrType} get${column.capitalAttrName}() {
        return ${column.attrName};
    }

</#list>
}
