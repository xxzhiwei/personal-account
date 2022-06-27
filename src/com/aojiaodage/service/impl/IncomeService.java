package com.aojiaodage.service.impl;

import com.aojiaodage.annotations.Autowired;
import com.aojiaodage.annotations.Service;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.MoneyType;
import com.aojiaodage.io.Writer;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.util.CommandLineUtil;

@Service(order = 2, name = "登记收入")
public class IncomeService extends RecordService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private Writer writer;

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

    @Override
    public Writer getWriter() {
        return writer;
    }

    @Override
    public DataRepository getDataRepository() {
        return dataRepository;
    }
}
