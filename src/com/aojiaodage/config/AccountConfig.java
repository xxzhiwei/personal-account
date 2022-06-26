package com.aojiaodage.config;

import com.aojiaodage.annotations.Bean;
import com.aojiaodage.annotations.Configuration;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.FileTypes;
import com.aojiaodage.exporter.FileExporter;
import com.aojiaodage.exporter.impl.TxtExporter;
import com.aojiaodage.io.Reader;
import com.aojiaodage.io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, FileExporter<?>> fileExporterMap() {
        Map<String, FileExporter<?>> map = new HashMap<>();
        map.put(FileTypes.TEXT.getValue(), new TxtExporter());
        return map;
    }
}
