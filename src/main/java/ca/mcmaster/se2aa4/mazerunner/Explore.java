package ca.mcmaster.se2aa4.mazerunner;

public class Explore {
    // Classes
    private static Maze maze = new Maze();
    private static Player player;

    // Pass row to maze
    public void addRow(String row) {
        maze.addRow(row);
    }

    // Do right hand exploration and print factorized path
    public void RightHand() {
        // find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // Right hand exploration
        boolean moved = false;
        while (player.getCol() != maze.getWidth() - 1) {
            moved = false;

            // Check if can turn right, turn right and move forward
            if (!moved) {
                player.turnRight(); // R
                if (maze.isWall(player.predictMove()[0], player.predictMove()[1]) == false) {
                    player.moveForward(); // F
                    moved = true;
                    player.logRight();
                    player.logForward();
                }
            }
            // check if can move forward, move forward
            if (!moved) {
                player.turnLeft(); // turn back to forward direction
                if (maze.isWall(player.predictMove()[0], player.predictMove()[1]) == false) {
                    player.moveForward(); // F
                    moved = true;
                    player.logForward();
                }
            }
            // check if can move left, turn left and move forward
            if (!moved) {
                player.turnLeft(); // L
                if (maze.isWall(player.predictMove()[0], player.predictMove()[1]) == false) {
                    player.moveForward(); // F
                    moved = true;
                    player.logLeft();
                    player.logForward();
                }
            }
            // check if dead end, turn left again then move forward
            if (!moved) {
                player.turnLeft(); // LL
                if (maze.isWall(player.predictMove()[0], player.predictMove()[1]) == false) {
                    player.moveForward(); // F
                    moved = true;
                    player.logLeft();
                    player.logLeft();
                    player.logForward();
                }
            }
        }
        // print factorized path
        System.out.println(player.getFactorized());
        
    }

    // Check path
    public void checkPath(String testPath) {

        // Initialize factorized path counter
        String factorizedCounter = "1";

        // find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // Check if path is correct, for each step in path, only move forward triggers error
        for (int i = 0; i < testPath.length(); i++) {
            // move by path
            if (testPath.charAt(i) == 'F') {
                // move forward by factorized counter
                for (int j = 0; j < Integer.parseInt(factorizedCounter); j++) {
                    // move forward
                    player.moveForward();
                    // check if out of bounds or into wall
                    verifyLocation();
                }
                // reset counter
                factorizedCounter = "1";
            } else if (testPath.charAt(i) == 'R') {
                // turn right by factorized counter
                for (int j = 0; j < Integer.parseInt(factorizedCounter); j++) {
                    // turn right
                    player.turnRight();
                }
                // reset counter
                factorizedCounter = "1";
            } else if (testPath.charAt(i) == 'L') {
                // turn left by factorized counter
                for (int j = 0; j < Integer.parseInt(factorizedCounter); j++) {
                    // turn left
                    player.turnLeft();
                }
                // reset counter
                factorizedCounter = "1";
            } else {
                // if not FRL, append factor to counter
                if (factorizedCounter.equals("1")) {
                    factorizedCounter = String.valueOf(testPath.charAt(i));
                }
                else {
                    factorizedCounter += String.valueOf(testPath.charAt(i));
                }
            }
        }

        // Check if exit point is reached
        if (player.getCol() == maze.getWidth() - 1) {
            System.out.println("correct path");
        } else {
            System.out.println("incorrect path");
        }

    }

    public void verifyLocation() {
        // check if out of bounds
        if (player.getRow() < 0 || player.getRow() >= maze.getHeight() ||
            player.getCol() < 0 || player.getCol() >= maze.getWidth()) {
            System.out.println("incorrect path");
            System.exit(1);
        }
        // check if wall 
        if (maze.isWall(player.getRow(), player.getCol())) {
            System.out.println("incorrect path");
            System.exit(1);
        }
    }
}
