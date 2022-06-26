package com.aojiaodage.core;

import com.aojiaodage.annotations.Application;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static final List<String> fullNames = new ArrayList<>();
    public static List<Class<?>> classes;
    public static <T> void start(Class<T> clazz) {
        Application application = clazz.getAnnotation(Application.class);

        if (application == null) {
            return;
        }
        String packageName = application.value();
        String fullNameField = application.fullNameField();
        try {
            scanPackages(packageName);
            Starter.classes = filterClasses();
            //Field declaredField = clazz.getDeclaredField(fullNameField);
            Constructor<T> constructor = clazz.getConstructor();
            //declaredField.setAccessible(true);
            //declaredField.set(instance, classes);
            T instance = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void scanPackages(String rootPackageName) {
        // 1、转换包路径"."为"/"格式
        String path = rootPackageName.replace(".", File.separator);
        URL resource = Starter.class.getClassLoader().getResource(path);
        if (resource == null) {
            throw new RuntimeException("指定包" + rootPackageName + "不存在");
        }
        scanFullNames(new File(resource.getPath()), path);
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

    public static void scanFullNames(File file, String packagePath) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    scanFullNames(f, packagePath);
                }
            }
        }
        else {
            String path = file.getPath();
            int i = path.lastIndexOf(packagePath);
            int dotIndex = path.lastIndexOf(".");
            fullNames.add(path.substring(i, dotIndex).replace(File.separator, "."));
        }
    }
}
