package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.enums.Services;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.Application;

import java.util.List;

public class DetailService extends AccountService {

    public DetailService(Application application) {
        super(Services.DETAIL.getDesc(), application);
    }

    @Override
    public void execute() {
        System.out.println("\n《收支明细记录》");
        System.out.println("金额/类型/备注/日期");
        System.out.println("----------------");
        List<Detail> details = application.getDataRepository().getDetails();
        for (Detail detail : details) {
            String typeDesc = detail.getType().equals(MoneyType.INCOME.getValue()) ? MoneyType.INCOME.getDesc() : MoneyType.COST.getDesc();
            System.out.println(detail.getMoney() + "," + typeDesc + "," + detail.getRemark() + "," + detail.getDate());
        }
        if (details.isEmpty()) {
            System.out.println("当前没有任何收支记录");
        }
        System.out.println("----------------");
        System.out.println("可用金额为：" + application.getDataRepository().getTotal());
    }
}
