package ru.taskmanager.commands;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SafetyCommand implements Command {

    public abstract List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException;

    @Override
    public List<CommandResult> execute(List<KeyValueParam> params) {
        try {
            KeyValueParam envParam = ListUtils.getKeyValueParam(params, "env");
            String env = null;
            if(null != envParam){
                try {
                    env = envParam.getStringValue();
                } catch (StringIsEmptyException e) {
                }
            }

//            if(!StringUtils.isNullOrEmpty(env)){
//                try {
//                    //Устанавливаем значение среды, чтобы корректно брать настройки программы.
//                    // Параметры запроса уже обработанны с учетом env.json
//                    EnvironmentVariables.getInstance().setEnvironmentParameter(env);
//                } catch (ConfigurationException e) {
//                }
//            }

            return safetyExecute(params);
        } catch (CommandException ex){
            List<CommandResult> result = new ArrayList<>(1);
            result.add(new ErrorResult(ex));
            return result;
        }
    }

    protected List<CommandResult> createSingleSuccessResult(String message){
        List<CommandResult> result = new ArrayList<>(1);
        SuccessResult successResult = new SuccessResult();
        successResult.setMessage(message);
        result.add(successResult);

        return result;
    }

    protected String getStringParam(List<KeyValueParam> params, String key){
        KeyValueParam param = ListUtils.getKeyValueParam(params, key);
        String val = "";
        if(null != param){
            try {
                val = param.getStringValue();
            } catch (StringIsEmptyException e) {}
        }

        return val;
    }
}
