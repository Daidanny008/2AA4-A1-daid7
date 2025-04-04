package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class TurnRightCommand implements Command, Undoable {
    private Player player;
    private List<CommandObserver> observers = new ArrayList<>(); 

    public TurnRightCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.turnRight(); // turn rifht
        notifyObservers('R'); // Notify observers of the turn right action
    }

    @Override
    public void undo() {
        player.turnLeft(); // Undo the turn right by turning left
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
