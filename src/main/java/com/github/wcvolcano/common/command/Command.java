package com.github.wcvolcano.common.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wencan on 2015/7/30.
 */
public class Command {
    public static void runCommand(String command) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        Process process = runtime.exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String aline=null;
        while ((aline = reader.readLine()) != null) {
            System.out.println(aline);
        }
        reader.close();
        process.waitFor();
        System.out.println("process done!");
    }
}
