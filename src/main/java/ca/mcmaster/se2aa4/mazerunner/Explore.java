package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.ObserverPattern.*;

public class Explore {
    // Declare sub-classes
    private Maze maze = new Maze();
    private Player player;
    private PathRecorder recorder = new PathRecorder();
    private PathValidator validator;
    private explorationAlgorithm explorationAlgorithm;

    // Settor to set the exploration strategy
    public void setAlgorithm(explorationAlgorithm explorationAlgorithm) {
        this.explorationAlgorithm = explorationAlgorithm;
    }

    // inject a maze object - for testing
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

    public boolean checkPath(String testPath) {

        // find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();
        
        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // Initialize path validator by maze and player
        validator = new PathValidator(maze, player);

        // Check if the path is valid
        return validator.checkPath(testPath);
    }
}
