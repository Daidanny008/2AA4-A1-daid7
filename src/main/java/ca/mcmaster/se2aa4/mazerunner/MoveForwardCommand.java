package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class MoveForwardCommand implements Command {
    private Player player;
    private List<CommandObserver> observers = new ArrayList<>(); 

    public MoveForwardCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveForward(); // move forward
        notifyObservers('F'); // Notify observers of the move forward action
    }

    @Override
    public void undo() { // note that undo is not called for move forward, since backward is not a valid move
        notifyObservers('U'); // Notify observers of the undo action
    }

    public void addObserver(CommandObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(char action) {
        for (CommandObserver observer : observers) {
            observer.recordAction(action);
        }
    }
}
