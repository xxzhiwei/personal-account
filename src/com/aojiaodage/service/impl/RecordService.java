package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.TextDataFormatter;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.Application;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class RecordService extends AccountService {

    private TextDataFormatter<Detail> textDataFormatter;

    public RecordService(String name, Application application) {
        super(name, application);
    }

    public abstract Detail makeDetail();

    @Override
    public void execute() {
        Detail detail = makeDetail();
        if (detail == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        detail.setDate(format);
        if (textDataFormatter == null) {
            textDataFormatter = Detail::formatAsDetailStr;
        }
        application.getWriter().write(detail, textDataFormatter);
        application.getDataRepository().addDetail(detail);
    }
}
