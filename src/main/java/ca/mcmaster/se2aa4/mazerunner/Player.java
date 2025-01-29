package ca.mcmaster.se2aa4.mazerunner;

public class Player {

    // maze variables
    private static int rowPos, colPos, direction;
    private static String movementLog = "";
    private static String[] directions = {"North", "East", "South", "West"};
    private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    private static int[][] movement = {
        {-1, 0}, 
        {0, 1}, 
        {1, 0}, 
        {0, -1}
    };

    // Construct new player at entry point on west side, facing east
    public Player(int entryRow) {
        rowPos = entryRow;
        colPos = 0;
        direction = EAST; 
    }

    // get player row
    public int getRow() {
        return rowPos;
    }

    // get player column
    public int getCol() {
        return colPos;
    }

    // print player position
    public void printPos() {
        System.out.println("The player is currently at row: " + String.valueOf(rowPos) 
        + ", column: " + String.valueOf(colPos)
        + ", facing: " + directions[direction]);
    }

    // turn left
    public void turnLeft() {
        direction -= 1;
        if (direction < 0) {
            direction = WEST;
        }
    }

    // turn right
    public void turnRight() {
        direction = (direction + 1) % 4;
    }
  
    // move forward
    public void moveForward() {
        rowPos += movement[direction][0];
        colPos += movement[direction][1];
    }

    // log right movement
    public void logRight() {
        movementLog += "R";
    }

    // log left movement
    public void logLeft() {
        movementLog += "L";
    }

    // log forward movement
    public void logForward() {
        movementLog += "F";
    }

    // get movement log
    public String getCanonical() {
        return movementLog;
    }

    // factorize movement log
    public String getFactorized() {

        // Initialize variables
        String factorized = "";
        int occurence = 1;
        char lastMove = movementLog.charAt(0);

        // Search through canonical path
        for (int i = 1; i < movementLog.length(); i++) {
            // if current is equivalent to last char, increment occurences
            if (movementLog.charAt(i) == lastMove) {
                occurence += 1;
            } // if not, append to factorized path
            else {

                // ignore occurence if only 1
                if (occurence == 1) {
                    factorized += lastMove + " ";
                }
                else {
                    factorized += String.valueOf(occurence) + lastMove + " ";
                }

                // reset occurence and last move
                occurence = 1;
                lastMove = movementLog.charAt(i);
            }
        }

        // appending last moves, ignore occurence if only 1
        if (occurence == 1) {
            factorized += lastMove + " ";
        }
        else {
            factorized += String.valueOf(occurence) + lastMove + " ";
        }

        // return factorized path
        return factorized;
    }

    // guess next position by applying a forward move to current position
    public int[] predictMove() {
        int[] predict = {rowPos + movement[direction][0], 
        colPos + movement[direction][1]};

        return predict;
    }

}