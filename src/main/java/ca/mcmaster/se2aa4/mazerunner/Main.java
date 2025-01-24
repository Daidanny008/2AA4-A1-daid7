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
                    // Append row of maze to maze arraylist
                    if (line.length() == 0) {
                        for (int i = 0; i < maze.getWidth(); i++) {
                            line += " ";
                        }
                    }
                    maze.addRow(line);
                    
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

        // Simple algorithm, move forward until wall, turn right, repeat until exit
        int steps = 0;
        while (player.getCol() != maze.getWidth() - 1 && steps < 100) {
            if (maze.isWall(player.predictMove()[0], player.predictMove()[1]) == false) {
                player.moveForward(); // F
                steps += 1;
            }
            else {
                player.turnRight();
            }
            maze.printMaze(player.getRow(), player.getCol());
            player.printPos();
        }

        if (player.getCol() == maze.getWidth() - 1) {
            System.out.println("Maze is completed");
        }

        System.out.println("Path: " + player.getCanonical());


        /* hard code for "small.maz.txt"

        // testing turning left
        player.turnLeft();
        player.printPos();
        player.turnLeft();
        player.printPos();
        player.turnLeft();
        player.printPos();
        player.turnLeft();
        player.printPos();
        player.turnLeft();
        player.printPos();

        // testing turning right
        player.turnRight();
        player.printPos();
        player.turnRight();
        player.printPos();
        player.turnRight();
        player.printPos();
        player.turnRight();
        player.printPos();
        player.turnRight();
        player.printPos();

        // testing moving forward
        player.moveForward(); // F
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnLeft(); // L F
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnRight(); // R 2F
        player.moveForward();
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnLeft(); // L 6F
        player.moveForward();
        player.moveForward();
        player.moveForward();
        player.moveForward();
        player.moveForward();
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnRight(); // R 4F
        player.moveForward();
        player.moveForward();
        player.moveForward();
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnRight(); // R 2F
        player.moveForward();
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnLeft(); // L 2F
        player.moveForward();
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnRight(); // R 2F
        player.moveForward();
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();

        player.turnLeft(); // L F
        player.moveForward();
        maze.printMaze(player.getRow(), player.getCol());
        player.printPos();
        */

    }
}
