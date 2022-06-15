package com.aojiaodage.handler.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.TextDataHandler;

public class TextDataHandlerImpl implements TextDataHandler<Detail> {
    @Override
    public Detail handle(String data) {
        return Detail.makeFromDetailStr(data);
    }
}
