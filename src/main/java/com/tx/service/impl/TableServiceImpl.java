package com.tx.service.impl;

import com.google.common.base.CaseFormat;
import com.tx.dao.TableDao;
import com.tx.enums.ColumnIndexEmun;
import com.tx.model.Column;
import com.tx.model.Table;
import com.tx.service.TableService;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class TableServiceImpl implements TableService {

    @Resource
    private TableDao tableDao;

    private PropertiesConfiguration configuration;

    @PostConstruct
    public void getConfig() throws Exception {
        Configurations configs = new Configurations();
        FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
        configuration = configs.properties("dataType.properties");
    }

    @Override
    public List<Table> queryTable(List<String> selectedTables) {

        List<Table> tables = tableDao.queryTable(selectedTables);

        if(CollectionUtils.isEmpty(tables))
            return null;

        for(Table table : tables) {

            table.setClassName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getTableName().toLowerCase()));
            table.setInstanceName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table.getTableName().toLowerCase()));

            List<Column> columns = tableDao.queryColumn(table.getTableName());

            //组装Table类
            if(CollectionUtils.isEmpty(columns))
                continue;

            List<Column> primaryKeys = new ArrayList<>();
            List<Column> otherColumns = new ArrayList<>();

            for(Column column : columns) {

                column.setAttrName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getColumnName().toLowerCase()));
                column.setCapitalAttrName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column.getColumnName().toLowerCase()));

                SetAttrType(table, column);

                //主键，忽略其他索引约束
                if(column.getIndexKey().equalsIgnoreCase(ColumnIndexEmun.PRI.getValue())) {
                    primaryKeys.add(column);
                    continue;
                }

                otherColumns.add(column);
            }

            table.setPrimaryKeys(primaryKeys);
            table.setOtherColumns(otherColumns);
            table.setAllColumns(columns);
        }

        return tables;
    }

    private void SetAttrType(Table table, Column column) {

        String dataType = column.getDataType();

        column.setAttrType(configuration.getString(dataType));

        if("decimal".contains(dataType))
            table.setHasBigDecimal(true);

        if(Arrays.asList("date", "datetime", "timestamp").contains(dataType))
            table.setHasDate(true);
    }
}
