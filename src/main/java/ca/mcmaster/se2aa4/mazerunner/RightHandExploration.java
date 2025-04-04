package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.ObserverPattern.*;
import ca.mcmaster.se2aa4.mazerunner.CommandPattern.*;

public class RightHandExploration implements explorationAlgorithm {

    // declare s
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
    private boolean canMove() {
        int[] nextMove = player.predictMove(); // get player pos after a forward move
        return !maze.isWall(nextMove[0], nextMove[1]); // check if the next move position is a wall
    }

    @Override
    public void explore(Maze maze, Player player) {

        // Right hand exploration
        boolean moved = false;
        while (player.getCol() != maze.getWidth() - 1) { // check if player is at exit
            moved = false;

            // Check if can turn right, turn right and move forward
            if (!moved) {
                executor.executeCommand(turnRight); // turn right
                if (canMove()) {
                    // logs RF
                    executor.executeCommand(moveForward); // move forward
                    moved = true;
                }
            }
            // check if can move forward, move forward
            if (!moved) {
                executor.executeCommand(turnLeft); // turn left back to forward direction
                if (canMove()) {
                    // logs F = RL -> '' -> F
                    for (int i = 0; i < 2; i++) { // undo twice
                        executor.undoCommand(); // undo
                    }
                    executor.executeCommand(moveForward); // move forward
                    moved = true;
                }
            }
            // check if can move left, turn left and move forward
            if (!moved) {
                executor.executeCommand(turnLeft); // turn left to left direction
                if (canMove()) {
                    // logs LF = RLL -> '' -> LF
                    for (int i = 0; i < 3; i++) { // undo 3 times
                        executor.undoCommand(); // undo
                    }
                    executor.executeCommand(turnLeft); // redo turn left
                    executor.executeCommand(moveForward); // move forward

                    moved = true;
                }
            }
            // check if dead end, turn left again then move forward
            if (!moved) {
                executor.executeCommand(turnLeft); // turn left to backward direction
                if (canMove()) {
                    // logs LLF = RLLL -> '' -> LLF
                    for (int i = 0; i < 4; i++) { // undo 4 times
                        executor.undoCommand(); // undo
                    }
                    executor.executeCommand(turnLeft); // redo turn left twice
                    executor.executeCommand(turnLeft); 
                    executor.executeCommand(moveForward); // move forward

                    moved = true;
                }
            }
        }
    }
}
