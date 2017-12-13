package main.controler.execution;

import java.util.List;

public interface ExecutionStrategy {
    public void execute(List<String> comandLine);
}
