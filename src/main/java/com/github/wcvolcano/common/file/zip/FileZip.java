package com.github.wcvolcano.common.file.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by canwen on 2016/8/25.
 */
public class FileZip {
    public static void unGzip(File gzipFile, File outFile, boolean deleteGzipfileWhenSuccessUnzip) throws IOException {
        byte[] buffer = new byte[2048];
        GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(gzipFile));
        FileOutputStream out = new FileOutputStream(outFile);

        int len;
        while ((len = gzip.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

        gzip.close();
        out.close();

        if (deleteGzipfileWhenSuccessUnzip) {
            gzipFile.delete();
        }
    }
}
