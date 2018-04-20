package com.tx;

import com.tx.model.Table;
import com.tx.service.TableService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.tx.MybatisGenerator.class)
public class MybatisGeneratorTest {

    @Resource
    private TableService tableService;

    @Test
    public void testQueryTalbe() {
        List<Table> tables = tableService.queryTable(null);
        System.out.println(tables);
    }

}
