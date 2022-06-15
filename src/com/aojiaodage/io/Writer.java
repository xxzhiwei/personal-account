package com.aojiaodage.io;

import com.aojiaodage.entity.Detail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public void write(Detail detail) {
        File file = new File("data.txt");
        try {
            if (!file.exists()) {
                boolean result = file.createNewFile();
                if (!result) {
                    throw new RuntimeException("创建data.txt失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(Detail.formatAsDetailStr(detail));
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
