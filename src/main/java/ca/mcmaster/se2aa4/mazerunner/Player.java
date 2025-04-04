package ca.mcmaster.se2aa4.mazerunner;

public class Player {

    // maze variables
    private int rowPos, colPos, direction; // initialize player position and direction
    //private static String movementLog = "";
    private String[] directions = {"North", "East", "South", "West"}; // for printing
    private int[][] movement = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // movement vector for NESW
    private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3; // final constants for directions

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
    
    // move forward
    public void moveForward() {
        rowPos += movement[direction][0];
        colPos += movement[direction][1];
    }

    // turn left
    public void turnLeft() {
        direction = (direction - 1 + 4) % 4; // decrement direction -> CCW is left, added 4 to avoid negative
    }
    
    // turn right
    public void turnRight() {
        direction = (direction + 1) % 4; // increment direction -> CW is right
    }
    
    // guess next position by applying a forward move to current position
    public int[] predictMove() {
        int[] predict = {rowPos + movement[direction][0], colPos + movement[direction][1]};
        return predict;
    }
    
    // print player position
    public void printPos() {
        System.out.println("The player is currently at row: " + String.valueOf(rowPos) 
        + ", column: " + String.valueOf(colPos)
        + ", facing: " + directions[direction]);
    }
    
}