package com.aojiaodage.enums;

public enum Services {
    DETAIL("收支明细"),
    INCOME("登记收入"),
    COST("登记支出"),
    EXIT("退出");

    private final String desc;

    public String getDesc() {
        return desc;
    }

    Services(String desc) {
        this.desc = desc;
    }
}
