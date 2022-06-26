package com.aojiaodage.service.impl;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.enums.FileTypes;
import com.aojiaodage.importer.FileImporter;
import com.aojiaodage.importer.impl.TextImporter;
import com.aojiaodage.service.AccountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportService extends AccountService {

    private final Map<String, FileImporter<List<Detail>>> fileImporterMap = new HashMap<>();
    private final List<String> supports;

    public ImportService() {
        fileImporterMap.put(FileTypes.TEXT.getValue(), new TextImporter());
        supports = new ArrayList<>(fileImporterMap.size());
        supports.addAll(fileImporterMap.keySet());

    }

    @Override
    public void execute() {
//        System.out.println("目前支持的格式有：" + String.join(",", supports));
//        System.out.print("\n请输入文件路径：");
//        String filepath = CommandLineUtil.readStr("请输入正确的文件路径：");
//        int suffixIdx = filepath.lastIndexOf(".");
//        if (suffixIdx == -1) {
//            System.out.println("请输入带后缀名的文件");
//            return;
//        }
//        String suffix = filepath.substring(suffixIdx + 1);
//        if (!fileImporterMap.containsKey(suffix.toLowerCase(Locale.ROOT))) {
//            System.out.println("目前暂不支持" + suffix + "格式文件的导入");
//            return;
//        }
//        try {
//            List<Detail> details = fileImporterMap.get(suffix).importFile(filepath);
//            if (details == null || details.size() == 0) {
//                return;
//            }
//            application.getDataRepository().addDetails(details);
//            application.getWriter().write(details, new BatchTextDataHandler());
//            System.out.println("导入成功");
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            System.out.println("导入失败");
//        }
    }
}
