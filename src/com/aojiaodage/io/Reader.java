package com.aojiaodage.io;

import com.aojiaodage.handler.DataHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private final String path;

    public Reader(String path) {
        this.path = path;
    }

    public <T> List<T> read(DataHandler<T, String> handler) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return null;
        }
        List<T> list = new ArrayList<>();
        FileReader fr;
        BufferedReader br = null;
        String str;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            while ((str = br.readLine()) != null) {
                T entity = handler.handle(str);
                if (entity != null) {
                    list.add(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
