package ru.taskmanager.utils;

import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.output.ConsolePrinter;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by agorinenko on 02.11.2017.
 */
public class ProcessWorker implements Runnable {
    private final InputStream is;
    private final PrintStream os;
    private final AtomicInteger integer;

    public ProcessWorker(InputStream is, PrintStream os){
        this.is = is;
        this.os = os;
        this.integer = new AtomicInteger();
    }

    public int getLineCount(){
        return integer.get();
    }

    public void run() {

        String charset = "UTF-8";

        try {
            integer.set(0);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, charset));
            bufferedReader.lines().forEach(s -> {
                integer.incrementAndGet();
                os.println(s);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
