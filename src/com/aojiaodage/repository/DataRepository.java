package com.aojiaodage.repository;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.io.Writer;

import java.util.List;

public class DataRepository {
    private final List<Detail> details;
    private Integer total;
    private final Writer writer;

    public List<Detail> getDetails() {
        return details;
    }

    public Integer getTotal() {
        return total;
    }

    public Writer getWriter() {
        return writer;
    }

    public DataRepository(List<Detail> details, Writer writer) {
        this.details = details;
        this.writer = writer;
        Integer total = 0;
        for (Detail detail : details) {
            if (MoneyType.INCOME.getValue().equals(detail.getType())) {
                total += detail.getMoney();
            }
            else {
                total -= detail.getMoney();
            }
        }
        this.total = total;
    }

    public void increase(Integer money) {
        total += money;
    }

    public void decrease(Integer money) {
        total -= money;
    }
}
