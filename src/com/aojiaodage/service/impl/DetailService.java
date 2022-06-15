package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.service.AccountService;

import java.util.List;

public class DetailService extends AccountService {

    private final DataRepository dataRepository;

    public DetailService(Integer id, String name, DataRepository dataRepository) {
        super(id, name);
        this.dataRepository = dataRepository;
    }

    @Override
    public int execute() {
        System.out.println("《收支明细记录》");
        System.out.println("金额/类型/备注/日期\n");
        System.out.println("----------------");
        List<Detail> details = dataRepository.getDetails();
        for (Detail detail : details) {
            String typeDesc = detail.getType().equals(MoneyType.INCOME.getValue()) ? MoneyType.INCOME.getDesc() : MoneyType.COST.getDesc();
            System.out.println(detail.getMoney() + "," + typeDesc + "," + detail.getRemark() + "," + detail.getDate());
        }
        if (details.isEmpty()) {
            System.out.println("当前没有任何收支记录");
        }
        System.out.println("----------------");
        System.out.println("可用金额为：" + dataRepository.getTotal());
        return 0;
    }
}
