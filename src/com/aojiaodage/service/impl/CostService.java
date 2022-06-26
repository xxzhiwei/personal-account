package com.aojiaodage.service.impl;

import com.aojiaodage.annotations.Autowired;
import com.aojiaodage.annotations.Service;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.util.CommandLineUtil;

@Service(order = 3, name = "登记支出")
public class CostService extends RecordService {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public Detail makeDetail() {
        System.out.print("本次" + MoneyType.COST.getDesc() + "金额：");
        double money = CommandLineUtil.readDouble("请输入正确的金额：");
        if (dataRepository.getTotal() < money) {
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
