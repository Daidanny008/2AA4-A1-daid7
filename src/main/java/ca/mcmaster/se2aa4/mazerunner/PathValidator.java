package ca.mcmaster.se2aa4.mazerunner;

public class PathValidator {

    // declare maze and player
    private final Maze maze;
    private final Player player;

    public PathValidator(Maze maze, Player player) {
        // create maze and player
        this.maze = maze;
        this.player = player;
    }

    public boolean checkPath(String testPath) {
        // Initialize factorized path counter
        String factorizedCounter = "1";
        boolean multiple = false;

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
                    if (maze.isWall(player.getRow(), player.getCol())) {
                        return false;
                    };
                }
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
                // if not FRL or space, append factor to counter
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
}