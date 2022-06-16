package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.Importers;
import com.aojiaodage.importer.FileImporter;
import com.aojiaodage.importer.impl.TextImporter;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;
import com.aojiaodage.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ImportService extends AccountService {

    private final Map<String, FileImporter<List<Detail>>> fileImporterMap = new HashMap<>();

    public ImportService(Application application) {
        this("导入数据", application);
        fileImporterMap.put(Importers.TEXT.getValue(), new TextImporter());
    }
    public ImportService(String name, Application application) {
        super(name, application);
    }

    @Override
    public void execute() {
        System.out.print("请输入文件路径：");
        String filepath = CommandLineUtil.readStr("请输入正确的文件路径：");
        int suffixIdx = filepath.lastIndexOf(".");
        if (suffixIdx == -1) {
            System.out.println("请输入带后缀名的文件");
            return;
        }
        String suffix = filepath.substring(suffixIdx + 1);
        if (!fileImporterMap.containsKey(suffix.toLowerCase(Locale.ROOT))) {
            System.out.println("目前暂不支持" + suffix + "格式文件的导入");
            return;
        }
        List<Detail> details = fileImporterMap.get(suffix).importFile(filepath);
        if (details == null || details.size() == 0) {
            return;
        }
        application.getDataRepository().addDetails(details);
        application.getWriter().write(details, data -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<data.size(); i++) {
                stringBuilder.append(Detail.formatAsDetailStr(data.get(i)));
                if (i != data.size()) {
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        });
        System.out.println("导入成功！");
    }
}
