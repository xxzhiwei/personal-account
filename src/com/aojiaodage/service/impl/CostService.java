package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.enums.Services;
import com.aojiaodage.util.CommandLineUtil;
import com.aojiaodage.Application;

public class CostService extends RecordService {

    public CostService(Application application) {
        super(Services.COST.getDesc(), application);
    }

    @Override
    public Detail makeDetail() {
        System.out.print("本次" + MoneyType.COST.getDesc() + "金额：");
        double money = CommandLineUtil.readDouble("请输入正确的金额：");
        if (application.getDataRepository().getTotal() < money) {
            System.out.println("\n>> 余额不足");
            return null;
        }
        Detail detail = new Detail();
        detail.setType(MoneyType.COST.getValue());
        detail.setMoney(money);
        System.out.print("本次" + MoneyType.COST.getDesc() + "说明：");
        String str = CommandLineUtil.readStr("请输入说明：");
        detail.setRemark(str);
        return detail;
    }
}
