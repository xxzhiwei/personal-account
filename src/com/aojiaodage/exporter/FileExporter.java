package com.aojiaodage.exporter;

public interface FileExporter<T> {
    void export(T t, String path, boolean isFile) throws Exception;
}
