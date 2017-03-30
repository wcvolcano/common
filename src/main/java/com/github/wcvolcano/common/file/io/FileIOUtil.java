package com.github.wcvolcano.common.file.io;

import java.io.*;

/**
 * Created by wencan on 2015/5/5.
 */
public class FileIOUtil {


    public static BufferedReader getBufferedReader(File file)
            throws IOException {
        return new LineNumberReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
    }

    public static BufferedReader getBufferedReader(String filePath)
            throws IOException {
        return new LineNumberReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
    }

    public static BufferedWriter getBufferedWriter(File file)
            throws IOException {
        writeTargetCheck(file);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
    }

    public static BufferedWriter getBufferedWriter(String filePath)
            throws IOException {
        return getBufferedWriter(new File(filePath));
    }

    public static BufferedWriter getBufferedWriterAppend(File file)
            throws IOException {
        writeTargetCheck(file);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8"));
    }

    public static BufferedWriter getBufferedWriterAppend(String filePath)
            throws IOException {
        return getBufferedWriterAppend(new File(filePath));
    }

    private static void writeTargetCheck(File writeTarget) {
        if (writeTarget.getParentFile() != null) {
            if (!writeTarget.getParentFile().isDirectory()) {
                writeTarget.getParentFile().mkdirs();
            }
        }
    }

}
