package ru.taskmanager.args;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.args.params.KeyValueParam;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ParamsManager {
    private List<CommandParam> commandParams;
    private List<KeyValueParam> keyValueParams;
    private List<String> requiredKeys;
    private List<String> keys;

    public ParamsManager(String[] args) throws StringIsEmptyException, CorruptedParamException {
        ParamsFactory factory = new ParamsFactory();

        commandParams = new ArrayList<>();
        keyValueParams = new ArrayList<>();
        keys = new ArrayList<>();

        for (String arg: args) {
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

    private void raiseRequiredParamExceptionIfNeed() throws RequiredParamException {
        if(null == requiredKeys || null == keys){
            return;
        }

        if(!requiredKeys.stream().allMatch(this::keyExist)){
            throw new RequiredParamException();
        }
    }
}
