package ca.mcmaster.se2aa4.mazerunner;

// Imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    /* Program structure:
     * Main: class
     *  - Explore: class
     *      - Maze: class
     *      - Player: class
     *      - explorationAlgorithm: interface
     *          - rightHandExploration: class
     *      - CommandPattern: package
     *          - Command: interface
     *              - MoveCommand: class
     *          - Command: interface, Undoable: interface 
     *              - TurnLeftCommand: class
     *              - TurnRightCommand: class
     *       - ObserverPattern: package
     *          - CommandObserver: interface
     *              - PathRecorder: class
     */

    // Declare logger and explore class
    private static final Logger logger = LogManager.getLogger();
    private static final Explore explore = new Explore();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Instantiate options
        Options options = createOptions();

        // Create parser
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            // if -i flag is present
            if (cmd.hasOption("i")) {
                processInputFile(cmd);
            } else {
                logger.error("/!\\ An error has occured /!\\ " + "\n");
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\" + "\n");
        }
        logger.info("**** Computing path");
        logger.info("** End of MazeRunner" + "\n");

    }

    private static Options createOptions() {
        // create new options, add -i and -p options, return options object
        Options options = new Options();
        options.addOption("i", true, "input file");
        options.addOption("p", true, "check Path");
        return options;
    }

    private static void processInputFile(CommandLine cmd) throws Exception {
        // get input file from option -i, read maze from file
        String inputFile = cmd.getOptionValue("i");
        MazeReader.readMazeFromFile(inputFile, explore);

        // if -p flag, check path; else, explore maze.
        if (cmd.hasOption("p")) {
            checkPath(cmd);
        } else {
            exploreMaze();
        }
    }

    private static void checkPath(CommandLine cmd) {
        // get testPath from option -p, check path
        String testPath = cmd.getOptionValue("p").trim();
        boolean valid = explore.checkPath(testPath);

        // print path correctness
        if (valid) {
            System.out.println("correct path");
        } else {
            System.out.println("incorrect path");
        }
    }

    private static void exploreMaze() {
        // explore maze
        explore.explore();
    }
}
