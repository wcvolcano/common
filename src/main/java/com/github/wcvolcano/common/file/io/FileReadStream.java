package com.github.wcvolcano.common.file.io;

import java.io.*;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by canwen on 2017/4/28.
 */
public class FileReadStream implements Iterable<String> {
    public static Stream<String> createStream(File file) throws FileNotFoundException {
        BufferedReader reader = new LineNumberReader(new FileReader(file));
        FileReadStream iterator = new FileReadStream(reader);
        return StreamSupport.stream(iterator.spliterator(), false);
    }

    public static Stream<String> createStream(File file, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), encoding));
        FileReadStream iterator = new FileReadStream(reader);
        return StreamSupport.stream(iterator.spliterator(), false);
    }

    private final BufferedReader reader;
    private String str = null;

    private FileReadStream(BufferedReader reader) {this.reader = reader;}

    @Override
    public Iterator<String> iterator() {
        return iterator;
    }

    private Iterator<String> iterator = new Iterator<String>() {
        @Override
        public boolean hasNext() {
            try {
                str = reader.readLine();
                if (str == null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str != null;
        }

        @Override
        public String next() {
            return str;
        }
    };
}
