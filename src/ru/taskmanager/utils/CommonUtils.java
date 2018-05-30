package ru.taskmanager.utils;

import ru.taskmanager.api.PowerShellResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.PowerShellException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class CommonUtils {
    public static PowerShellResult executePowerShellScript(String script, HashMap<String, Object> params, String charsetName) throws IOException, PowerShellException {
        Path path = Paths.get(script);
        return executePowerShellScript(path, params, charsetName);
    }

    public static PowerShellResult executePowerShellScript(Path path, HashMap<String, Object> params, String charsetName) throws IOException, PowerShellException {
        path = path.normalize();

        PowerShellResult result;
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

        Runtime runtime = Runtime.getRuntime();
        Process powerShellProcess = runtime.exec(command);
        try{
            ProcessWorker errProcessWorker = new ProcessWorker(powerShellProcess.getErrorStream(), charsetName);
            ProcessWorker outProcessWorker = new ProcessWorker(powerShellProcess.getInputStream(), charsetName);

            Thread errorProcess = new Thread(errProcessWorker);
            Thread inputProcess = new Thread(outProcessWorker);

            errorProcess.start();
            inputProcess.start();
            try {
                powerShellProcess.waitFor();
                errorProcess.join();
                inputProcess.join();

                result = new PowerShellResult(outProcessWorker.getResult(), errProcessWorker.getResult());
            } catch (InterruptedException e) {
                throw new PowerShellException("InterruptedException");
            }
        }finally {
            powerShellProcess.destroy();
        }

        return result;
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

}
