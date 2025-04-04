package ca.mcmaster.se2aa4.mazerunner.CommandPattern;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.se2aa4.mazerunner.*;
import ca.mcmaster.se2aa4.mazerunner.ObserverPattern.*;

public class TurnLeftCommand implements Command, Undoable {
    private Player player;
    private List<CommandObserver> observers = new ArrayList<>(); 

    public TurnLeftCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.turnLeft(); // turn left
        notifyObservers('L'); // Notify observers of the turn left action
    }

    @Override
    public void undo() {
        player.turnRight(); // Undo the turn left by turning right
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
