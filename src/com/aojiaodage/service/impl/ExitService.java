package com.aojiaodage.service.impl;

import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.Locale;

public class ExitService extends AccountService {

    private final String[] commands = new String[]{"Y", "N"};

    public ExitService(Integer id, String name) {
        super(id, name);
    }

    @Override
    public int execute() {
        System.out.print("确认是否退出(Y/N)：");
        String str = CommandLineUtil.readStr("请输入正确的指令", commands).toUpperCase(Locale.ROOT);

        if ("Y".equals(str)) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
