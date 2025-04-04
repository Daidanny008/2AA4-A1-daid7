package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class TurnLeftCommand implements Command {
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

    public void addObserver(CommandObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(char action) {
        for (CommandObserver observer : observers) {
            observer.recordAction(action);
        }
    }
}
