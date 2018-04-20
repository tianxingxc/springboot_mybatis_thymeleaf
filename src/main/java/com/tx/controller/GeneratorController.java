package com.tx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.dto.GeneratorDto;
import com.tx.model.Table;
import com.tx.service.TableService;
import com.tx.tools.GenCodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Controller
public class GeneratorController {

    @Autowired
    private TableService tableService;

    @Autowired
    private GenCodeUtil genCodeUtil;

    @GetMapping("/")
    public String listTable(Map<String, Object> map) {

        // 获取系统盘符列表
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] files = File.listRoots();

        Map<String, String> diskMap = new HashMap<>();
        for(File file : files) {
            diskMap.put(file.getAbsolutePath(), fsv.getSystemDisplayName(file));
        }

        List<Table> tables = tableService.queryTable(null);

        map.put("diskMap", diskMap);
        map.put("tables", tables);
        return "table_list";
    }

    @ResponseBody
    @PostMapping("/gen")
    public Object generateCode(@RequestBody GeneratorDto generatorDto) throws Exception{

        if(CollectionUtils.isEmpty(generatorDto.getSelectedTables()))
            return "请选择需要生成的数据库表";

        List<Table> tables = tableService.queryTable(generatorDto.getSelectedTables());

        Map<String, String> tablePackageMap = generatorDto.getTablePackageMap();

        for(Table table : tables) {
            String packageName = tablePackageMap.get(table.getTableName());
            if(StringUtils.isNoneBlank(packageName))
                table.setSubPackageName(packageName);
        }

        //生成代码
        genCodeUtil.generateCode(generatorDto.getProjectName(), generatorDto.getBasePackageName(), tables, generatorDto.getDestOutputDir());
        return "已经成功生成了代码,位于目录: " + generatorDto.getDestOutputDir() + ", 项目目录名: " + generatorDto.getProjectName();
    }

    @PostMapping("/download")
    public void generateCodeAndDownload(String reqStr, HttpServletResponse response) throws Exception{

        GeneratorDto generatorDto = new ObjectMapper().readValue(reqStr, GeneratorDto.class);

        if(CollectionUtils.isEmpty(generatorDto.getSelectedTables()))
            return;

        List<Table> tables = tableService.queryTable(generatorDto.getSelectedTables());

        Map<String, String> tablePackageMap = generatorDto.getTablePackageMap();

        for(Table table : tables) {
            String packageName = tablePackageMap.get(table.getTableName());
            if(StringUtils.isNoneBlank(packageName))
                table.setSubPackageName(packageName);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        //生成代码
        genCodeUtil.generateCodeAndDownload(generatorDto.getProjectName(),generatorDto.getBasePackageName(),tables, zipOutputStream);
        zipOutputStream.flush();
        zipOutputStream.finish();
        byte[] bytes = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + generatorDto.getProjectName() +".zip\"");
        response.setContentType("application/octet-stream; charset=UTF-8");
        ServletOutputStream stream = response.getOutputStream();
        stream.write(bytes);
    }

}
