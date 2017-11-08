package ru.taskmanager.utils;

import ru.taskmanager.errors.PowerShellException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class CommonUtils {
    public static void executePowerShellScript(String script, HashMap<String, Object> params) throws IOException, PowerShellException {
        Path path = Paths.get(script);
        executePowerShellScript(path, params);
    }

    public static void executePowerShellScript(Path path, HashMap<String, Object> params) throws IOException, PowerShellException {
        path = path.normalize();

        String filePath;
        String command;
        String paramsString = buildScriptParams(params);
        if(path.isAbsolute()){
            filePath = path.toString();
            command = "powershell.exe " + filePath + paramsString;
        }else{
            filePath = "./" + path.toString();
            command = "powershell.exe -File " + filePath + paramsString;
        }

        //String success;
        Runtime runtime = Runtime.getRuntime();
        Process powerShellProcess = runtime.exec(command);
        try{
            Thread errorProcess = new Thread(new ProcessWorker(powerShellProcess.getErrorStream(), System.err));
            Thread inputProcess = new Thread(new ProcessWorker(powerShellProcess.getInputStream(), System.out));

            errorProcess.start();
            inputProcess.start();

            try {
                powerShellProcess.waitFor();
                errorProcess.join();
                inputProcess.join();
            } catch (InterruptedException e) {
                throw new PowerShellException("InterruptedException");
            }
        }finally {
            powerShellProcess.destroy();
        }

//        ProcessWorker worker = new ProcessWorker(powerShellProcess);
//        worker.start();
//        try {
//            worker.join(10000);
//            if (null == worker.getExit()) {
//                throw new PowerShellException("Timeout exception 10sec");
//            }
//        } catch(InterruptedException ex) {
//            worker.interrupt();
//            Thread.currentThread().interrupt();
//            throw new PowerShellException("InterruptedException");
//        } finally {
//            powerShellProcess.destroy();
//        }

       // powerShellProcess.getOutputStream().flush();
        //powerShellProcess.getOutputStream().close();
//        try {
//            powerShellProcess.waitFor();
//        } catch (InterruptedException e) {
//            throw new PowerShellException("Wait error");
//        }

//        try{
//            String error="";
//            InputStream errorStream = powerShellProcess.getErrorStream();
//            try{
//                error = buildPowerShellResult(errorStream);
//            }finally {
//                errorStream.close();
//            }
//            if(!StringUtils.isNullOrEmpty(error)){
//                throw new PowerShellException(error);
//            }
//
//            InputStream inputStream = powerShellProcess.getInputStream();
//            try{
//                success = buildPowerShellResult(inputStream);
//            }finally {
//                inputStream.close();
//            }
//
//            success = StringUtils.trimEnd(success, System.lineSeparator());
//
//        }finally {
//            powerShellProcess.destroy();
//        }

        //success = "Success";
        //return success;
    }

    private static String buildScriptParams(HashMap<String, Object> params){
        if(null != params && params.size() > 0){
            StringBuilder str = new StringBuilder();
            for(Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if(!StringUtils.isNullOrEmpty(key)){
                    if(value instanceof String || value instanceof Integer){
                        value = String.format("\"%s\"", value);
                        str.append(String.format(" -%s %s", key, value));
                    } else if(value instanceof Boolean){
                        if((Boolean)value) {
                            str.append(String.format(" -%s", key));
                        }
                    }
                }
            }

            return str.toString();
        }
        return "";
    }

//    private static String buildPowerShellResult(InputStream in) throws IOException {
//
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < in.available(); i++) {
//            Reader r = new InputStreamReader(in);
//            char[] buffer = new char[1024];
//            while (r.read(buffer) > 0){
//                str.append(buffer, 0, buffer.length);
//            }
//        }
//
//        return str.toString();
//    }
//    private static String buildPowerShellResult(InputStream in) throws IOException {
//        int i = in.available();
//        if(0==i){
//            return "";
//        }
//        StringBuilder str = new StringBuilder();
//        BufferedReader stdout = new BufferedReader(new InputStreamReader(in));
//        try {
//            String line;
//            while ((line = stdout.readLine()) != null) {
//                str.append(line);
//
//                StringUtils.appendLineSeparator(str);
//            }
//        } finally {
//            stdout.close();
//        }
//
//        return str.toString();
//    }
}
