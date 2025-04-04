package ca.mcmaster.se2aa4.mazerunner;

// Imports
import java.util.ArrayList;

public class Maze {

    // Maze variables
    private ArrayList<String> maze;
    private int entryRow, exitRow;

    // Constructor
    public Maze() {
        // Initialize maze
        maze = new ArrayList<String>();
        this.entryRow = -1;
        this.exitRow = -1;
    }

    // Append a row of the maze, also check for entry and exit points
    public void addRow(String row) {
        // append row
        maze.add(row);
    }

    // find entry point
    public void findEntryRow() {
        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).charAt(0) == ' ') {
                entryRow = i;
                return;
            }
        }
    }

    // find exit point
    public void findExitRow() {
        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).charAt(maze.get(0).length()-1) == ' ') {
                exitRow = i;
                return;
            }
        }
    }

    // get entry point
    public int getEntryRow() {
        return entryRow;
    }

    // get exit point
    public int getExitRow() {
        return exitRow;
    }

    // get maze width
    public int getWidth() {
        return maze.get(0).length();
    }

    // get maze height
    public int getHeight() {
        return maze.size();
    }

    // check if position is wall 
    public boolean isWall(int row, int column) {
        if (row < 0 || row >= maze.size() || column < 0 || column >= maze.get(0).length()) {
            return true;
        }
        if (maze.get(row).charAt(column) == '#') {
            return true;
        } else {
            return false;
        }
    }

    // print maze stored
    public void printMaze() {
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(0).length(); j++) {
                System.out.print(maze.get(i).charAt(j));
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    // print maze stored with Player
    public void printMaze(int row, int column) {
        System.out.println("");
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(0).length(); j++) {
                if (i == row && j == column) {
                    System.out.print("P "); 
                }
                else {
                    System.out.print(maze.get(i).charAt(j) + " ");
                }
            }
            System.out.println("");
        }
    }

}