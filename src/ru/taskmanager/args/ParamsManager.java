package ru.taskmanager.args;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ParamsManager {
    private List<CommandParam> commandParams;
    private List<KeyValueParam> keyValueParams;
    private List<String> requiredKeys;
    private List<String> keys;
    private ParamsFactory factory;

    public ParamsManager(String[] args) throws StringIsEmptyException, CorruptedParamException, RequiredParamException {
        factory = new ParamsFactory();
        commandParams = new ArrayList<>();
        keyValueParams = new ArrayList<>();
        keys = new ArrayList<>();

        for (String arg: args) {
            appendParam(factory, arg);
        }

        if(envPresent()) {
            mergeKeyValueParamsWithEnvParams();
        }
    }

    private void appendParam(ParamsFactory factory, String arg) throws StringIsEmptyException, CorruptedParamException {
        if(!StringUtils.isNullOrEmpty(arg)){
            BaseParam param = factory.create(arg);
            if(param instanceof CommandParam){
                commandParams.add((CommandParam) param);
            } else if(param instanceof KeyValueParam) {
                keyValueParams.add((KeyValueParam) param);
            } else{
                throw new NotImplementedException();
            }

            keys.add(param.getKey());
        }
    }
    /**
     * Установка ключей обязательных параметров
     * @param keys ключи параметров
     */
    public void setRequiredParams(String[] keys){
        this.requiredKeys = Arrays.asList(keys);
    }
    /**
     * Получение списка всех ключ-значение параметров
     * @return список параметров
     */
    public List<KeyValueParam> getKeyValueParams() throws RequiredParamException {
        raiseRequiredParamExceptionIfNeed();

        return keyValueParams;
    }

    public KeyValueParam getKeyValueParam(String key) throws RequiredParamException {
        if(keyExist(key)){
            List<KeyValueParam> ps = getKeyValueParams();
            return ListUtils.getKeyValueParam(ps, key);
        }

        return null;
    }

    /**
     * Получение списка всех параметров команд
     * @return список параметров
     */
    public List<CommandParam> getCommandParams() throws RequiredParamException {
        raiseRequiredParamExceptionIfNeed();
        return commandParams;
    }

    /**
     * Получение списка всех ключей
     * @return - список всех ключей
     */
    public List<String> getKeys(){
        return keys;
    }

    /**
     * Проверка существования ключа в коллекциях параметров
     * @param key - ключ
     * @return - ключ существует
     */
    public boolean keyExist(String key){
        return keys.stream().filter(k -> k.equalsIgnoreCase(key)).findFirst().isPresent();
    }

    public boolean envPresent(){
        return keyExist("env");
    }

    private void raiseRequiredParamExceptionIfNeed() throws RequiredParamException {
        if(null == requiredKeys || null == keys){
            return;
        }

        if(!requiredKeys.stream().allMatch(this::keyExist)){
            throw new RequiredParamException();
        }
    }

    private void mergeKeyValueParamsWithEnvParams() throws RequiredParamException {
        KeyValueParam env = getKeyValueParam("env");
        if(null != env){
            String envValue;
            try {
                envValue = env.getStringValue();
                if(!StringUtils.isNullOrEmpty(envValue)){
                    extendParametersByEnv(envValue);
                }
            } catch (StringIsEmptyException e) {}
            catch (FileNotFoundException e) {}
            catch (CorruptedParamException e) {}
        }
    }

    private void extendParametersByEnv(String env) throws FileNotFoundException, RequiredParamException, StringIsEmptyException, CorruptedParamException {
        Path path = Paths.get("env.json");
        if (Files.exists(path)) {
            JsonReader jsonReader = Json.createReader(new FileInputStream(path.toFile().getAbsoluteFile()));
            try {
                JsonObject json = jsonReader.readObject();
                JsonObject envObject = json.getJsonObject(env);

                Set<Map.Entry<String, JsonValue>> set = envObject.entrySet();

                for (Map.Entry<String, JsonValue> entry : set) {
                    Map<String, String> map = new HashMap<>();

                    String key = entry.getKey();
                    if (!StringUtils.isNullOrEmpty(key) && !keyExist(key)) {
                        JsonValue value = entry.getValue();
                        Object realValue = detectValue(value);
                        if (null != realValue) {
                            String stringValue = realValue.toString();
                            keyValueParams.add(factory.createKeyValueParam(key, stringValue));
                            keys.add(key);
                        }
                    }
                }
            } finally {
                jsonReader.close();
            }
        }
    }

    private Object detectValue(JsonValue value){
        if(null == value){
            return null;
        }

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
            boolean isIntegral = ((JsonNumber)value).isIntegral();
            if(isIntegral){
                return ((JsonNumber)value).longValue();
            }

            return ((JsonNumber)value).doubleValue();
        }else if(type == JsonValue.ValueType.TRUE || type == JsonValue.ValueType.FALSE){
            return Boolean.valueOf(value.toString());
        }

        throw new NotImplementedException();
    }
}
