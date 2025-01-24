package ca.mcmaster.se2aa4.mazerunner;

public class Player {

    // maze variables
    private static int rowPos, colPos;
    private static String movementLog = "";
    private static enum directions {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
    private static int[][] movement = {
        {-1, 0}, 
        {0, 1}, 
        {1, 0}, 
        {0, -1}
    };
    private static directions direction;

    // Construct new player at entry point on west side, facing east
    public Player(int entryRow) {
        this.rowPos = entryRow;
        this.colPos = 0;
        this.direction = directions.EAST; 
    }

    // get player row
    public int getRow() {
        return this.rowPos;
    }

    // get player column
    public int getCol() {
        return this.colPos;
    }

    // print player position
    public void printPos() {
        System.out.println("The player is currently at row: " + String.valueOf(this.rowPos) 
        + ", column: " + String.valueOf(this.colPos)
        + ", facing: " + this.direction);
    }

    // turn left
    public void turnLeft() {
        //
    }

    // turn right
    public void turnRight() {
        //
    }
  
    // move forward
    public void moveForward() {
        //
    }

}