package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.util.CommandLineUtil;

public class CostService extends RecordService {

    public CostService(Integer id, String name, DataRepository dataRepository) {
        super(id, name, dataRepository);
    }

    @Override
    public void calculateTotal(Integer money) {
        this.getDataRepository().decrease(money);
    }

    @Override
    public Detail makeDetail() {
        System.out.print("本次" + MoneyType.COST.getDesc() + "金额：");
        int money = CommandLineUtil.readNum("请输入正确的金额");
        if (this.getDataRepository().getTotal() < money) {
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

    @Override
    public int execute() {
        return super.execute();
    }
}
