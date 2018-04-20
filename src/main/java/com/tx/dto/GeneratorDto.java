package com.tx.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GeneratorDto {
    private String projectName;
    private String basePackageName;
    private String destOutputDir;
    private Map<String, String> tablePackageMap;
    private List<String> selectedTables;
}
