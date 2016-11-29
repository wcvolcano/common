package com.github.wcvolcano.common.parameters;

import java.io.*;
import java.util.*;

/**
 * Created by wencan on 2015/5/4.
 */
public class ConfigUtil {
    /**
     * 惯用法： main() 参数的形式为 key=parameter
     * example:
     * json=json=E:\data\station_cluster\045_9036.csv
     * @param args
     * @return
     */
    public static Map<String, String> argsParse(String[] args) {
        Map<String, String> result = new HashMap<>();
        for (String arg : args) {
            String[] tags = arg.split("=");
            result.put(tags[0], tags[1]);
        }
        return result;
    }

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

    public static Map<String, String> getParameters(File configFile) throws IOException {
        return getParameters(new FileInputStream(configFile));
    }

    public static Map<String, String> getParameters(InputStream is) throws IOException {
        List<String> ignore = new ArrayList<>();
        ignore.add("//");
        ignore.add("#");
        return getParameters(is, ignore);
    }



    public static Map<String, String> getParameters(InputStream inputStream, List<String> ignoreLine) throws IOException {
        Map<String, String> result = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
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
