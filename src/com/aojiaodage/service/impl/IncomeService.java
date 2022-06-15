package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.util.CommandLineUtil;

public class IncomeService extends RecordService {

    public IncomeService(Integer id, String name, DataRepository dataRepository) {
        super(id, name, dataRepository);
    }

    @Override
    public Detail makeDetail() {
        System.out.print("本次" + MoneyType.INCOME.getDesc() + "金额：");
        int money = CommandLineUtil.readNum("请输入正确的金额");
        Detail detail = new Detail();
        detail.setType(MoneyType.INCOME.getValue());
        detail.setMoney(money);
        System.out.print("本次" + MoneyType.INCOME.getDesc() + "说明：");
        String str = CommandLineUtil.readStr("请输入说明：");
        detail.setRemark(str);
        return detail;
    }

    @Override
    public void calculateTotal(Integer money) {
        this.getDataRepository().increase(money);
    }

    @Override
    public int execute() {
        return super.execute();
    }
}
