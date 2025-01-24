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

    // Variables
    private static int entryRow, exitRow;

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
                String line;
                while ((line = reader.readLine()) != null) {
                    // Append row of maze to maze arraylist
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

        // Instantiate player class when entry point is found, print player position
        player = new Player(8);
        player.printPos();

        // print entry and exit points
        //System.out.println("The entry on the west side is row index: " + String.valueOf(entryRow));
        //System.out.println("The exit on the east side is row index: " + String.valueOf(exitRow));
    }
}
