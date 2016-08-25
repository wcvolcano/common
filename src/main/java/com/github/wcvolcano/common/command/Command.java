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

        Runnable normal = () -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
                String aline;
                while ((aline = reader.readLine()) != null) {
                    System.out.println(aline);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Runnable error = () -> {
            try {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "utf-8"));
                String aline=null;
                while ((aline = errorReader.readLine()) != null) {
                    System.out.println(aline);
                }
                errorReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(normal).start();
        new Thread(error).start();

        process.waitFor();
        System.out.println("process done!");
    }



}
