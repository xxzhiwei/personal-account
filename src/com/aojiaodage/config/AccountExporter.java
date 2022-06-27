package com.aojiaodage.config;

import com.aojiaodage.exporter.FileExporter;

import java.util.List;

public class AccountExporter {
    List<FileExporter> fileExporters;
    public AccountExporter() {}
    public List<FileExporter> getFileExporters() {
        return fileExporters;
    }

    public void setFileExporters(List<FileExporter> fileExporters) {
        this.fileExporters = fileExporters;
    }

    public AccountExporter(List<FileExporter> fileExporters) {
        this.fileExporters = fileExporters;
    }
}
