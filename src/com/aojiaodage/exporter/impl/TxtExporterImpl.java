package com.aojiaodage.exporter.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.FileTypes;
import com.aojiaodage.exporter.FileExporter;
import com.aojiaodage.handler.impl.BatchTextDataHandler;
import com.aojiaodage.io.Writer;

import java.util.List;

public class TxtExporterImpl implements FileExporter {

    @Override
    public String getSupport() {
        return FileTypes.TEXT.getValue();
    }

    @Override
    public void export(Detail detail, String path, boolean isFile) throws Exception {

    }

    @Override
    public void export(List<Detail> list, String path, boolean isFile) throws Exception {
        Writer writer = new Writer(path);
        writer.write(list, new BatchTextDataHandler(), isFile);
    }
}
