package com.github.wcvolcano.common.file.diff;


import com.github.wcvolcano.common.file.io.FileIOUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

/**
 * Created by wencan on 2015/6/17.
 */
public class FileDiff {
    File fileA;
    File fileB;
    int ord;
    File out;
    Comparator<String> comparator;

    public static void main(String[] args) {
        File fileA = new File(args[0]);
        File fileB = new File(args[1]);
        File out = new File(args[2]);
        int ord = Integer.parseInt(args[3]);

        new FileDiff(fileA, fileB, out, ord);
    }

    public FileDiff(File fileA, File fileB, File out, int ord) {
        this.fileA = fileA;
        this.fileB = fileB;
        this.out = out;
        this.ord = ord;

        comparator = (o1, o2) -> ord * o1.compareTo(o2);

        try {
            diff();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void diff() throws IOException {
        BufferedReader a_reader = FileIOUtil.getBufferedReader(fileA);
        BufferedReader b_reader = FileIOUtil.getBufferedReader(fileB);

        BufferedWriter writer = FileIOUtil.getBufferedWriter(out);

        long diffN = 0;

        String a_line, b_line;
        a_line = a_reader.readLine();
        b_line = b_reader.readLine();

        while (a_line != null && b_line != null) {
            int cmp = comparator.compare(a_line, b_line);

            if (cmp == 0) {
                a_line = a_reader.readLine();
                b_line = b_reader.readLine();
            }
            else if (cmp > 0) {
                writer.write("b," + b_line + "\n");
                diffN++;
                b_line = b_reader.readLine();
            }
            else {
                writer.write("a," + a_line + "\n");
                diffN++;
                a_line = a_reader.readLine();
            }
        }

        while (a_line != null) {
            writer.write("a," + a_line + "\n");
            diffN++;
            a_line = a_reader.readLine();
        }

        while (b_line != null) {
            writer.write("b," + b_line);
            diffN++;
            b_line = b_reader.readLine();
        }

        System.out.println(fileA + " and " + fileB + " different lines: " + diffN);
        if (diffN == 0) out.delete();

        writer.flush();
        writer.close();
    }


}
