package com.aojiaodage.importer;

public interface FileImporter<T> {
    T importFile(String path);
}
