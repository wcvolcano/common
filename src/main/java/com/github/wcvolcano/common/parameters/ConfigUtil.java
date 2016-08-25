package com.github.wcvolcano.common.parameters;

import com.github.wcvolcano.common.file.io.FileIOUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by wencan on 2015/5/4.
 */
public class ConfigUtil {
    public static Properties getProperties(Class cla, String... fileName) {
        Properties properties = new Properties();
        File jarPath = new File(cla.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath = jarPath.getParentFile().getAbsolutePath();
        String file = "parameters";
        if (fileName.length == 1) {
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

    public static Map<String, String> getParameters(String configFile) throws IOException {
        List<String> ignore = new ArrayList<>();
        ignore.add("//");
        ignore.add("#");
        return getParameters(configFile, ignore);
    }

    public static Map<String, String> getParameters(String configFile, List<String> ignoreLine) throws IOException {
        Map<String, String> result = new HashMap<>();

        BufferedReader reader = FileIOUtil.getBufferedReader(configFile);
        String aline;
        while ((aline = reader.readLine()) != null) {
            aline = aline.trim();
            boolean isIgnored = false;
            if(aline.matches("^$")) isIgnored = true;
            for (String h : ignoreLine) {
                if (aline.startsWith(h)) {
                    isIgnored = true;
                    break;
                }
            }

            if (!isIgnored) {
                String[] tag = aline.split("=");
                if (tag.length == 2) {
                    result.put(tag[0], tag[1]);
                }
            }
        }

        return result;
    }
}
