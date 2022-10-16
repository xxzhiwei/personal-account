package com.aojiaodage.core;

import com.aojiaodage.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationApplicationContext implements BeanFactory {

    private final Map<String, Object> beanMap = new HashMap<>(); // 在构造器中初始化组件
    private final Map<String, String> beanTypeNameToIdMap = new HashMap<>();

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }

    public List<Object> getBeans(Class<?> clazz) {
        Collection<Object> values = beanMap.values();
        List<Object> beans = new ArrayList<>();
        for (Object bean : values) {
            if (clazz.isAssignableFrom(bean.getClass())) {
                beans.add(bean);
            }
        }
        return beans;
    }

//    public <T> List<T> getBeans(Class<T> clazz) {
//        Collection<Object> values = beanMap.values();
//        List<T> beans = new ArrayList<>();
//
//        for (Object bean : values) {
//            if (clazz.isAssignableFrom(bean.getClass())) {
//                beans.add((T) bean);
//            }
//        }
//        return beans;
//    }

    public AnnotationApplicationContext(Class<?> clazz) {
        Starter.start(clazz);
        try {
            beanInit(Starter.getClasses());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // bean初始化
    private void beanInit(List<Class<?>> classes) throws Exception {
        List<Class<?>> classesWithConfiguration = classes.stream()
                .filter(clz -> clz.getAnnotation(Configuration.class) != null)
                .collect(Collectors.toList());
        createBenByConfiguration(classesWithConfiguration);
        classes.removeAll(classesWithConfiguration);
        for (Class<?> clazz : classes) {
            createBean(clazz);
        }
    }

    private void createBenByConfiguration(List<Class<?>> classes) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        for (Class<?> clazz : classes) {
            if (getBean(clazz) == null) {
                createBean(clazz);
            }
        }
    }

    private Object createBean(Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Annotation annotation;
        if ((annotation = clazz.getAnnotation(Component.class)) == null && (annotation = clazz.getAnnotation(Configuration.class)) == null && (annotation = clazz.getAnnotation(Service.class)) == null) {
            return null;
        }
        String beanId = null;
        boolean isConfigurationAnnotation = false;
        if (annotation instanceof Component) {
            beanId = ((Component) annotation).value();
        }
        if (annotation instanceof Configuration) {
            isConfigurationAnnotation = true;
            beanId = ((Configuration) annotation).value();
        }
        if (annotation instanceof Service) {
            beanId = ((Service) annotation).value();
        }

        String typeName = clazz.getTypeName();
        if ("".equals(beanId)) {
            String subTypeName = typeName.substring(typeName.lastIndexOf(".") + 1);
            beanId = getTypeNameWithLowercaseFirstLetter(subTypeName);
        }
        if (beanMap.containsKey(beanId)) {
            return beanMap.get(beanId);
        }
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        if (declaredConstructors.length > 1) {
            throw new RuntimeException("只能存在一个构造器");
        }
        Constructor<?> constructor = declaredConstructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] args = createArgs(parameterTypes);
        Object instance = constructor.newInstance(args);

        // 加载@Bean
        if (isConfigurationAnnotation) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Bean beanAnnotation = method.getAnnotation(Bean.class);
                if (beanAnnotation == null) {
                    continue;
                }

                String subBeanId = beanAnnotation.value();
                if ("".equals(subBeanId)) {
                    subBeanId = method.getName();
                }

                Class<?>[] methodParameterTypes = method.getParameterTypes();
                Object[] methodArgs = createArgs(methodParameterTypes);
                Object subInstance = method.invoke(instance, methodArgs);
                Class<?> methodReturnType = method.getReturnType();

                beanMap.put(subBeanId, subInstance);
                beanTypeNameToIdMap.put(methodReturnType.getTypeName(), subBeanId);
            }
        }
        // 注入属性@Autowired
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired == null) {
                continue;
            }
            Object bean = getBeanOrElseCreate(field.getType());
            field.setAccessible(true);
            field.set(instance, bean);
        }
        beanMap.put(beanId, instance);
        beanTypeNameToIdMap.put(typeName, beanId);
        return instance;
    }

    // 根据class从组件容器中获取组件，如果没有则尝试创建（符合条件时创建
    private Object getBeanOrElseCreate(Class<?> clazz) {
        Object bean = getBean(clazz);
        if (bean == null) {
            try {
                bean = createBean(clazz);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("创建Bean失败");
            }
        }
        return bean;
    }

    private Object getBean(Class<?> clazz) {
        String typeName = clazz.getTypeName();
        Object bean = getBeanByTypeName(typeName);
        if (bean == null) {
            for (Object beanInMap : beanMap.values()) {
                // 判断组件是否为clazz的子类
                if (clazz.isAssignableFrom(beanInMap.getClass())) {
                    bean = beanInMap;
                    break;
                }
            }
        }
        return bean;
    }
    private Object getBeanByTypeName(String typeName) {

        if (beanTypeNameToIdMap.containsKey(typeName)) {
            String beanId = beanTypeNameToIdMap.get(typeName);
            return beanMap.get(beanId);
        }

        return null;
    }

    private String getTypeNameWithLowercaseFirstLetter(String typeName) {
        char[] chars = typeName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    // 创建参数列表
    private Object[] createArgs(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i=0; i<parameterTypes.length; i++) {
            Class<?> paramClazz = parameterTypes[i];
            Object bean = getBeanOrElseCreate(paramClazz);
            args[i] = bean;
        }
        return args;
    }
}
