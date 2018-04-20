package ${packageName}.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.List;

import ${basePackageName}.common.OffsetBean;
import ${basePackageName}.common.PageBean;

import ${packageName}.model.${table.className};
import org.springframework.web.bind.annotation.RequestParam;
import ${packageName}.service.${table.className}Service;

@Controller
@RequestMapping("/${table.subPackageName}/${table.instanceName}")
public class ${table.className}Controller {

    @Resource
    private ${table.className}Service ${table.instanceName}Service;

    @GetMapping("/list")
    public String list${table.className}(${table.className} ${table.instanceName}, Map<String, Object> map, @RequestParam(name="page", defaultValue = "1") int current) {
        List<${table.className}> ${table.instanceName}List = ${table.instanceName}Service.list${table.className}(null, new OffsetBean(current));
        map.put("${table.instanceName}List", ${table.instanceName}List);
        int total${table.className} = ${table.instanceName}Service.total${table.className}(null);
        map.put("pageBean", new PageBean(current, total${table.className}));
        return "${table.subPackageName}/${table.tableName}/${table.tableName}_list";
    }

    @PostMapping("/list")
    public String list${table.className}ByCondition(${table.className} ${table.instanceName}, Map<String, Object> map, @RequestParam(name="page", defaultValue = "1") int current) {
        List<${table.className}> ${table.instanceName}List = ${table.instanceName}Service.list${table.className}(${table.instanceName}, new OffsetBean(current));
        map.put("${table.instanceName}List", ${table.instanceName}List);
        int total${table.className} = ${table.instanceName}Service.total${table.className}(${table.instanceName});
        map.put("pageBean", new PageBean(current, total${table.className}));
        return "${table.subPackageName}/${table.tableName}/${table.tableName}_list";
    }

    @GetMapping("/add")
    public String add${table.className}(${table.className} ${table.instanceName}) {
        return "${table.subPackageName}/${table.tableName}/${table.tableName}_add";
    }

    @PostMapping("/add")
    public String insert${table.className}(@Valid ${table.className} ${table.instanceName}, BindingResult error) {

        if(error.hasErrors())
            return "${table.subPackageName}/${table.tableName}/${table.tableName}_add";

        ${table.instanceName}Service.add${table.className}(${table.instanceName});
        return "redirect:/${table.subPackageName}/${table.instanceName}/list";
    }

    @GetMapping("/update/<#list table.primaryKeys as column >{${column.attrName}}<#if column_index < (table.primaryKeys?size - 1)>/</#if></#list>")
    public String modify${table.className}(Map<String, Object> map, <#list table.primaryKeys as column >@PathVariable("${column.attrName}") ${column.attrType} ${column.attrName}<#if column_index < (table.primaryKeys?size - 1)>, </#if></#list>) {
        ${table.className} ${table.instanceName} = ${table.instanceName}Service.get${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);
        map.put("${table.instanceName}", ${table.instanceName});
        return "${table.subPackageName}/${table.tableName}/${table.tableName}_update";
    }

    @PostMapping("/update")
    public String update${table.className}(@Valid ${table.className} ${table.instanceName}, BindingResult error) {

        if(error.hasErrors())
            return "${table.subPackageName}/${table.tableName}/${table.tableName}_update";

        ${table.instanceName}Service.update${table.className}(${table.instanceName});
        return "redirect:/${table.subPackageName}/${table.instanceName}/list";
    }

    @PostMapping("/delete/<#list table.primaryKeys as column >{${column.attrName}}<#if column_index < (table.primaryKeys?size - 1)>/</#if></#list>")
    public String delete${table.className}(<#list table.primaryKeys as column >@PathVariable("${column.attrName}") ${column.attrType} ${column.attrName}<#if column_index < (table.primaryKeys?size - 1)>, </#if></#list>) {
        ${table.instanceName}Service.del${table.className}ByPrimaryKey(<#list table.primaryKeys as column>${column.attrName}<#if column_index < table.primaryKeys?size - 1>, </#if></#list>);
        return "redirect:/${table.subPackageName}/${table.instanceName}/list";
    }

}