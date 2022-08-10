package com.aojiaodage.service.impl;

import com.aojiaodage.annotations.Autowired;
import com.aojiaodage.annotations.Service;
import com.aojiaodage.config.AccountImporter;
import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.impl.BatchTextDataHandler;
import com.aojiaodage.importer.IImporter;
import com.aojiaodage.importer.FileImporter;
import com.aojiaodage.io.Writer;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service(order = 4, name = "导入数据")
public class ImportService extends AccountService {

    private final List<String> supports;
    private final AccountImporter accountImporter;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private Writer writer;

    public ImportService(AccountImporter accountImporter) {
        this.accountImporter = accountImporter;
        supports = new ArrayList<>(accountImporter.getFileImporters().size());
        supports.addAll(accountImporter.getFileImporters().stream().map(IImporter::getSupport).collect(Collectors.toList()));

    }

    @Override
    public void execute() {
        System.out.println("目前支持的格式有：" + String.join(",", supports));
        System.out.print("\n请输入文件所在路径：");
        String filepath = CommandLineUtil.readStr("请输入正确的路径：");
        int suffixIdx = filepath.lastIndexOf(".");
        if (suffixIdx == -1) {
            System.out.println("请输入带后缀名的文件");
            return;
        }
        String suffix = filepath.substring(suffixIdx + 1);
        FileImporter fileImporter = null;
        for (FileImporter fi : accountImporter.getFileImporters()) {
            if (fi.support(suffix.toLowerCase(Locale.ROOT))) {
                fileImporter = fi;
                break;
            }
        }
        if (fileImporter == null) {
            System.out.println("目前暂不支持" + suffix + "格式文件的导入");
            return;
        }
        try {
            List<Detail> details = fileImporter.importBatch(filepath);
            if (details == null || details.size() == 0) {
                return;
            }
            dataRepository.addDetails(details);
            writer.write(details, new BatchTextDataHandler());
            System.out.println("导入成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("导入失败");
        }
    }
}
