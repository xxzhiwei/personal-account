package com.aojiaodage.service.impl;

import com.aojiaodage.enums.Services;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;
import com.aojiaodage.Application;

import java.util.Locale;

public class ExitService extends AccountService {

    private final String[] commands = new String[]{"Y", "N"};

    public ExitService(Application application) {
        super(Services.EXIT.getDesc(), application);
    }

    @Override
    public void execute() {
        System.out.print("确认是否退出(Y/N)：");
        String str = CommandLineUtil.readStr("请输入正确的指令", commands).toUpperCase(Locale.ROOT);

        if ("Y".equals(str)) {
            application.setExited(true);
        }
    }
}
