package com.aojiaodage.service.impl;

import com.aojiaodage.annotations.Autowired;
import com.aojiaodage.annotations.Service;
import com.aojiaodage.config.AccountExporter;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.exporter.FileExporter;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.List;
import java.util.Locale;

@Service(order = 5, name = "导出数据")
public class ExportService extends AccountService {

    private final String[] supports;
    private final AccountExporter accountExporter;

    @Autowired
    private DataRepository dataRepository;

    public ExportService(AccountExporter accountExporter) {
        this.accountExporter = accountExporter;
        supports = new String[accountExporter.getFileExporters().size()];
        for (int i=0; i<accountExporter.getFileExporters().size(); i++) {
            FileExporter fileExporter = accountExporter.getFileExporters().get(i);
            supports[i] = fileExporter.getSupport();
        }
    }

    @Override
    public void execute() {
        System.out.println("目前支持的格式有：");
        for (int i = 0; i < supports.length; i++) {
            System.out.println((i + 1) + ". " + supports[i]);
        }
        System.out.print("\n请输入导出的路径：");
        String filepath = CommandLineUtil.readStr("请输入正确的路径：");
        int suffixIdx = filepath.lastIndexOf(".");
        String suffix;
        // 输入带后缀名时的情况
        boolean isFile = false; // 若输入路径的后缀对应的导出器存在时，则当该路径为文件处理（无论该文件是否存在
        FileExporter fileExporter = null;
        if (suffixIdx != -1) {
            suffix = filepath.substring(suffixIdx + 1);
            for (FileExporter fe : accountExporter.getFileExporters()) {
                if (fe.support(suffix.toLowerCase(Locale.ROOT))) {
                    fileExporter = fe;
                    break;
                }
            }
            // 如果不支持导出「某后缀的文件，就当做文件夹处理」
        }
        if (fileExporter == null) {
            System.out.print("请选择导出格式：");
            String noStr = CommandLineUtil.readStr("请选择导出格式：");
            int no = Integer.parseInt(noStr) - 1;
            if (no > accountExporter.getFileExporters().size()) {
                System.out.println("操作错误");
                return;
            }
            fileExporter = accountExporter.getFileExporters().get(no);
        }
        else {
            isFile = true;
        }
        List<Detail> details = dataRepository.getDetails();
        if (details == null || details.size() == 0) {
            System.out.println("无数据");
            return;
        }
        try {
            fileExporter.export(details, filepath, isFile);
            System.out.println("导出成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("导出失败");
        }
    }
}
