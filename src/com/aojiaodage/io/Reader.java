package com.aojiaodage.io;

import com.aojiaodage.entity.Detail;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public List<Detail> read() {
        File file = new File("data.txt");
        List<Detail> details = new ArrayList<>();
        if (!file.exists()) {
            return details;
        }
        FileReader fr;
        BufferedReader br = null;
        String str;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            while ((str = br.readLine()) != null) {
                Detail detail = Detail.makeFromDetailStr(str);
                details.add(detail);
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
        return details;
    }

    public static void main(String[] args) {
        Reader reader = new Reader();
    }
}
