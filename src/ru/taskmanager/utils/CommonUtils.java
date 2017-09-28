package ru.taskmanager.utils;

import ru.taskmanager.errors.PowerShellException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class CommonUtils {
    public static String executePowerShellScript(String script) throws IOException, PowerShellException {
        Path path = Paths.get(script);
        return executePowerShellScript(path);
    }
    public static String executePowerShellScript(Path path) throws IOException, PowerShellException {
        path = path.normalize();

        String command = "powershell.exe " + path.toString();
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
