package com.aojiaodage.enums;

public enum MoneyType {
    INCOME(1, "收入"),
    COST(2, "支出");

    private final Integer value;
    private final String desc;

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    MoneyType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
