package com.aojiaodage;

import com.aojiaodage.annotations.*;
import com.aojiaodage.core.Starter;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Application("com.aojiaodage")
public class AccountApplication {
    private final Map<Integer, AccountService> serviceMap = new HashMap<>();
    private static boolean exited = false;
    private final List<String> menus = new ArrayList<>();
    private final Map<String, Object> components = new HashMap<>(); // 在构造器中初始化组件
    private final Map<String, String> componentTypeNames = new HashMap<>();

    private Object getComponentOrElseCreate(Class<?> clazz) {
        String typeName = clazz.getTypeName();
        Object component = getComponent(typeName);
        if (component == null) {
            for (Object comp : components.values()) {
                if (clazz.isAssignableFrom(comp.getClass())) {
                    component = comp;
                    break;
                }
            }
        }
        if (component == null) {
            try {
                component = createComponent(clazz);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("创建组件失败");
            }
        }
        return component;
    }
    private Object getComponent(String typeName) {
        Object component = null;

        if (componentTypeNames.containsKey(typeName)) {
            String componentId = componentTypeNames.get(typeName);
            component = components.get(componentId);
        }
        return component;
    }

    private String getTypeNameWithLowercaseFirstLetter(String typeName) {
        char[] chars = typeName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static void setExited(boolean exited) {
        AccountApplication.exited = exited;
    }

    public static void main(String[] args) {
        Starter.start(AccountApplication.class);
    }

    public AccountApplication() {
        run();
    }

    private Object[] createArgs(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i=0; i<parameterTypes.length; i++) {
            Class<?> paramClazz = parameterTypes[i];
            Object component = getComponentOrElseCreate(paramClazz);
            args[i] = component;
        }
        return args;
    }

    private Object createComponent(Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Annotation annotation;
        if ((annotation = clazz.getAnnotation(Component.class)) == null && (annotation = clazz.getAnnotation(Configuration.class)) == null && (annotation = clazz.getAnnotation(Service.class)) == null) {
            return null;
        }
        String componentId = null;
        if (annotation instanceof Component) {
            componentId = ((Component) annotation).value();
        }
        if (annotation instanceof Configuration) {
            componentId = ((Configuration) annotation).value();
        }
        if (annotation instanceof Service) {
            componentId = ((Service) annotation).value();
        }

        String typeName = clazz.getTypeName();
        if ("".equals(componentId)) {
            String subTypeName = typeName.substring(typeName.lastIndexOf(".") + 1);
            componentId = getTypeNameWithLowercaseFirstLetter(subTypeName);
        }
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        if (declaredConstructors.length > 1) {
            throw new RuntimeException("只能存在一个构造器");
        }
        Constructor<?> constructor = declaredConstructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] args = createArgs(parameterTypes);
        Object instance = constructor.newInstance(args);

        Configuration configuration;
        if (annotation instanceof Configuration) {
            configuration = (Configuration) annotation;
        }
        else {
            configuration = clazz.getAnnotation(Configuration.class);
        }
        if (configuration != null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Bean beanAnnotation = method.getAnnotation(Bean.class);
                if (beanAnnotation == null) {
                    continue;
                }

                String beanId = beanAnnotation.value();
                if ("".equals(beanId)) {
                    beanId = method.getName();
                }
                Class<?>[] methodParameterTypes = method.getParameterTypes();
                Object[] methodArgs = createArgs(methodParameterTypes);
                Object bean = method.invoke(instance, methodArgs);
                Class<?> methodReturnType = method.getReturnType();
                components.put(beanId, bean);
                componentTypeNames.put(methodReturnType.getTypeName(), beanId);
            }
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired == null) {
                continue;
            }
            Object component = getComponentOrElseCreate(field.getType());
            field.setAccessible(true);
            field.set(instance, component);
        }
        components.put(componentId, instance);
        componentTypeNames.put(typeName, componentId);
        return instance;
    }

    private void componentInit() {
        try {
            List<Class<?>> classesWithConfiguration = Starter.classes.stream().filter(clz -> clz.getAnnotation(Configuration.class) != null).collect(Collectors.toList());
            for (Class<?> clazz : classesWithConfiguration) {
                createComponent(clazz);
            }
            // 注册组件
            for (Class<?> clazz : Starter.classes) {
                if (classesWithConfiguration.contains(clazz)) {
                    continue;
                }
                createComponent(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 初始化数据
    private void init() {
        try {
            componentInit();
            serviceInit();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("初始化失败");
        }
    }

    // 初始化服务
    private void serviceInit() {
        Collection<Object> values = components.values();
        for (Object obj : values) {
            if (obj instanceof AccountService) {
                Service annotation = obj.getClass().getAnnotation(Service.class);
                if (annotation == null) {
                    return;
                }
                String name = annotation.name();
                int order = annotation.order();

                while (serviceMap.containsKey(order)) {
                    order += 1;
                }
                serviceMap.put(order, (AccountService) obj);
                if ("".equals(name)) {
                    name = componentTypeNames.get(obj.getClass().getTypeName());
                }
                menus.add(order + ". " + name);
            }
        }
    }

    public void run() {
        // 0、加载配置文件
        // 1、加载数据
        init();
        boolean contained = true;
        // 2、运行服务【exited改为静态属性
        while (!exited) {
            if (!contained) {
                System.out.println("请输入正确的服务序号");
            }

            System.out.println("\n《个人记账软件》\n");

            for (String item : menus) {
                System.out.println(item);
            }
            System.out.print("请选择：");
            int no = CommandLineUtil.readInt();
            contained = serviceMap.containsKey(no);
            if (!contained) {
                continue;
            }
            serviceMap.get(no).execute();
        }
    }
}
