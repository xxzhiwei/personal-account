package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.service.AccountService;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class RecordService extends AccountService {
    private DataRepository dataRepository;

    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public RecordService(Integer id, String name) {
        super(id, name);
    }

    public RecordService(Integer id, String name, DataRepository dataRepository) {
        this(id, name);
        this.dataRepository = dataRepository;
    }

    public abstract Detail makeDetail();

    public abstract void calculateTotal(Integer money);

    @Override
    public int execute() {
        Detail detail = makeDetail();
        if (detail == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        detail.setDate(format);
        dataRepository.getWriter().write(detail);
        dataRepository.getDetails().add(detail);
        calculateTotal(detail.getMoney());
        return 1;
    }
}
