package com.aojiaodage.enums;

public enum Services {
    DETAIL("收支明细"),
    INCOME("记录收入"),
    COST("记录支出"),
    EXIT("退出");

    private final String desc;

    public String getDesc() {
        return desc;
    }

    Services(String desc) {
        this.desc = desc;
    }
}
