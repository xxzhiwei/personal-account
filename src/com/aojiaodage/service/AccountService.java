package com.aojiaodage.service;

public abstract class AccountService {
    private Integer id;
    private String name;

    public AccountService(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract int execute();
    public void execute(boolean contained) {
        throw new RuntimeException("请重新该方法");
    }
}
