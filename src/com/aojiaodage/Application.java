package com.aojiaodage;

import com.aojiaodage.entity.Detail;
import com.aojiaodage.handler.impl.TextDataHandlerImpl;
import com.aojiaodage.io.Reader;
import com.aojiaodage.io.Writer;
import com.aojiaodage.repository.DataRepository;
import com.aojiaodage.service.AccountService;
import com.aojiaodage.util.CommandLineUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.*;

public class Application {
    private final Map<Integer, AccountService> serviceMap = new HashMap<>();
    private boolean exited = false;
    private DataRepository dataRepository;
    private final List<String> menus = new ArrayList<>();
    private Writer writer;

    public Writer getWriter() {
        return writer;
    }

    public void setExited(boolean exited) {
        this.exited = exited;
    }
    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    // 初始化数据
    private void init(Properties properties) {
        String dataFile = properties.getProperty("data-file");
        writer = new Writer(dataFile);
        Reader reader = new Reader(dataFile);
        List<Detail> details = reader.read(new TextDataHandlerImpl());
        dataRepository = new DataRepository(details);
        initServices(properties);
    }

    // 初始化服务
    private void initServices(Properties properties) {
        try {
            String services = properties.getProperty("services");
            String[] servicesArr = services.split(",");
            for (int i=0; i<servicesArr.length; i++) {
                String fullName = servicesArr[i];
                Class<?> clazz = Class.forName(fullName);
                Constructor<?> constructor = clazz.getConstructor(Application.class);
                AccountService service = (AccountService) constructor.newInstance(this);
                int no = i + 1;
                serviceMap.put(no, service);
                String serviceName = service.getName();
                if (serviceName == null || "".equals(serviceName)) {
                    serviceName = fullName.substring(fullName.lastIndexOf("."));
                }
                menus.add(no + ". " + serviceName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // 0、加载配置文件
        Properties properties = new Properties();
        // 通过当前类的classLoader加载配置文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("account.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 1、加载数据
        init(properties);
        boolean contained = true;
        // 2、运行服务
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
