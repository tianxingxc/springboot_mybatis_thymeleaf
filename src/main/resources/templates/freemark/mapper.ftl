<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.dao.${table.className}Dao">

    <resultMap id="${table.instanceName}" type="${packageName}.model.${table.className}">
        <#list table.primaryKeys as column>
        <id column="${column.columnName}" property="${column.attrName}" />
        </#list>
        <#list table.otherColumns as column>
        <result column="${column.columnName}" property="${column.attrName}" />
        </#list>
    </resultMap>

    <select id="list${table.className}" resultMap="${table.instanceName}">
        select * from ${table.tableName} t
        <if test="${table.instanceName} != null">
            <where>
                <#list table.allColumns as column>
                    <#if column.extra != 'auto_increment'>
                    <if test="${table.instanceName}.${column.attrName} != null<#if column.attrType == 'String'> and ${table.instanceName}.${column.attrName} != ''</#if>">
                        and t.${column.columnName} = ${r'#{'}${table.instanceName}.${column.attrName}${r'}'}
                    </if>
                    </#if>
                </#list>
            </where>
        </if>
        <if test="offsetBean != null"> limit ${r'#{'}offsetBean.offset}, ${r'#{'}offsetBean.limit}</if>
    </select>
    
    <select id="get${table.className}ByPrimaryKey" resultMap="${table.instanceName}">
        select * from ${table.tableName} where <#list table.primaryKeys as column >${column.columnName} = ${r'#{'}${column.attrName}${'}'}<#if column_index < (table.primaryKeys?size - 1)> and </#if></#list>
    </select>
    
    <select id="total${table.className}" resultType="int" parameterType="${packageName}.model.${table.className}">
        select count(1) from ${table.tableName} t
        <if test="${table.instanceName} != null">
            <where>
                <#list table.allColumns as column>
                    <#if column.extra != 'auto_increment'>
                    <if test="${table.instanceName}.${column.attrName} != null<#if column.attrType == 'String'> and ${table.instanceName}.${column.attrName} != ''</#if>">
                        and t.${column.columnName} = ${r'#{'}${table.instanceName}.${column.attrName}${r'}'}
                    </if>
                    </#if>
                </#list>
            </where>
        </if>
    </select>

    <insert id="add${table.className}" parameterType="${packageName}.model.${table.className}">
        insert into ${table.tableName}(<#list table.primaryKeys as column><#if column.extra != 'auto_increment'>${column.columnName},</#if></#list><#list table.otherColumns as column>${column.columnName}<#if column_index < (table.otherColumns?size - 1)>, </#if></#list>)
        values (<#list table.primaryKeys as column><#if column.extra != 'auto_increment'>${r'#{'}${column.attrName}${'}'},</#if></#list><#list table.otherColumns as column>${r'#{'}${column.attrName}${'}'}<#if column_index < (table.otherColumns?size-1)>, </#if></#list>)
    </insert>

    <update id="update${table.className}" parameterType="${packageName}.model.${table.className}">
        update ${table.tableName}
        <set>
        <#list table.otherColumns as column>
            <#if column.isNullable == "NO">
            <if test="${column.attrName} != null<#if column.attrType == 'String'> and ${column.attrName} != ''</#if>">
            </#if>
                ${column.columnName} = ${r'#{'}${column.attrName}${r'}'},
            <#if column.isNullable == "NO">
            </if>
            </#if>
        </#list>
        </set>
        where <#list table.primaryKeys as column >${column.columnName} = ${r'#{'}${column.attrName}${'}'}<#if column_index < (table.primaryKeys?size - 1)> and </#if></#list>
    </update>
    
    <delete id="del${table.className}ByPrimaryKey">
        delete from ${table.tableName} where <#list table.primaryKeys as column >${column.columnName} = ${r'#{'}${column.attrName}${'}'}<#if column_index < (table.primaryKeys?size - 1)> and </#if></#list>
    </delete>

</mapper>
