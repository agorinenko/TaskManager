package ru.taskmanager.commands.imp;

import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.*;
import ru.taskmanager.config.PlanJsonConfiguration;
import ru.taskmanager.errors.*;
import ru.taskmanager.output.ConsolePrinter;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PlanCommand extends SafetyCommand {
    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam envParam = ListUtils.getKeyValueParam(params, "env");

        String env = null;
        if(null != envParam){
            try {
                env = envParam.getStringValue();
            } catch (StringIsEmptyException e) {
            }
        }

        KeyValueParam planFileNameParam = ListUtils.getKeyValueParam(params, "f");
        String planFileName = null;
        if(null != planFileNameParam){
            try {
                planFileName = planFileNameParam.getStringValue();
            } catch (StringIsEmptyException e) {
            }
        }
        if(StringUtils.isNullOrEmpty(planFileName)){
            planFileName = "plan";
        }

        if(!planFileName.endsWith(".json")){
            planFileName = String.format("%s.json", planFileName);
        }

        Path path = Paths.get(planFileName);
        PlanJsonConfiguration planConfig;
        try {
            planConfig = new PlanJsonConfiguration(path);
            planConfig.load();
        } catch (ConfigurationException e) {
            throw new CommandException(String.format("Plan file %s invalid: %s", planFileName, e.getMessage()));
        }

        List<CommandResult> result = new ArrayList<>();
        for (Integer order: planConfig.getOrdersList()) {
            PlanJsonConfiguration.PlanKeyValue value =  planConfig.getEntityByOrder(order);
            List<CommandResult> commandResult = executeOperation(params, value, env);
            result.addAll(commandResult);
        }

        return result;
    }

    private List<CommandResult> executeOperation(List<KeyValueParam> params, PlanJsonConfiguration.PlanKeyValue parameters, String env) throws CommandException {

        String command = parameters.getKey();
        List<String> planParams = sanitizePlanParams(params, parameters.getValue());
        List<String> args = new ArrayList(planParams);
        args.add(command);

        if(!StringUtils.isNullOrEmpty(env)){
            args.add(String.format("env:%s", env));
        }

        ParamsManager manager;
        try {
            manager = new ParamsManager(args.toArray(new String[args.size()]));
        } catch (StringIsEmptyException e) {
            throw new CommandException(e.getMessage());
        } catch (CorruptedParamException e) {
            throw new CommandException(e.getMessage());
        } catch (RequiredParamException e) {
            throw new CommandException(e.getMessage());
        }catch (UniqueParamException e) {
            throw new CommandException(e.getMessage());
        }

        Executor executor = new Executor(manager);
        try {
            List<CommandResult> result = executor.execute();

//            String errorMessage = generateMessage(result, ErrorResult.class);
//            if(!StringUtils.isNullOrEmpty(errorMessage)){
//                throw new CommandException(String.format("Command %s error: %s", command, errorMessage));
//            }
//            ConsolePrinter printer = new ConsolePrinter();
//            printer.print(result);

            return result;
        } catch (RequiredParamException e) {
            throw new CommandException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new CommandException(e.getMessage());
        } catch (InstantiationException e) {
            throw new CommandException(e.getMessage());
        } catch (StringIsEmptyException e) {
            throw new CommandException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new CommandException(e.getMessage());
        } catch (ConfigurationException e) {
            throw new CommandException(e.getMessage());
        }
    }

    private List<String> sanitizePlanParams(List<KeyValueParam> params, List<String> planParams){
//        String separator = "->";
//        String separator2 = ":";
        return planParams.stream().map(i-> {
            for (KeyValueParam param : params) {
                String key = null;
                try {
                    key = param.getKey();
                } catch (StringIsEmptyException e) {
                }

                if(!StringUtils.isNullOrEmpty(key)){
                    key = String.format("{%s}", key);
                }
                if(i.contains(key)){
                    String value = null;
                    try {
                        value = param.getStringValue();
                    } catch (StringIsEmptyException e) {
                    }
                    if(!StringUtils.isNullOrEmpty(value)) {
                        return i.replace(key, value);
                    }
                }
            }
            return i;
//            if(i.contains(separator)){
//                String[] parts = i.split(separator);
//                String rawData = parts[1];
//                if(rawData.contains(separator2)){
//                    String paramName = rawData.split(separator2)[0];
//                    String newValue = getStringParam(params, parts[0]);
//
//                    return String.format("%s:%s", paramName, newValue);
//                }
//            }
        }).collect(Collectors.toList());
    }

//    private String generateMessage(List<CommandResult> result, Class clazz) {
//        List<CommandResult> errors = result.stream()
//                .filter(i -> (clazz.isInstance(i)))
//                .collect(Collectors.toList());
//
//        String message = errors.stream()
//                .map(i -> i.getMessage())
//                .reduce((commandResult, commandResult2) -> commandResult += commandResult2 + ";")
//                .orElse("");
//
//        return message;
//    }
}
