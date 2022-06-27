package com.aojiaodage.exporter;

import java.util.List;

public interface IExporter<T> {
    default boolean support(String suffix) {
        return getSupport().equals(suffix);
    }
    String getSupport();
    void export(T t, String path, boolean isFile) throws Exception;
    void export(List<T> list, String path, boolean isFile) throws Exception;
}
