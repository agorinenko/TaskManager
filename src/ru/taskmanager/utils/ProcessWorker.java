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
    private final StringBuilder stringBuilder;
    private final String charsetName;

    public ProcessWorker(InputStream is, String charsetName) {
        this.is = is;
        this.stringBuilder = new StringBuilder();
        this.charsetName = charsetName;
    }

    public String getResult() {
        return this.stringBuilder.toString();
    }

    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is, this.charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        bufferedReader.lines().forEach(s -> {
            stringBuilder.append(s);
            stringBuilder.append(System.lineSeparator());
        });
    }
}
