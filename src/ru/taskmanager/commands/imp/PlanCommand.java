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
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
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

        for (Integer order: planConfig.getOrdersList()) {
            PlanJsonConfiguration.PlanKeyValue value =  planConfig.getEntityByOrder(order);
            ExecuteOperation(value, env);
        }

        SuccessResult result = new SuccessResult();
        result.setMessage(String.format("The plan '%s' was successful", planFileName));
        return result;
    }

    private void ExecuteOperation(PlanJsonConfiguration.PlanKeyValue parameters, String env) throws CommandException {
        ConsolePrinter printer = new ConsolePrinter();
        String command = parameters.getKey();
        List<String> args = new ArrayList(parameters.getValue());
        args.add(command);

        if(!StringUtils.isNullOrEmpty(env)){
            args.add(String.format("env:%s", env));
        }

        ParamsManager manager = null;
        try {
            manager = new ParamsManager(args.toArray(new String[args.size()]));
        } catch (StringIsEmptyException e) {
            e.printStackTrace();
        } catch (CorruptedParamException e) {
            throw new CommandException(e.getMessage());
        } catch (RequiredParamException e) {
            throw new CommandException(e.getMessage());
        }

        Executor executor = new Executor(manager);
        try {
            List<CommandResult> result = executor.execute();

            String errorMessage = generateMessage(result, ErrorResult.class);
            if(!StringUtils.isNullOrEmpty(errorMessage)){
                throw new CommandException(String.format("Command %s error: %s", command, errorMessage));
            }

            printer.print(result);
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

    private String generateMessage(List<CommandResult> result, Class clazz) {
        List<CommandResult> errors = result.stream()
                .filter(i -> (clazz.isInstance(i)))
                .collect(Collectors.toList());

        String message = errors.stream()
                .map(i -> i.getMessage())
                .reduce((commandResult, commandResult2) -> commandResult += commandResult2 + ";")
                .orElse("");

        return message;
    }
}
