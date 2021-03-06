package com.github.wcvolcano.common.file.sort2;

import com.github.wcvolcano.common.file.io.FileIOUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

/**
 * Created by canwen on 2017/3/29.
 */
public class FileSort2 {
    private File inputDataFile;
    private File sortedTargetFile;
    private File tempMiddleResultDir;
    private Function<String, String> contentTransform = m -> m;
    private Comparator<String> comparator = String::compareTo;
    private long memoryLimit;
    private final long defaultMemory = 300; //M

    private FileSort2(File inputDataFile, File sortedTargetFile, File tempMiddleResultDir) {
        this.inputDataFile = inputDataFile;
        this.sortedTargetFile = sortedTargetFile;
        this.tempMiddleResultDir = tempMiddleResultDir;
        this.setMemoryLimit(MemoryUnit.M, defaultMemory);
        if (!tempMiddleResultDir.exists() || !tempMiddleResultDir.isDirectory()) {
            tempMiddleResultDir.mkdirs();
            tempMiddleResultDir.deleteOnExit();
        }
    }

    public static FileSort2 create(File inputDataFile) {
        return create(inputDataFile, null);
    }

    public static FileSort2 create(File inputDataFile, File sortedTargetFile) {
        return create(inputDataFile, sortedTargetFile, null);
    }

    public static FileSort2 create(File inputDataFile, File sortedTargetFile, File tempMiddleResultDir) {
        if (sortedTargetFile == null) sortedTargetFile = defaultSortedTargeFile(inputDataFile);
        if (tempMiddleResultDir == null) tempMiddleResultDir = defaultTempMiddleDir(inputDataFile);
        if (sortedTargetFile.equals(inputDataFile)) {
            throw new IllegalArgumentException("outFile can not be equals to inputFile");
        }
        return new FileSort2(inputDataFile, sortedTargetFile, tempMiddleResultDir);
    }

    private static File defaultSortedTargeFile(File inputDataFile) {
        return new File(inputDataFile + ".sort");
    }

    private static File defaultTempMiddleDir(File inputDataFile) {
        return new File(inputDataFile.getParentFile() + "/sortMiddleFile");
    }

    public FileSort2 setContentTransform(Function<String, String> transform) {
        this.contentTransform = transform;
        return this;
    }

    public FileSort2 setComparator(Comparator<String> comparator) {
        this.comparator = comparator;
        return this;
    }

    public enum MemoryUnit {
        G,
        M,
        K,
        Byte,;

        public static long bytes(MemoryUnit unit) {
            switch (unit) {
                case G:
                    return 1024 * 1024 * 1024;
                case M:
                    return 1024 * 1024;
                case K:
                    return 1024;
                case Byte:
                    return 1;
                default:
                    return 1;
            }
        }
    }

    public FileSort2 setMemoryLimit(MemoryUnit unit, long memoryLimit) {
        this.memoryLimit = memoryLimit * MemoryUnit.bytes(unit);
        return this;
    }

    public void sort() throws IOException {
        List<File> splitFiles = new FileSplit().split();
        new FileMerge().merge(splitFiles);
    }

    private class FileSplit {
        List<File> split() throws IOException {
            List<File> result = new ArrayList<>();

            BufferedReader reader = FileIOUtil.getBufferedReader(inputDataFile);
            long memory = 0;
            List<String> piece = new ArrayList<>();
            while (true) {
                String aline = reader.readLine();
                if (aline == null) {
                    if (!piece.isEmpty()) {
                        result.add(saveAndSort(piece));
                    }
                    break;
                }
                aline = contentTransform.apply(aline);
                piece.add(aline);

                memory += aline.length() * 2 + 16;
                if (memory > memoryLimit) {
                    if (!piece.isEmpty()) {
                        result.add(saveAndSort(piece));
                    }
                    memory = 0;
                    piece.clear();
                }
            }
            reader.close();

            return result;
        }

        private File saveAndSort(List<String> data) throws IOException {
            File temp = File.createTempFile("sort-", System.currentTimeMillis() + "", tempMiddleResultDir);
            temp.deleteOnExit();
            data.sort(comparator);

            BufferedWriter writer = FileIOUtil.getBufferedWriter(temp);
            for (String aline : data) {
                writer.write(aline + "\n");
            }
            writer.close();

            return temp;
        }
    }

    private class FileMerge {
        class FileWrapper {
            int order;
            File file;
            String aline;
            BufferedReader reader;

            public FileWrapper(int order, File file) throws IOException {
                this.order = order;
                this.file = file;
                this.reader = FileIOUtil.getBufferedReader(file);
                nextLine();
            }

            public void close() throws IOException {
                this.reader.close();
            }

            public String nextLine() throws IOException {
                this.aline = reader.readLine();
                return this.aline;
            }
        }

        public void merge(List<File> files) throws IOException {
            if (sortedTargetFile.exists()) sortedTargetFile.delete();
            if (files.isEmpty()) return;
            else if (files.size() == 1) {
                File file = files.get(0);
                file.renameTo(sortedTargetFile);
                return;
            }

            PriorityQueue<FileWrapper> queue = new PriorityQueue<>((o1, o2) -> {
                int cmp = comparator.compare(o1.aline, o2.aline);
                if (cmp == 0) return (o1.order - o2.order);
                return cmp;
            });

            for (int i = 0; i < files.size(); ++i) {
                queue.add(new FileWrapper(i, files.get(i)));
            }

            BufferedWriter writer = FileIOUtil.getBufferedWriter(sortedTargetFile);
            while (!queue.isEmpty()) {
                FileWrapper top = queue.poll();
                writer.write(top.aline + "\n");
                if (top.nextLine() != null) queue.add(top);
                else top.close();
            }

            writer.close();
        }
    }

}
