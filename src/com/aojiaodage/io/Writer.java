package com.aojiaodage.io;

import com.aojiaodage.handler.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private final String path;
    public Writer(String path) {
        this.path = path;
    }

    public <T> void write(T t, DataHandler<String, T> handler) {
        File file = new File(path);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (!parentFile.exists()) {
                    boolean created = parentFile.mkdirs();
                    if (!created) {
                        throw new RuntimeException("创建" + parentFile.getPath() + "失败");
                    }
                }
            }
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new RuntimeException("创建" + file.getPath() + "失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            bw.write(handler.handle(t));
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
