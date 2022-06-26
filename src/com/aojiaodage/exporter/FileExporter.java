package com.aojiaodage.exporter;

public interface FileExporter<T> {
    boolean support(String suffix);
    String getSupport();
    void export(T t, String path, boolean isFile) throws Exception;
}
