package ca.mcmaster.se2aa4.mazerunner;

import java.util.Stack;

public class PathExecutor {
    private Stack<Undoable> undoStack = new Stack<>();
    
    public void executeCommand(Command cmd) {
        cmd.execute();
        if (cmd instanceof Undoable) {
            undoStack.push((Undoable) cmd);
        }
    }

    public void undoCommand() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().undo();
        }
    }

    public void reset() {
        undoStack = new Stack<>();
    }
}
