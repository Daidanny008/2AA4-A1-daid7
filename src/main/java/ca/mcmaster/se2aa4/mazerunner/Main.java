package ca.mcmaster.se2aa4.mazerunner;

// Imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;
import java.util.ArrayList;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    // Classes
    private static Maze maze = new Maze();
    private static Player player;

    // Necessary variables
    private static int width = 0, height = 0;

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options options = new Options();
        options.addOption("i", true, "input file");

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("i")) {

                String inputFile = cmd.getOptionValue("i");

                logger.info("**** Reading the maze from file " + inputFile + "\n");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String line = "";
                while ((line = reader.readLine()) != null) {

                    // if first line read, initialize width of maze
                    if (height == 0) {
                        width = line.length();
                    }

                    // Append row of maze to maze arraylist
                    while (line.length() < width) {
                        line += " ";
                    }
                    maze.addRow(line);

                    // update height
                    height += 1;
                    
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            logger.info("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            logger.info("PASS ");
                        }
                    }
                    logger.info(System.lineSeparator());
                }
                reader.close();
            }
            else {
                logger.error("/!\\ An error has occured /!\\ " + "\n");
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\" + "\n");
        }
        logger.info("**** Computing path" + "\n");
        logger.info("PATH NOT COMPUTED" + "\n");
        logger.info("** End of MazeRunner" + "\n");

        // print maze
        maze.printMaze();

        // find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();
       
        // print entry and exit points
        System.out.println("The entry on the west side is row index: " + String.valueOf(maze.getEntryRow()));
        System.out.println("The exit on the east side is row index: " + String.valueOf(maze.getExitRow()));

        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // print maze map with player position and direction
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();
        
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
            
            // Print maze and player position for visual testing
            maze.printMaze(player.getRow(), player.getCol());
            player.printPos();
        }


        // print ending message
        if (player.getCol() == maze.getWidth() - 1) {
            System.out.println("Maze is completed");
        }

        // print canonical path
        System.out.println("Path: " + player.getCanonical());

        // print factorized path
        System.out.println("Path: " + player.getFactorized());

    }
}
