package com.aojiaodage.service.impl;

import com.aojiaodage.AccountApplication;
import com.aojiaodage.annotations.Service;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.Locale;

@Service(order = 6, name = "退出")
public class ExitService extends AccountService {

    private final String[] commands = new String[]{"Y", "N"};

    @Override
    public void execute() {
        System.out.print("确认是否退出(Y/N)：");
        String str = CommandLineUtil.readStr("请输入正确的指令", commands).toUpperCase(Locale.ROOT);

        if ("Y".equals(str)) {
            AccountApplication.setExited(true);
        }
    }
}
