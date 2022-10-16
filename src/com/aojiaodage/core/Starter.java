package com.aojiaodage.core;

import com.aojiaodage.annotations.Application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Starter {
    public static final List<String> fullNames = new ArrayList<>();
    private static List<Class<?>> classes;

    public static List<Class<?>> getClasses() {
        return classes;
    }
    public static <T> void start(Class<T> clazz) {
        Application application = clazz.getAnnotation(Application.class);

        if (application == null) {
            throw new RuntimeException("找不到启动器");
        }
        String packageName = application.value();
        try {
            scanPackages(packageName);
            Starter.classes = filterClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void scanPackages(String rootPackageName) throws IOException {
        // 1、转换包路径"."为"/"格式
        String packagePrefix = rootPackageName.replace(".", File.separator);
        URL location = Starter.class.getProtectionDomain().getCodeSource().getLocation();

        if (location == null) {
            throw new RuntimeException("指定包" + rootPackageName + "不存在");
        }

        String locationPath = location.getPath();

        if (locationPath.endsWith(".jar")) {
            JarFile jarFile = new JarFile(new File(locationPath));
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (!jarEntry.isDirectory()) {
                    addFullName(name, packagePrefix);
                }
            }
            jarFile.close();
        }
        else {
            scanFullNames(new File(location.getPath()), packagePrefix);
        }
    }

    /**
     * 添加完整类名
     * @param path 路径或包目录
     * @param packagePrefix 包前缀
     */
    private static void addFullName(String path, String packagePrefix) {
        int i = path.lastIndexOf(packagePrefix);
        if (i == -1 || !path.endsWith(".class")) {
            return;
        }
        int dotIndex = path.lastIndexOf(".");
        fullNames.add(path.substring(i, dotIndex).replace(File.separator, "."));
    }

    private static List<Class<?>>  filterClasses() {
        List<Class<?>> classes = new ArrayList<>(fullNames.size());
        try {
            for (String fullName : fullNames) {
                Class<?> clazz = Class.forName(fullName);

                if (clazz.isAnnotation() || clazz.isInterface()) {
                    continue;
                }
                if (Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                classes.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void scanFullNames(File file, String packagePrefix) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    scanFullNames(f, packagePrefix);
                }
            }
        }
        else {
            String path = file.getPath();
            addFullName(path, packagePrefix);
        }
    }
}
