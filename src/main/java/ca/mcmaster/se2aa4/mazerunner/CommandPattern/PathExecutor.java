package ca.mcmaster.se2aa4.mazerunner.CommandPattern;

import java.util.Stack;

public class PathExecutor {
    // Declare a stack to keep track of undoable commands
    private Stack<Undoable> undoStack = new Stack<>();
    
    // Call execution of commands, and push to stack if undoable
    public void executeCommand(Command cmd) {
        cmd.execute();
        if (cmd instanceof Undoable) {
            undoStack.push((Undoable) cmd);
        }
    }

    // Undo the last command
    public void undoCommand() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().undo();
        }
    }

    // reset the stack
    public void reset() {
        undoStack = new Stack<>();
    }
}
