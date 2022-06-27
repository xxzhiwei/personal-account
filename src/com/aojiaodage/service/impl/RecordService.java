package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.TextDataFormatter;
import com.aojiaodage.io.Writer;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.service.AccountService;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class RecordService extends AccountService {

    private TextDataFormatter<Detail> textDataFormatter;

    public abstract Detail makeDetail();
    public abstract Writer getWriter();
    public abstract DataRepository getDataRepository();

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
        try {
            getWriter().write(detail, textDataFormatter);
            getDataRepository().addDetail(detail);
            System.out.println("登记成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("登记失败");
        }
    }
}
