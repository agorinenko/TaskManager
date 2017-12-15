package ru.taskmanager.output;

import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.ErrorResult;
import ru.taskmanager.commands.SuccessResult;

import java.util.List;

public abstract class Printer {

    public void print(List<CommandResult> commandResults){
        if(commandResults.size() > 0) {
            for (CommandResult result : commandResults){
                if(result instanceof ErrorResult){
                    printError((ErrorResult) result);
                } else if(result instanceof SuccessResult){
                    printSuccess((SuccessResult) result);
                }
            }
        } else {
            SuccessResult noResult =new SuccessResult();
            noResult.setMessage("No result");
            printSuccess(noResult);
        }

    }

    protected abstract void printError(ErrorResult errorResult);
    protected abstract void printSuccess(SuccessResult successResult);


}
