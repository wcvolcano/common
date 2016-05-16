package com.github.wcvolcano.common.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by wencan on 2015/5/4.
 */
public class PropertiesUtil {
    public static Properties getProperties(Class cla, String... fileName) {
        Properties properties = new Properties();
        File jarPath = new File(cla.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath = jarPath.getParentFile().getAbsolutePath();
        String file = "properties";
        if(fileName.length == 1) {
            file = fileName[0];
        }

        try {
            try {
                properties.load(new FileInputStream(propertiesPath + "/" + file));
            } catch (Exception var7) {
                try {
                    properties.load(new FileInputStream("./" + file));
                } catch (Exception ex) {
                    properties.load(new FileInputStream(file));
                }
            }
        } catch (IOException var8) {
            System.out.println("配置文件参数不正确！");
            var8.printStackTrace();
        }

        return properties;
    }
}
