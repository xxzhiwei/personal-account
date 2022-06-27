package com.aojiaodage.config;

import com.aojiaodage.importer.FileImporter;

import java.util.List;

public class AccountImporter {
    List<FileImporter> fileImporters;

    public AccountImporter(List<FileImporter> fileImporters) {
        this.fileImporters = fileImporters;
    }

    public List<FileImporter> getFileImporters() {
        return fileImporters;
    }

    public void setFileImporters(List<FileImporter> fileImporters) {
        this.fileImporters = fileImporters;
    }

    public AccountImporter() {

    }
}
