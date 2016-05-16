package com.github.wcvolcano.common.file.sort.sorter;


import com.github.wcvolcano.common.file.io.FileIOUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by wencan on 2015/6/16.
 */
class FileMerger {
    private List<File> fileList;
    private File outFile;
    private Comparator<String> comparator;

    private PriorityQueue<FileQueue> priorityQueue = new PriorityQueue<>();

    public FileMerger(List<File> fileList, File outFile, Comparator<String> comparator) {
        if (fileList.size() == 1) {
            if(outFile.exists()) outFile.delete();
            fileList.get(0).renameTo(outFile);
            return;
        }
        this.fileList = fileList;
        this.outFile = outFile;
        this.comparator = comparator;

        try {
            genQueue();
            merge();
            end();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void end() {
        try {
            fileList.forEach(File::delete);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void genQueue() throws IOException {
        int size = fileList.size();
        for (int index = 0; index < size; index++) {
            BufferedReader reader = FileIOUtil.getBufferedReader(fileList.get(index));
            priorityQueue.add(new FileQueue(reader, index, comparator));
        }
    }

    private void merge() throws IOException {
        BufferedWriter writer = FileIOUtil.getBufferedWriter(outFile);
        FileQueue fileQueue;
        long estimateLine = estimateLines();
        long currentLine = 0;

        while (!priorityQueue.isEmpty()) {
            fileQueue = priorityQueue.poll();
            writer.write(fileQueue.pop()+"\n");
            if (++currentLine == estimateLine) {
                System.gc();
                currentLine=0;
            }
            if (fileQueue.isEmpty()) {
                fileQueue.close();
                System.gc();
            } else priorityQueue.add(fileQueue);
        }
        writer.close();
        try {
            for (FileQueue f : priorityQueue) {
                f.close();
            }
        } catch (Exception ex) {
            //
        }
    }

    private long estimateLines() {
        long max = 1000000;
        long maxMemory = 200 * 1024 * 1024;
        long temp = maxMemory / (priorityQueue.peek().string.length() * 2 + 16);
        if(temp>max) return max;
        else return temp;
    }


    private class FileQueue implements Comparable<FileQueue>{
        BufferedReader reader;
        final int index;
        String string ="";
        Comparator<String> comparator;

        public FileQueue(BufferedReader reader, int index, Comparator<String> comparator) throws IOException {
            this.reader = reader;
            this.index = index;
            this.comparator = comparator;
            pop();
        }

        public boolean isEmpty() {return string ==null;}


        public String pop() throws IOException {
            String temp = string;
            string = reader.readLine();
            return temp;
        }

        @Override
        public int compareTo(FileQueue that) {
            int cmp = comparator.compare(this.string, that.string);
            if(cmp!=0) return cmp;
            return this.index-that.index;
        }

        public void close() throws IOException {
            reader.close();
        }
    }
}
