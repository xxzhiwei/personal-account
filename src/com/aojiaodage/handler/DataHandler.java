package com.aojiaodage.handler;

public interface DataHandler<T, D> {
    T handle(D data);
}
