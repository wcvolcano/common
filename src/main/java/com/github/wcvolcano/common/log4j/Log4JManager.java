package com.github.wcvolcano.common.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by wencan on 2015/8/5.
 */
public class Log4JManager {

    public static void init(String log4j2XMLFilePath) {
        try {
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4j2XMLFilePath));
            Configurator.initialize(null, source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        String filePath;
        try {
            filePath = ".";
        } catch (Exception ex) {
            filePath = Log4JManager.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        }

        try {
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(filePath + "/log4j2.xml"));
            Configurator.initialize(null, source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }

    public static Logger getLogger() {
        return LogManager.getLogger();
    }

    public static String getLoggerFile( Logger log ) {
        org.apache.logging.log4j.core.Logger loggerImpl = (org.apache.logging.log4j.core.Logger) log;
        Appender appender = loggerImpl.getAppenders().get("RollingFile");
        return ((RollingFileAppender) appender).getFileName();
    }

    public static void resetConfig() {
        org.apache.logging.log4j.core.LoggerContext ctx =
                (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        ctx.reconfigure();
    }

}
