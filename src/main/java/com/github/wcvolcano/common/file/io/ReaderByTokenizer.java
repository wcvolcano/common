package com.github.wcvolcano.common.file.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by wencan on 2015/5/31.
 */
public class ReaderByTokenizer {
    BufferedReader reader;
    StringTokenizer tokenizer;
    String delimiter;

    public ReaderByTokenizer(InputStream inputStream, String delimiter) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        tokenizer = new StringTokenizer("");
        this.delimiter = delimiter;
    }

    public String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine(),delimiter);
        }
        return tokenizer.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public void close() throws IOException {
        reader.close();
    }


}
