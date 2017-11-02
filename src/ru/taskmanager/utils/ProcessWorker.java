package ru.taskmanager.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

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
        try {
            int c;
            while ((c = is.read()) != -1)
                os.print((char) c);
        } catch (IOException x) {
            // Handle error
        }
    }
}
