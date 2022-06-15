package com.aojiaodage.service.impl;

import com.aojiaodage.enums.Services;
import com.aojiaodage.util.CommandLineUtil;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.Application;

public class IncomeService extends RecordService {

    public IncomeService(Application application) {
        super(Services.INCOME.getDesc(), application);
    }

    @Override
    public Detail makeDetail() {
        System.out.print("本次" + MoneyType.INCOME.getDesc() + "金额：");
        double money = CommandLineUtil.readDouble("请输入正确的金额：");
        Detail detail = new Detail();
        detail.setType(MoneyType.INCOME.getValue());
        detail.setMoney(money);
        System.out.print("本次" + MoneyType.INCOME.getDesc() + "说明：");
        String str = CommandLineUtil.readStr("请输入说明：");
        detail.setRemark(str);
        return detail;
    }
}
