package com.tx.enums;

public enum ColumnIndexEmun {

    /** 主键 */
    PRI("PRI"),

    /** 可多值 */
    MUL("MUL"),

    /** 唯一索引 */
    UNI("UNI");

    private String value;

    ColumnIndexEmun(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
