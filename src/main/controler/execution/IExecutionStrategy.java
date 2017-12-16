package main.controler.execution;

import java.util.List;

public interface IExecutionStrategy {
    void execute(List<String> comandLine);
}
