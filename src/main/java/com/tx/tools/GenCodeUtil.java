package com.tx.tools;

import com.google.common.base.CaseFormat;
import com.tx.model.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class GenCodeUtil {

    private final Map<String, String> tplPkgRelMap = new HashMap<>();
    private final Map<String, String> nameRelMap = new HashMap<>();
    private final List<String> pageTemplates = new ArrayList<>();

    //源代码基础目录
    private static final String SRC_BASE_DIR = "src/main/java/";
    private static final String RES_BASE_DIR = "src/main/resources/";
    private static final String TEST_SRC_BASE_DIR = "src/test/java/";
    private static final String TEST_RES_BASE_DIR = "src/test/resources/";
    private static final String COMMON_DIR = "common";

    {
        tplPkgRelMap.put("dao.ftl", "dao");
        tplPkgRelMap.put("mapper.ftl", "mapper");
        tplPkgRelMap.put("model.ftl", "model");
        tplPkgRelMap.put("service.ftl", "service");
        tplPkgRelMap.put("serviceImpl.ftl", "service.impl");
        tplPkgRelMap.put("controller.ftl", "controller");
        tplPkgRelMap.put("exception.ftl", "exception");
        tplPkgRelMap.put("offsetBean.ftl", "common");
        tplPkgRelMap.put("pageBean.ftl", "common");

        nameRelMap.put("dao.ftl", "Dao.java");
        nameRelMap.put("mapper.ftl", "Mapper.xml");
        nameRelMap.put("model.ftl", ".java");
        nameRelMap.put("service.ftl", "Service.java");
        nameRelMap.put("serviceImpl.ftl", "ServiceImpl.java");
        nameRelMap.put("controller.ftl", "Controller.java");
        nameRelMap.put("main.ftl", "Application.java");
        nameRelMap.put("test.ftl", "ApplicationTest.java");
        nameRelMap.put("exception.ftl", "BusinessException.java");
        nameRelMap.put("offsetBean.ftl", "OffsetBean.java");
        nameRelMap.put("pageBean.ftl", "PageBean.java");

        // 页面部分
        nameRelMap.put("list.ftl", "_list.html");
        nameRelMap.put("add.ftl", "_add.html");
        nameRelMap.put("update.ftl", "_update.html");

        pageTemplates.add("list.ftl");
        pageTemplates.add("update.ftl");
        pageTemplates.add("add.ftl");
    }

    /**
     * 生成代码
     */
    public void generateCode(String projectName, String basePackageName, List<Table> tables, String destOutputDir) throws Exception {

        String capitalProjectName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, projectName);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        File templatesDir = new File(this.getClass().getResource("/").getPath() + "/templates/freemark");

        List<String> templateNames = new ArrayList<>();
        File[] childs = null;
        if(templatesDir.isDirectory())
            childs = templatesDir.listFiles();
        if(childs != null) {
            for (File file : childs)
                if(!Arrays.asList("main.ftl","test.ftl", "exception.ftl", "offsetBean.ftl", "pageBean.ftl").contains(file.getName()))
                    templateNames.add(file.getName());
        }

        String destDir = destOutputDir + "/" + projectName;

        cfg.setDirectoryForTemplateLoading(templatesDir);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        String packagePath = basePackageName.replace(".", "/");

        //静态文件目的目录
        File resourcesDir = new File(destDir + "/" + RES_BASE_DIR);

        String destTplDir = destDir + "/" + RES_BASE_DIR + "templates";
        File destTeimplatesDir = new File(destDir + "/" + RES_BASE_DIR + "templates");
        String outputPath = destDir + "/" + SRC_BASE_DIR + packagePath;


        Map<String, Object> root = new HashMap<>();

        root.put("capitalProjectName", capitalProjectName);
        root.put("packageName", basePackageName);
        root.put("basePackageName", basePackageName);

        //Application启动类
        File mainDir = new File(outputPath);
        if(!mainDir.exists()) mainDir.mkdirs();

        Template tpl = cfg.getTemplate("main.ftl");
        String fileName = outputPath + "/" + capitalProjectName + nameRelMap.get("main.ftl");
        Writer out = new OutputStreamWriter(new FileOutputStream(fileName));
        tpl.process(root, out);
        out.flush();
        out.close();

        //测试类与 test resources
        String testSrcPath = destDir + "/" + TEST_SRC_BASE_DIR;
        String testResPath = destDir + "/" + TEST_RES_BASE_DIR;

        File testSrcPkgDir = new File(testSrcPath + packagePath);
        File testResDir = new File(testResPath);

        if(!testResDir.exists()) testResDir.mkdirs();
        if(!testSrcPkgDir.exists()) testSrcPkgDir.mkdirs();

        fileName = testSrcPath + packagePath + "/" + capitalProjectName + nameRelMap.get("test.ftl");
        tpl = cfg.getTemplate("test.ftl");
        out = new OutputStreamWriter(new FileOutputStream(fileName));
        tpl.process(root, out);
        out.flush();
        out.close();

        //业务异常类
        tpl = cfg.getTemplate("exception.ftl");
        fileName = outputPath + "/" + tplPkgRelMap.get("exception.ftl");
        File exceptionDir = new File(fileName);
        if(!exceptionDir.exists()) exceptionDir.mkdirs();

        fileName += "/" + nameRelMap.get("exception.ftl");

        out = new OutputStreamWriter(new FileOutputStream(fileName));
        tpl.process(root, out);
        out.flush();
        out.close();

        //分页辅助类
        String commonSrcPath = outputPath + "/" + COMMON_DIR;
        File commonSrc = new File(commonSrcPath);

        if(!commonSrc.exists()) commonSrc.mkdirs();

        tpl = cfg.getTemplate("offsetBean.ftl");
        String offsetFileName = commonSrcPath + "/" + nameRelMap.get("offsetBean.ftl");
        out = new OutputStreamWriter(new FileOutputStream(offsetFileName));
        tpl.process(root, out);
        out.flush();
        out.close();

        tpl = cfg.getTemplate("pageBean.ftl");
        String pageFileName = commonSrcPath + "/" + nameRelMap.get("pageBean.ftl");
        out = new OutputStreamWriter(new FileOutputStream(pageFileName));
        tpl.process(root, out);
        out.flush();
        out.close();

        // 表结构
        for(Table table : tables) {

            root.put("table", table);
            root.put("capitalProjectName", capitalProjectName);

            if (CollectionUtils.isEmpty(templateNames))
                return;

            for (String templateName : templateNames) {

                Template temp = cfg.getTemplate(templateName);

                String subPackageName = table.getSubPackageName();
                if(pageTemplates.contains(templateName)) {
                    //生成静态HTML模板，使用thymeleaf

                    String subDirName;
                    if(StringUtils.isNotBlank(subPackageName))
                        subDirName = destTplDir + "/" + subPackageName + "/" + table.getTableName();
                    else
                        subDirName = destTplDir + "/" + table.getTableName();

                    File subResDir = new File(subDirName);

                    if(!subResDir.exists()) subResDir.mkdirs();

                    fileName = subDirName + "/" + table.getTableName() + nameRelMap.get(templateName);
                    out = new OutputStreamWriter(new FileOutputStream(fileName));
                    temp.process(root, out);
                    out.flush();
                    out.close();
                    continue;
                }

                String packageName = basePackageName;
                if(StringUtils.isNotBlank(subPackageName))
                    packageName += "." + subPackageName;
                root.put("packageName", packageName);
                String outputDir = tplPkgRelMap.get(templateName);
                if(StringUtils.isNotBlank(subPackageName))
                    outputDir = outputPath + "/" + subPackageName + "/" + outputDir.replace(".", "/");
                else
                    outputDir = outputPath + "/" + outputDir.replace(".", "/");

                File outDir = new File(outputDir);
                if(!outDir.exists())
                    outDir.mkdirs();

                fileName = outputDir + "/" + table.getClassName() + nameRelMap.get(templateName);
                out = new OutputStreamWriter(new FileOutputStream(fileName));
                temp.process(root, out);
                out.flush();
                out.close();
            }
        }

        //拷贝静态文件至resources下
        if(!resourcesDir.exists()) resourcesDir.mkdirs();
        if(!destTeimplatesDir.exists()) destTeimplatesDir.mkdirs();

        File staticDir = new File(this.getClass().getResource("/").getPath() + "/templates/static");
        File templateDir = new File(this.getClass().getResource("/").getPath() + "/templates/fragment");

        if(staticDir.exists())
            FileUtils.copyDirectoryToDirectory(staticDir, resourcesDir);

        if(templateDir.exists())
            FileUtils.copyDirectoryToDirectory(templateDir, destTeimplatesDir);

        //拷贝POM文件到项目目录下
        FileUtils.copyFileToDirectory(new File(this.getClass().getResource("/").getPath() + "/templates/" + "pom.xml"), new File(destDir));
        FileUtils.copyFileToDirectory(new File(this.getClass().getResource("/").getPath() + "/templates/" + "application.properties"), new File(destDir + "/" + RES_BASE_DIR));
    }

    /**
     * 生成代码，并下载
     * @param projectName
     * @param basePackageName
     * @param tables
     * @throws Exception
     */
    public void generateCodeAndDownload(String projectName, String basePackageName, List<Table> tables,ZipOutputStream zipOutputStream) throws Exception {

        String capitalProjectName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, projectName);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        File templatesDir = new File(this.getClass().getResource("/").getPath() + "/templates/freemark");

        List<String> templateNames = new ArrayList<>();
        File[] childs = null;
        if(templatesDir.isDirectory())
            childs = templatesDir.listFiles();

        if(childs != null) {
            for (File file : childs)
                if(!Arrays.asList("main.ftl","test.ftl", "exception.ftl", "offsetBean.ftl", "pageBean.ftl").contains(file.getName()))
                    templateNames.add(file.getName());
        }

        cfg.setDirectoryForTemplateLoading(templatesDir);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        String packagePath = basePackageName.replace(".", "/");

        //静态文件目的目录
        String destTplDir = projectName + "/" + RES_BASE_DIR + "templates";
        String outputPath = projectName + "/" + SRC_BASE_DIR + packagePath;

        Map<String, Object> root = new HashMap<>();
        root.put("packageName", basePackageName);
        root.put("basePackageName", basePackageName);
        root.put("capitalProjectName", capitalProjectName);

        Template tpl = cfg.getTemplate("main.ftl");
        String fileName = outputPath + "/" + capitalProjectName + nameRelMap.get("main.ftl");
        StringWriter out = new StringWriter();
        tpl.process(root, out);
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        zipOutputStream.write(out.toString().getBytes("utf-8"));
        zipOutputStream.closeEntry();
        out.close();

        String testSrcPath = projectName + "/" + TEST_SRC_BASE_DIR;
        String testResPath = projectName + "/" + TEST_RES_BASE_DIR;

        zipOutputStream.putNextEntry(new ZipEntry(testResPath));
        zipOutputStream.closeEntry();

        // 测试类
        fileName = testSrcPath + packagePath + "/" + capitalProjectName + nameRelMap.get("test.ftl");
        tpl = cfg.getTemplate("test.ftl");
        out = new StringWriter();
        tpl.process(root, out);
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        zipOutputStream.write(out.toString().getBytes("utf-8"));
        zipOutputStream.closeEntry();
        out.close();

        // 异常类
        fileName = outputPath + "/" + tplPkgRelMap.get("exception.ftl") + "/" + nameRelMap.get("exception.ftl");
        tpl = cfg.getTemplate("exception.ftl");
        out = new StringWriter();
        tpl.process(root, out);
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        zipOutputStream.write(out.toString().getBytes("utf-8"));
        zipOutputStream.closeEntry();
        out.close();

        //分页辅助
        String commonSrcPath = outputPath + "/" + COMMON_DIR;
        String offsetFileName = commonSrcPath + "/" + nameRelMap.get("offsetBean.ftl");
        tpl = cfg.getTemplate("offsetBean.ftl");
        out = new StringWriter();
        tpl.process(root, out);
        zipOutputStream.putNextEntry(new ZipEntry(offsetFileName));
        zipOutputStream.write(out.toString().getBytes("utf-8"));
        zipOutputStream.closeEntry();
        out.close();

        String pageFileName = commonSrcPath + "/" + nameRelMap.get("pageBean.ftl");
        tpl = cfg.getTemplate("pageBean.ftl");
        out = new StringWriter();
        tpl.process(root, out);
        zipOutputStream.putNextEntry(new ZipEntry(pageFileName));
        zipOutputStream.write(out.toString().getBytes("utf-8"));
        zipOutputStream.closeEntry();
        out.close();

        for(Table table : tables) {

            root.put("table", table);

            if (CollectionUtils.isEmpty(templateNames))
                return;

            for (String templateName : templateNames) {

                Template temp = cfg.getTemplate(templateName);

                String subPackageName = table.getSubPackageName();

                if(pageTemplates.contains(templateName)) {
                    //生成静态HTML模板，使用thymeleaf
                    String subDirName;
                    if(StringUtils.isNotBlank(subPackageName))
                        subDirName = destTplDir + "/" + subPackageName + "/" + table.getTableName();
                    else
                        subDirName = destTplDir + "/" + table.getTableName();

                    fileName = subDirName + "/" + table.getTableName() + nameRelMap.get(templateName);
                    out = new StringWriter();
                    temp.process(root, out);

                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    zipOutputStream.write(out.toString().getBytes("utf-8"));
                    zipOutputStream.closeEntry();
                    continue;
                }

                String outputDir = tplPkgRelMap.get(templateName);
                if(StringUtils.isNotBlank(subPackageName))
                    outputDir = outputPath + "/" + subPackageName + "/" + outputDir.replace(".", "/");
                else
                    outputDir = outputPath + "/" + outputDir.replace(".", "/");

                fileName = outputDir + "/" + table.getClassName() + nameRelMap.get(templateName);
                out = new StringWriter();
                String packageName = basePackageName;
                if(StringUtils.isNotBlank(subPackageName))
                    packageName += "." + subPackageName;
                root.put("packageName", packageName);

                temp.process(root, out);

                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                zipOutputStream.write(out.toString().getBytes("utf-8"));
                zipOutputStream.closeEntry();
                out.close();
            }
        }

        //拷贝静态文件至resources下
        File staticDir = new File(this.getClass().getResource("/").getPath() + "/templates/static");
        File templateDir = new File(this.getClass().getResource("/").getPath() + "/templates/fragment");
        String baseDir = projectName + "/src/main/resources";
        if(staticDir.exists())
            setZipEntity(zipOutputStream, staticDir, baseDir);

        if(templateDir.exists())
            setZipEntity(zipOutputStream, templateDir, baseDir + "/templates");

        //拷贝POM文件到项目目录下
        setZipEntity(zipOutputStream, new File(this.getClass().getResource("/").getPath() + "/templates/" + "pom.xml"), projectName + "/pom.xml");
        setZipEntity(zipOutputStream, new File(this.getClass().getResource("/").getPath() + "/templates/" + "application.properties"),projectName + "/" + RES_BASE_DIR + "application.properties");
    }

    /**
     * 设置ZIP ENTITY
     */
    private void setZipEntity(ZipOutputStream zipOutputStream, File file, String baseDir) throws Exception{
        if(!file.exists()) return;

        if(file.isDirectory()) {
            File[] chList = file.listFiles();

            if(chList == null)
                return;

            for(File f : chList) {
                StringBuffer sb = new StringBuffer(baseDir);
                sb.append("/" + file.getName());
                if(!f.isDirectory())
                    sb.append("/" + f.getName());
                setZipEntity(zipOutputStream, f, sb.toString());
            }
        } else {
            FileInputStream fip = new FileInputStream(file);
            zipOutputStream.putNextEntry(new ZipEntry(baseDir));
            zipOutputStream.write(IOUtils.readFully(fip, fip.available()));
            fip.close();
            zipOutputStream.closeEntry();

        }
    }

}
