package com.aojiaodage.service.impl;

import com.aojiaodage.Application;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.FileTypes;
import com.aojiaodage.exporter.FileExporter;
import com.aojiaodage.exporter.impl.TxtExporter;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExportService extends AccountService {

    private final Map<String, FileExporter<List<Detail>>> fileExporterMap = new HashMap<>();
    private String[] supports;
    private String[] supportNumber;

    public ExportService(Application application) {
        this("导出数据", application);
        fileExporterMap.put(FileTypes.TEXT.getValue(), new TxtExporter());

        int idx = 0;
        supports = new String[fileExporterMap.size()];
        supportNumber = new String[fileExporterMap.size()];
        for (String type : fileExporterMap.keySet()) {
            supports[idx] = type;
            supportNumber[idx] = String.valueOf(idx + 1);
            idx++;
        }
    }

    public ExportService(String name, Application application) {
        super(name, application);
    }

    @Override
    public void execute() {
        System.out.println("目前支持的格式有：");
        for (int i = 0; i < supports.length; i++) {
            System.out.println((i + 1) + ". " + supports[i]);
        }
        System.out.print("\n请输入文件路径：");
        String filepath = CommandLineUtil.readStr("请输入正确的文件路径：");
        int suffixIdx = filepath.lastIndexOf(".");
        String suffix;
        // 输入带后缀名时的情况
        boolean isFile = false; // 若输入路径的后缀对应的导出器存在时，则当该路径为文件处理（无论该文件是否存在
        if (suffixIdx != -1) {
            suffix = filepath.substring(suffixIdx + 1);
            if (!fileExporterMap.containsKey(suffix.toLowerCase(Locale.ROOT))) {
                System.out.print("请选择导出格式：");
                String no = CommandLineUtil.readStr("请选择导出格式：", supportNumber);
                suffix = supports[Integer.parseInt(no) - 1];
            }
            else {
                isFile = true;
            }
        }
        // 不带后缀名
        else {
            System.out.print("请选择导出格式：");
            String no = CommandLineUtil.readStr("请选择导出格式：", supportNumber);
            suffix = supports[Integer.parseInt(no) - 1];
        }
        List<Detail> details = application.getDataRepository().getDetails();
        if (details == null || details.size() == 0) {
            System.out.println("无数据");
            return;
        }
        try {
            fileExporterMap.get(suffix).export(details, filepath, isFile);
            System.out.println("导出成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("导出失败");
        }
    }
}
