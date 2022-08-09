package com.aojiaodage.importer.impl;

import com.aojiaodage.annotations.Component;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.FileTypes;
import com.aojiaodage.handler.impl.TextDataHandlerImpl;
import com.aojiaodage.importer.FileImporter;
import com.aojiaodage.io.Reader;

import java.util.List;

@Component
public class TextImporter implements FileImporter {

    @Override
    public String getSupport() {
        return FileTypes.TEXT.getValue();
    }

    @Override
    public Detail importData(String path) throws Exception {
        return null;
    }

    @Override
    public List<Detail> importBatch(String path) throws Exception {
        Reader reader = new Reader(path);
        return reader.read(new TextDataHandlerImpl());
    }
}
