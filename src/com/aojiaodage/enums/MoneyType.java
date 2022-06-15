package com.aojiaodage.enums;

public enum MoneyType {
    INCOME(1, "收入"),
    COST(2, "支出");

    private Integer value;
    private String desc;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    MoneyType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
