package com.aojiaodage.handler.impl;

import com.aojiaodage.annotations.Component;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.TextDataHandler;

@Component
public class TextDataHandlerImpl implements TextDataHandler {
    @Override
    public Detail handle(String data) {
        return Detail.makeFromDetailStr(data);
    }
}
