package com.tx.service;

import com.tx.model.Table;

import java.util.List;

public interface TableService {
    List<Table> queryTable(List<String> selectedTables);
}
