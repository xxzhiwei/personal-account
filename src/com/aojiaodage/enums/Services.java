package com.aojiaodage.enums;

public enum Services {
    MENU(0, "菜单"),
    DETAIL(1, "收支明细"),
    INCOME(2, "记录收入"),
    COST(3, "记录支出"),
    EXIT(4, "退出");

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

    Services(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
