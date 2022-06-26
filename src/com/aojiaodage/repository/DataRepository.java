package com.aojiaodage.repository;

import com.aojiaodage.annotations.Component;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.handler.TextDataHandler;
import com.aojiaodage.io.Reader;

import java.util.List;

@Component
public class DataRepository {
    private List<Detail> details;
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
        for (Detail detail : details) {
            if (MoneyType.INCOME.getValue().equals(detail.getType())) {
                total += detail.getMoney();
            } else {
                total -= detail.getMoney();
            }
        }
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    // 构造器中，不可使用@Autowired的属性（还没有注入
    public DataRepository(Reader reader, TextDataHandler textDataHandler) {
        try {
            this.details = reader.read(textDataHandler);
            calculateTotal();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("读取数据失败");
        }
    }
}
