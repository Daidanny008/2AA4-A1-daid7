package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.ObserverPattern.*;
import ca.mcmaster.se2aa4.mazerunner.CommandPattern.*;

public class RightHandExploration implements explorationAlgorithm {

    // declare sub classes
    private Player player;
    private Maze maze;
    private PathExecutor executor; 
    private MoveForwardCommand moveForward;
    private TurnLeftCommand turnLeft;
    private TurnRightCommand turnRight;

    public RightHandExploration(Player player, Maze maze, PathRecorder recorder) {
        // Initialize subclasses passed down
        this.player = player; // Initialize the Player
        this.maze = maze; // Initialize the Maze
        this.executor = new PathExecutor(); // Initialize the PathExecutor

        // Initialize commands
        this.moveForward = new MoveForwardCommand(player); // Initialize the MoveForwardCommand
        this.turnLeft = new TurnLeftCommand(player); // Initialize the TurnLeftCommand
        this.turnRight = new TurnRightCommand(player); // Initialize the TurnRightCommand

        // attach observers to the commands
        moveForward.addObserver(recorder); // Attach the PathRecorder to the MoveForwardCommand
        turnLeft.addObserver(recorder); // Attach the PathRecorder to the TurnLeftCommand
        turnRight.addObserver(recorder); // Attach the PathRecorder to the TurnRightCommand

    }

    // Check if the player can move forward
    private boolean canMoveForward() {
        int[] nextMove = player.predictMove(); // get player pos after a forward move
        return !maze.isWall(nextMove[0], nextMove[1]); // check if the next move position is a wall
    }

    // Check if the player can turn right
    private boolean canMoveRight() {
        player.turnRight();
        boolean canMove = canMoveForward();
        player.turnLeft(); // Return to original orientation
        return canMove;
    }
    
    // Check if the player can turn left
    private boolean canMoveLeft() {
        player.turnLeft();
        boolean canMove = canMoveForward();
        player.turnRight(); // Return to original orientation
        return canMove;
    }
    
    private boolean isAtExit() {
        return player.getCol() == maze.getWidth() - 1;
    }

    @Override
    public void explore(Maze maze, Player player) {

        // Right hand exploration
        while (!isAtExit()) { // check if player is at exit
            if (canMoveRight()) {
                executor.executeCommand(turnRight); // turn right
                executor.executeCommand(moveForward); // move forward
            } else if (canMoveForward()) {
                executor.executeCommand(moveForward); // move forward
            } else if (canMoveLeft()) {
                executor.executeCommand(turnLeft); // turn left 
                executor.executeCommand(moveForward); // move forward
            } else {
                executor.executeCommand(turnLeft); // turn left 
                executor.executeCommand(turnLeft); // turn left 
                executor.executeCommand(moveForward); // move forward
            }
        }
    }
}
