package com.aojiaodage.exporter.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.exporter.FileExporter;
import com.aojiaodage.handler.impl.BatchTextDataHandler;
import com.aojiaodage.io.Writer;

import java.util.List;

public class TxtExporter implements FileExporter<List<Detail>> {
    @Override
    public void export(List<Detail> details, String path, boolean isFile) throws Exception {
        Writer writer = new Writer(path);
        writer.write(details, new BatchTextDataHandler(), isFile);
    }
}
