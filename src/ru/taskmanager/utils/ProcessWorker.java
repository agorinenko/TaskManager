package ru.taskmanager.utils;

import java.io.*;
/**
 * Created by agorinenko on 02.11.2017.
 */
public class ProcessWorker implements Runnable {
    private final InputStream is;
    private final PrintStream os;

    public ProcessWorker(InputStream is, PrintStream os){
        this.is = is;
        this.os = os;
    }

    public void run() {

        String charset = "UTF-8";

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, charset));
            bufferedReader.lines().forEach(os::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
