package ru.taskmanager.commands.imp;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionComparator;
import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.PowerShellException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.CommonUtils;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class PowerShellCommand extends SafetyCommand {

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();

        HashMap<String, Object> psParams = ListUtils.convertToHashMap(params);


        LocalVersionManager manager = new LocalVersionManager();

        KeyValueParam out = ListUtils.getKeyValueParam(params, "out");
        if(null != out){
            String outValue = null;
            try {
                outValue = out.getStringValue();
                if(!StringUtils.isNullOrEmpty(outValue)){
                    manager.setBaseDir(outValue);
                }
            } catch (StringIsEmptyException e) {}
        }

        KeyValueParam env = ListUtils.getKeyValueParam(params, "env");
        if(null != env){
            String envValue = null;
            try {
                envValue = env.getStringValue();
                if(!StringUtils.isNullOrEmpty(envValue)){
                    extendPowerShellParametersByEnv(psParams, envValue);
                }
            } catch (StringIsEmptyException e) {}
            catch (FileNotFoundException e) {}
        }

        KeyValueParam versionParam = ListUtils.getKeyValueParam(params, "v");
        String v = null != versionParam ? versionParam.getDefaultOrStringValue("") : "";
        boolean versionIsMissing = StringUtils.isNullOrEmpty(v) ? true : false;

        StringBuilder sb = new StringBuilder();
        try {
            List<Version> localVersions = manager.select(".ps1");
            localVersions.sort(new VersionComparator());
            for (int i = 0; i < localVersions.size(); i++) {
                String psResult;
                LocalVersion localVersion = (LocalVersion) localVersions.get(i);

                Path path = localVersion.getLocalPath();
                sb.append("Execute script:" + path);
                StringUtils.appendLineSeparator(sb);

                if(versionIsMissing || localVersion.getVersionTimestampString().equalsIgnoreCase(v)) {
                    try {
                        psResult = CommonUtils.executePowerShellScript(path, psParams);
                    } catch (PowerShellException e) {
                        psResult = e.getMessage();
                    }
                } else {
                    psResult = "MISSED";
                }

                sb.append(psResult);
                StringUtils.appendLineSeparator(sb);
            }
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        String resultMessage = sb.toString();
        result.setMessage(resultMessage);
        return result;
    }

    private void extendPowerShellParametersByEnv(HashMap<String, Object> psParams, String env) throws FileNotFoundException {
        Path path = Paths.get("env.json");
        if(Files.exists(path)){

            JsonReader jsonReader = Json.createReader(new FileInputStream(path.toFile().getAbsoluteFile()));
            try{
                JsonObject json = jsonReader.readObject();
                JsonObject envObject = json.getJsonObject(env);

                Set<Map.Entry<String, JsonValue>> set = envObject.entrySet();

                for(Map.Entry<String, JsonValue> entry : set) {
                    String key = entry.getKey();
                    JsonValue value = entry.getValue();


                    if(!StringUtils.isNullOrEmpty(key) && null != value){
                        Object o = detectValue(value);
                        psParams.put(key, o);
                    }
                }
            }finally {
                jsonReader.close();
            }
        }
    }

    private Object detectValue(JsonValue value){
        JsonValue.ValueType type = value.getValueType();

        if(type == JsonValue.ValueType.NULL){
            return null;
        } else if(type == JsonValue.ValueType.ARRAY){
            throw new NotImplementedException();
        } else if(type == JsonValue.ValueType.OBJECT){
            throw new NotImplementedException();
        }else if(type == JsonValue.ValueType.STRING){
            return ((JsonString)value).getString();
        }else if(type == JsonValue.ValueType.NUMBER){
            throw new NotImplementedException();
        }else if(type == JsonValue.ValueType.TRUE || type == JsonValue.ValueType.FALSE){
            return Boolean.valueOf(value.toString());
        }

        throw new NotImplementedException();
    }



}
