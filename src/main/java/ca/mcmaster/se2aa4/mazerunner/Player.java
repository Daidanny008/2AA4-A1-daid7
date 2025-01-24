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
        this.rowPos = entryRow;
        this.colPos = 0;
        this.direction = this.EAST; 
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
        + ", facing: " + this.directions[this.direction]);
    }

    // turn left
    public void turnLeft() {
        this.direction -= 1;
        if (this.direction < 0) {
            this.direction = this.WEST;
        }
    }

    // turn right
    public void turnRight() {
        this.direction = (this.direction + 1) % 4;
    }
  
    // move forward
    public void moveForward() {
        this.rowPos += movement[this.direction][0];
        this.colPos += movement[this.direction][1];
    }

}