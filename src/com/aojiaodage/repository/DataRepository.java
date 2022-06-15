package com.aojiaodage.repository;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;

import java.util.List;

public class DataRepository {
    private final List<Detail> details;
    private Double total;

    public List<Detail> getDetails() {
        return details;
    }
    public void addDetail(Detail detail) {
        details.add(detail);
        if (MoneyType.INCOME.getValue().equals(detail.getType())) {
            total += detail.getMoney();
        } else {
            total -= detail.getMoney();
        }
    }

    public void addDetails(List<Detail> details) {
        this.details.addAll(details);
        calculateTotal();
    }

    private void calculateTotal() {
        Double total = 0.0;
        if (details != null) {
            for (Detail detail : details) {
                if (MoneyType.INCOME.getValue().equals(detail.getType())) {
                    total += detail.getMoney();
                } else {
                    total -= detail.getMoney();
                }
            }
        }
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public DataRepository(List<Detail> details) {
        this.details = details;
        calculateTotal();
    }
}
