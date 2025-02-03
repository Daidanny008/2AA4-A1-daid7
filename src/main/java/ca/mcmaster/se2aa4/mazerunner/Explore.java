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
        System.out.println("Path: " + player.getFactorized());
        
    }

    // Check path
    public void checkPath(String testPath) {
        
    }
}
