package com.github.wcvolcano.common.file.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by canwen on 2017/4/28.
 */
public class FileReadStreamTest {
    @Test
    public void test() throws IOException {
        File file = new File("pom.xml");
        BufferedReader reader = FileIOUtil.getBufferedReader(file);
        List<String> list = new ArrayList<>();
        while (true) {
            String aline = reader.readLine();
            if (aline == null) break;
            list.add(aline);
        }
        reader.close();

        List<String> stream = FileReadStream.createStream(file).collect(Collectors.toList());
        Assert.assertEquals(list, stream);
    }
}
