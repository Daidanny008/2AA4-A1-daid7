package ca.mcmaster.se2aa4.mazerunner;

import java.util.Stack;

public class PathExecutor {
    private Stack<Command> commandHistory = new Stack<>();

    public void executeCommand(Command cmd) {
        cmd.execute();
        commandHistory.push(cmd); // Optional: Store for undo/redo
    }

    public void reset() {
        commandHistory = new Stack<>();
    }
}
