package com.github.wcvolcano.common.file.zip;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by canwen on 2016/8/25.
 */
public class FileZip {
    public static void unGzip(File gzipFile, File outFile, boolean deleteGzipFileWhenSuccessUnzip) throws IOException {
        byte[] buffer = new byte[2048];
        GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(gzipFile));
        FileOutputStream out = new FileOutputStream(outFile);

        int len;
        while ((len = gzip.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

        gzip.close();
        out.close();

        if (deleteGzipFileWhenSuccessUnzip) {
            gzipFile.delete();
        }
    }

    public static void gzip(File sourceFile, File outFile, boolean deleteSourceFile) throws IOException {
        GZIPOutputStream zipOut = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)));
        gzipAppend(sourceFile, zipOut, deleteSourceFile);
        zipOut.close();
    }

    public static void gzipAppend(File sourceFile, GZIPOutputStream gzipOut, boolean deleteSourceFile) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
        byte[] buffer = new byte[2048];

        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            gzipOut.write(buffer, 0, len);
        }
        inputStream.close();

        if(deleteSourceFile) sourceFile.delete();
    }
}
