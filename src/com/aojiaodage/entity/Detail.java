package com.aojiaodage.entity;

public class Detail {
    private Double money;
    private String remark;
    private String date;
    private Integer type;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static Detail makeFromDetailStr(String detailStr) {
        if (detailStr == null || "".equals(detailStr.trim())) {
            return null;
        }
        String[] detailStrArr = detailStr.split(",");
        Detail detail = new Detail();
        if (detailStrArr.length != 4) {
            throw new RuntimeException("数据格式不正确");
        }
        detail.money = Double.parseDouble(detailStrArr[0]);
        detail.type = Integer.parseInt(detailStrArr[1]);
        detail.remark = detailStrArr[2];
        detail.date = detailStrArr[3];
        return detail;
    }

    public static String formatAsDetailStr(Detail detail) {
        return detail.getMoney() +
                "," +
                detail.getType() +
                "," +
                detail.getRemark() +
                "," +
                detail.getDate();
    }

    public String toString() {
        return formatAsDetailStr(this);
    }
}
