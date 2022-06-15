package com.aojiaodage.service.impl;

import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.List;

public class MenuService extends AccountService {
    private List<String> items;
    private boolean contained = true;
    public MenuService(Integer id, String name) {
        super(id, name);
    }

    public MenuService(Integer id, String name, List<String> items) {
        this(id, name);
        this.items = items;
    }

    @Override
    public int execute() {
        System.out.println("\n《个人记账软件》\n");
        for (String item : items) {
            System.out.println(item);
        }
        if (contained) {
            System.out.print("请选择：");
        }
        else {
            System.out.print(CommandLineUtil.numTips);
        }
        return 0;
    }

    @Override
    public void execute(boolean contained) {
        this.contained = contained;
        execute();
    }
}
