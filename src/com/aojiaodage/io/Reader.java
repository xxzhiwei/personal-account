package com.aojiaodage.io;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.TextDataHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private final String path;

    public Reader(String path) {
        this.path = path;
    }

    public List<Detail> read(TextDataHandler handler) throws Exception {
        File file = new File(path);
        List<Detail> list = new ArrayList<>();
        if (!file.exists()) {
            return list;
        }
        BufferedReader br = null;
        InputStreamReader isr;
        String str;
        try {
            isr = new FileReader(file);
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                Detail entity = handler.handle(str);
                if (entity != null) {
                    list.add(entity);
                }
            }
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
