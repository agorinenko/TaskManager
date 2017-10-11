package ru.taskmanager.utils;

import ru.taskmanager.errors.PowerShellException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class CommonUtils {
    public static String executePowerShellScript(String script, HashMap<String, Object> params) throws IOException, PowerShellException {
        Path path = Paths.get(script);
        return executePowerShellScript(path, params);
    }

    public static String executePowerShellScript(Path path, HashMap<String, Object> params) throws IOException, PowerShellException {
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

        String success;
        Process powerShellProcess = Runtime.getRuntime().exec(command);

        try{
            powerShellProcess.getOutputStream().close();

            String error = buildPowerShellResult(powerShellProcess.getErrorStream());
            if(!StringUtils.isNullOrEmpty(error)){
                throw new PowerShellException(error);
            }

            success = buildPowerShellResult(powerShellProcess.getInputStream());
            success = StringUtils.trimEnd(success, System.lineSeparator());
        }finally {
            powerShellProcess.destroy();
        }

        return success;
    }

    private static String buildScriptParams(HashMap<String, Object> params){
        if(null != params && params.size() > 0){
            StringBuilder str = new StringBuilder();
            for(Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = (String) entry.getValue();

                if(!StringUtils.isNullOrEmpty(key) && !StringUtils.isNullOrEmpty(value)){
                    str.append(String.format(" -%s %s", key, value));
                }
            }
            return str.toString();
        }
        return "";
    }

    private static String buildPowerShellResult(InputStream in) throws IOException {
        StringBuilder str = new StringBuilder();
        BufferedReader stdout = new BufferedReader(new InputStreamReader(in));
        try {
            String line;
            while ((line = stdout.readLine()) != null) {
                str.append(line);

                StringUtils.appendLineSeparator(str);
            }
        } finally {
            stdout.close();
        }

        return str.toString();
    }
}
