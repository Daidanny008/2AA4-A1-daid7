package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeReader {
    private static final Logger logger = LogManager.getLogger();
    
    public static void readMazeFromFile(String filePath, Explore explore) throws IOException {
        logger.info("**** Reading the maze from file " + filePath + "\n");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // declare variables
            String line;
            int width = -1;
            
            while ((line = reader.readLine()) != null) {
                // Initialize width on first line
                if (width == -1) {
                    width = line.length();
                }
                
                // Pad the line with spaces if needed
                line = padLine(line, width);
                
                // Add row to maze
                explore.addRow(line);
                
                // Log the maze row
                logMazeRow(line);
            }
        }
    }
    
    private static String padLine(String line, int width) {
        // Pad the line with spaces to match the width
        while (line.length() < width) {
            line += " ";
        }
        return line;
    }
    
    private static void logMazeRow(String line) {
        for (int idx = 0; idx < line.length(); idx++) {
            if (line.charAt(idx) == '#') {
                logger.info("WALL ");
            } else if (line.charAt(idx) == ' ') {
                logger.info("PASS ");
            }
        }
        logger.info(System.lineSeparator());
    }
}