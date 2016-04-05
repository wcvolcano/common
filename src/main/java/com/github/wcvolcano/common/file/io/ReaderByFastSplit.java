package com.github.wcvolcano.common.file.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wencan on 2015/6/1.
 * 场景： 读入数据流（文件输入，标准输入）并通过分隔符解析每行数据
 */
public class ReaderByFastSplit {
    BufferedReader bufferedReader;
    char delimiter;
    String aline;

    public ReaderByFastSplit(InputStream inputStream, char delimiter) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.delimiter = delimiter;
        aline = bufferedReader.readLine();
        begin = 0;
        end = -1;
    }

    int begin;
    int end;

    public String next() throws IOException {
        while (!hasMoreToken()) {
            aline = bufferedReader.readLine();
            //一般此处需要有关于aline==null--> endOfFile
            //此程序应用场景不包括
        }
        return nextToken();

    }

    private String nextToken() {
        return aline.substring(begin, end);
    }

    boolean lastToken = true;
    private boolean hasMoreToken() {
        begin = end + 1;
        end = aline.indexOf(delimiter, begin);
        if(end>0 && lastToken) return true;
        if (lastToken) {
            lastToken = false;
            end = aline.length();
            return true;
        } else {
            lastToken = true;
            end = -1;
            return false;
        }

    }

    public void close() throws IOException {
        bufferedReader.close();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }


}
