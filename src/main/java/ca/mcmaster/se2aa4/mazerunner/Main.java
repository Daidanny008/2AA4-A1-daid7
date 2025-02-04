package ca.mcmaster.se2aa4.mazerunner;

// Imports
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    // Program structure:
    // Main: Class
    //  - Explore: Class
    //      - Maze: Class
    //      - Player: Class
    //      - explorationAlgorithm: Interface 
    //          - rightHandExploration: Class

    private static final Logger logger = LogManager.getLogger();

    // Classes
    private static Explore explore = new Explore();

    // Necessary variables
    private static int width = 0;
    private static boolean firstLine = true;

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Instantiate options
        Options options = new Options();
        options.addOption("i", true, "input file");
        options.addOption("p", true, "check Path");

        // Create parser
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            // if -i flag is present
            if (cmd.hasOption("i")) {

                String inputFile = cmd.getOptionValue("i");

                logger.info("**** Reading the maze from file " + inputFile + "\n");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String line = "";
                while ((line = reader.readLine()) != null) {

                    // if first line read, initialize width of maze
                    if (firstLine) {
                        width = line.length();
                        firstLine = false;
                    }

                    // Pad the line with spaces if it's shorter than the width
                    while (line.length() < width) {
                        line += " ";
                    }

                    // Append row of maze to maze arraylist
                    explore.addRow(line);
                    
                    // logger info
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            logger.info("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            logger.info("PASS ");
                        }
                    }
                    logger.info(System.lineSeparator());
                }
                // close reader
                reader.close();

                // If -p flag, check path, else print correct right hand path
                if (cmd.hasOption("p")) {
                    // get path
                    String testPath = cmd.getOptionValue("p").trim();
                    // check path
                    explore.checkPath(testPath);
                }
                else { // only -i flag
                    // Call right hand exploration
                    explore.setAlgorithm(new rightHandExploration());                    
                    explore.explore();
                }
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

    }
}
