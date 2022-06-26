package com.aojiaodage.config;

import com.aojiaodage.annotations.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class AccountProperties {

    private final Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public AccountProperties() {
        // 0、加载配置文件
        // 通过当前类的classLoader加载配置文件【相对于classpath根目录】
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
    }
}
