package ca.mcmaster.se2aa4.mazerunner;

// Imports
import java.util.ArrayList;

public class Maze {

    // Maze variables
    private static ArrayList<String> maze = new ArrayList<String>();
    private static int entryRow, exitRow;

    // Append a row of the maze
    public void addRow(String row) {
        this.maze.add(row);
    }

    // print maze stored
    public void printMaze() {
        for (int i = 0; i < this.maze.size(); i++) {
            for (int j = 0; j < this.maze.get(0).length(); j++) {
                System.out.print(this.maze.get(i).charAt(j) + " ");
            }
            System.out.println("");
        }
    }

    // find entry point
    public int findEntryRow() {
        //
        return 0;
    }

    // find exit point
    public int findExitRow() {
        //
        return 0;
    }

    // check if position is wall
    public boolean isWall(int row, int column) {
        //
        return false;
    }

}