package com.aojiaodage.config;

import com.aojiaodage.annotations.Bean;
import com.aojiaodage.annotations.Configuration;
import com.aojiaodage.exporter.FileExporter;
import com.aojiaodage.exporter.impl.TxtExporterImpl;
import com.aojiaodage.handler.impl.TextDataHandlerImpl;
import com.aojiaodage.importer.FileImporter;
import com.aojiaodage.importer.impl.TextImporter;
import com.aojiaodage.io.Reader;
import com.aojiaodage.io.Writer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AccountConfig {

    @Bean
    public Writer writer(AccountProperties accountProperties) {
        return new Writer(accountProperties.getProperties().getProperty("data-file"));
    }

    @Bean
    public Reader reader(AccountProperties accountProperties) {
        return new Reader(accountProperties.getProperties().getProperty("data-file"));
    }

    @Bean
    public AccountExporter accountExporter() {
        AccountExporter accountExporter = new AccountExporter();
        List<FileExporter> fileExporters = new ArrayList<>();
        fileExporters.add(new TxtExporterImpl());
        accountExporter.setFileExporters(fileExporters);
        return accountExporter;
    }

    @Bean
    public AccountImporter accountImporter() {
        AccountImporter accountImporter = new AccountImporter();
        List<FileImporter> fileImporters = new ArrayList<>();
        fileImporters.add(new TextImporter());
        accountImporter.setFileImporters(fileImporters);
        return accountImporter;
    }
}
