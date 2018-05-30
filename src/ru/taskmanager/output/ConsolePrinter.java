package ru.taskmanager.output;

import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.ErrorResult;
import ru.taskmanager.commands.SuccessResult;

import java.util.List;

public class ConsolePrinter extends Printer {
    @Override
    protected void printError(ErrorResult errorResult) {
        System.out.print(errorResult.getMessage());
        System.out.print(System.lineSeparator());
    }

    @Override
    protected void printSuccess(SuccessResult successResult) {
        System.out.print(successResult.getMessage());
        System.out.print(System.lineSeparator());
    }
}
