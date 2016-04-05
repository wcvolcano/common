package com.github.wcvolcano.common.file.sort.sorter;


import com.github.wcvolcano.common.file.io.FileIOUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wencan on 2015/6/16.
 */
class FileSpliter {
    //metadata
    private File dataFile;
    private BufferedReader dataReader;
    private String tempDir;
    private Comparator<String> comparator;
    private long blockSize;
    private List<File> fileList = new ArrayList<>();


    public FileSpliter(File dataFile, String tempDir, Comparator<String> comparator)
            throws FileNotFoundException, UnsupportedEncodingException {
        this.dataFile = dataFile;
        this.tempDir = tempDir;
        this.comparator = comparator;
        dataReader =
                FileIOUtil.getBufferedReader(dataFile);
        blockSize = sizeOfBlock();
        makeTempDir();
    }

    public FileSpliter(BufferedReader dataReader, String tempDir, Comparator<String> comparator) {
        this.dataReader = dataReader;
        this.tempDir = tempDir;
        this.comparator = comparator;
        blockSize = sizeOfBlock();
        makeTempDir();
    }

    public List<File> fileSplit() throws IOException {
        List<String> tempList = new ArrayList<>();
        long currentSize=0;
        String aline="";

        while (aline != null) {
            while (currentSize < blockSize && (aline = dataReader.readLine()) != null) {
                tempList.add(aline);
                currentSize += estimatedSizeOf(aline);
            }
            fileList.add(sortAndSave(tempList));
            tempList.clear();
            currentSize = 0;
            System.gc();
        }

        return fileList;
    }

    private File sortAndSave(List<String> tempList) throws IOException {
        File outTemp = File.createTempFile("fileSort", null, tempDir ==null?null:new File(tempDir));
        outTemp.deleteOnExit();
        BufferedWriter writer = FileIOUtil.getBufferedWriter(outTemp);

        Collections.sort(tempList, comparator);

        for (String s : tempList) {
            writer.write(s + "\n");
        }
        writer.close();
        return outTemp;
    }

    private long estimatedSizeOf(String s) {
        return s.length()*2 + 16;
    }



    private long sizeOfBlock() {
        System.gc();
        long temp = Runtime.getRuntime().freeMemory()/2;
        long min = 100 * 1024 * 1024;
        long max = 3 * min;
        if (temp < min) {
            return min;
        } else if (temp > max) {
            return max;
        } else {
            return temp;
        }
    }

    private void makeTempDir() {
        File dir = new File(tempDir);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
    }

}
