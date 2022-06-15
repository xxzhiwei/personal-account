package com.aojiaodage.enums;

public enum Importers {
    TEXT("txt", "纯文本文件");

    private final String value;

    private final String desc;

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    Importers(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
