package com.aojiaodage;

import com.aojiaodage.annotations.Application;
import com.aojiaodage.annotations.Service;
import com.aojiaodage.core.BeanFactory;
import com.aojiaodage.core.ClassPathAnnotationApplicationContext;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.util.*;
import java.util.stream.Collectors;

@Application("com.aojiaodage")
public class AccountApplication {
    private final Map<Integer, AccountService> serviceMap = new HashMap<>();
    private static boolean exited = false;

    BeanFactory context;

    public static void setExited(boolean exited) {
        AccountApplication.exited = exited;
    }

    public static void main(String[] args) {
        AccountApplication application = new AccountApplication();
        application.run();
    }

    public AccountApplication() {
        this.context = new ClassPathAnnotationApplicationContext(AccountApplication.class);
    }

    // 初始化数据
    private void init() {
        try {
            serviceInit();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("初始化失败");
        }
    }

    // 初始化服务
    private void serviceInit() {
        List<Object> beans = ((ClassPathAnnotationApplicationContext) context).getBeans(AccountService.class);

        for (Object bean : beans) {
            Service annotation = bean.getClass().getAnnotation(Service.class);
            if (annotation == null) {
                return;
            }
            String name = annotation.name();
            int order = annotation.order();

            while (serviceMap.containsKey(order)) {
                order += 1;
            }
            if ("".equals(name)) {

                String typeName = bean.getClass().getTypeName();
                name = typeName.substring(typeName.lastIndexOf(".") + 1);
            }
            AccountService service = (AccountService) bean;
            service.setName(name);
            serviceMap.put(order, service);
        }
    }

    public void run() {
        // 0、加载配置文件
        // 1、加载数据
        init();
        boolean contained = true;
        Set<Integer> keys = serviceMap.keySet().stream().sorted((Integer::compareTo)).collect(Collectors.toCollection(LinkedHashSet::new));
        List<String> menus = new ArrayList<>(keys.size());
        for (Integer key : keys) {
            menus.add(key + ". " + serviceMap.get(key).getName());
        }
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
