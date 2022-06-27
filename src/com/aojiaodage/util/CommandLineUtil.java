package com.aojiaodage.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class CommandLineUtil {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String strTips = "请输入有效的字符：";
    public static final String numTips = "请输入有效的数字：";

    public static String readStr() {
        return readStr(null);
    }

    public static String readStr(String tips) {
        if (tips == null) {
            tips = strTips;
        }
        String str = "";
        while (scanner.hasNext()) {
            str = scanner.nextLine().trim();
            if ("".equals(str)) {
                System.out.println(tips);
                continue;
            }
            break;
        }
        return str;
    }

    public static String readStr(String tips, String[] included) {
        String s;
        for (;;) {
            s = readStr(tips);
            for (String item : included) {
                if (item.equals(s.toUpperCase(Locale.ROOT))) {
                    return s;
                }
            }
            System.out.print(tips);
        }
    }

    public static int readInt() {
        return readInt(null);
    }

    public static int readInt(String tips) {
        if (tips == null) {
            tips = numTips;
        }
        int n;
        for (;;) {
            String str = readStr(tips);
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.println(tips);
            }
        }
        return n;
    }

    public static double readDouble(String tips) {
        if (tips == null) {
            tips = numTips;
        }
        double d;
        for (;;) {
            String str = readStr(tips);
            try {
                d = Double.parseDouble(str);
                break;
            } catch (NumberFormatException e) {
                System.out.println(tips);
            }
        }
        return d;
    }
}
