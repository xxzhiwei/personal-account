package com.aojiaodage.handler.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.DataHandler;

import java.util.List;

public class BatchTextDataHandler implements DataHandler<String, List<Detail>> {
    @Override
    public String handle(List<Detail> data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<data.size(); i++) {
            stringBuilder.append(Detail.formatAsDetailStr(data.get(i)));
            if (i != data.size()) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
