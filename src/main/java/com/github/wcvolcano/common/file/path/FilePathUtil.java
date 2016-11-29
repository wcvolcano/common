package com.github.wcvolcano.common.file.path;

import java.io.File;

/**
 * Created by canwen on 2016/9/9.
 */
public class FilePathUtil {
    public static boolean ensureDir(String dir) {
        File dirF = new File(dir);
        if (!dirF.exists() || dirF.isDirectory()) {
            return dirF.mkdirs();
        }
        else {
            return true;
        }
    }

    public static boolean ensureFileParantDir(File file) {
        return ensureDir(file.getParent());
    }
}
