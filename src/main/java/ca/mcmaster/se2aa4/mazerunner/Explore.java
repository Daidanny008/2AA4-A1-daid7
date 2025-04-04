package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.ObserverPattern.*;

public class Explore {
    // Classes
    private Maze maze = new Maze();
    private Player player;
    private PathRecorder recorder = new PathRecorder();
    //private PathExecutor executor;
    private explorationAlgorithm explorationAlgorithm;

    // Settor to set the exploration strategy
    public void setAlgorithm(explorationAlgorithm explorationAlgorithm) {
        this.explorationAlgorithm = explorationAlgorithm;
    }

    // inject a maze object
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    // Pass row to maze
    public void addRow(String row) {
        maze.addRow(row);
    }

    // Do right hand exploration and print factorized path
    public void explore() {
        
        // find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();
        
        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // Initialize exploration algorithm to right hand exploration
        explorationAlgorithm = new RightHandExploration(player, maze, recorder);
        
        // Right hand exploration
        explorationAlgorithm.explore(maze, player);

        // print factorized path
        System.out.println(recorder.getFactorizedPath());
    }

    // Check path
    public boolean checkPath(String testPath) {

        // Initialize factorized path counter
        String factorizedCounter = "1";
        boolean multiple = false;

        // find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // Check if path is correct, for each step in path, only move forward triggers error
        for (int i = 0; i < testPath.length(); i++) {

            // move by path
            if (testPath.charAt(i) == 'F') {
                multiple = false;
                // move forward by factorized counter
                for (int j = 0; j < Integer.parseInt(factorizedCounter); j++) {
                    // move forward
                    player.moveForward();
                    // check if out of bounds or into wall
                    if (!verifyLocation()) {
                        return false;
                    };
                }
                //maze.printMaze(player.getRow(), player.getCol());
                // reset counter
                factorizedCounter = "1";
            } else if (testPath.charAt(i) == 'R') {
                multiple = false;
                // turn right by factorized counter
                for (int j = 0; j < Integer.parseInt(factorizedCounter); j++) {
                    // turn right
                    player.turnRight();
                }
                // reset counter
                factorizedCounter = "1";
            } else if (testPath.charAt(i) == 'L') {
                multiple = false;
                // turn left by factorized counter
                for (int j = 0; j < Integer.parseInt(factorizedCounter); j++) {
                    // turn left
                    player.turnLeft();
                }
                // reset counter
                factorizedCounter = "1";
            } else if (testPath.charAt(i) != ' ') {
                // if not FRL, append factor to counter
                if (multiple == false) {
                    factorizedCounter = String.valueOf(testPath.charAt(i));
                    multiple = true;
                }
                else {
                    factorizedCounter += String.valueOf(testPath.charAt(i));
                }
            }

        }
        
        // Check if exit point is reached
        return (player.getCol() == maze.getWidth() - 1);

    }

    public boolean verifyLocation() {
        // check if out of bounds
        if (player.getRow() < 0 || player.getRow() >= maze.getHeight() ||
            player.getCol() < 0 || player.getCol() >= maze.getWidth()) {
            return false;
        }
        // check if wall 
        if (maze.isWall(player.getRow(), player.getCol())) {
            return false;
        }

        return true;
    }
}
