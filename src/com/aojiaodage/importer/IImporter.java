package com.aojiaodage.importer;

import java.util.List;

public interface IImporter<T> {
    default boolean support(String suffix) {
        return getSupport().equals(suffix);
    }
    String getSupport();
    T importData(String path) throws Exception;
    List<T> importBatch(String path) throws Exception;
}
