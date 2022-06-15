package com.aojiaodage.service;

import com.aojiaodage.Application;

public abstract class AccountService {
    private String name;
    protected Application application;

    public AccountService(String name, Application application) {
        this.name = name;
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void execute();
}
