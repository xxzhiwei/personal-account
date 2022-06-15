package com.aojiaodage.importer.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.impl.TextDataHandlerImpl;
import com.aojiaodage.importer.FileImporter;
import com.aojiaodage.io.Reader;

import java.util.List;

public class TextImporter implements FileImporter<List<Detail>> {
    @Override
    public List<Detail> importFile(String path) {
        Reader reader = new Reader(path);
        return reader.read(new TextDataHandlerImpl());
    }
}
