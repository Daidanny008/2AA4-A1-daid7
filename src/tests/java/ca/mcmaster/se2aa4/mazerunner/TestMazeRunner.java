package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ===== TEST CLASS =====
public class TestMazeRunner {

    /*
    @Test
    void sampleTest() {
        assertEquals(1, 1); // Example test
    }
    */

    // declare sub classes
    private Explore explore = new Explore();
    private Player player;
    private rightHandExploration rightHandExploration = new rightHandExploration();
    private Maze maze = getSmallMaze(); // Initialize maze object as small maze
    
    // ------------------------------- HELPER METHODS ---------------------------------------------
    private Maze getSmallMaze() {
        // Initialize the maze object
        maze = new Maze();

        // create small maze txt
        maze.addRow("###########");
        maze.addRow("#         #");
        maze.addRow("### ### ###");
        maze.addRow("#     #   #");
        maze.addRow("# # ##### #");
        maze.addRow("# #     #  "); // exit (5,10)
        maze.addRow("### # #####");
        maze.addRow("#   #     #");
        maze.addRow("  # # ### #"); // entrance (8,0)
        maze.addRow("# # # #   #");
        maze.addRow("###########");

        // return maze object
        return maze;
    }

    // ------------------------------- TEST CASES -------------------------------------------------

    // Test 1: check entry/exit
    // Note: change maze to non-static to text multiple mazes
    @Test
    public void testfindEntryExitRows() {
        // use the maze object to check entry and exit points
        this.maze.findEntryRow();
        this.maze.findExitRow();
        
        assertEquals(8, this.maze.getEntryRow()); // Entrance at row 1 (left)
        assertEquals(5, this.maze.getExitRow());  // Exit at row 1 (right)
        }

    // Test 2: Right-Hand Rule on simple maze - canonical and factorized path
    // Note: change player to non-static to test multiple paths separately
    @Test
    public void testRightHandSmallMaze() {
        // find entry and exit points
        this.maze.findEntryRow();
        this.maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(this.maze.getEntryRow());
        
        // Right hand exploration
        rightHandExploration.explore(this.maze, player);

        // Assert player moved correctly
        String correctPath = "F R F 2L 2F R 2F R 2F 2L 4F R 2F R 4F 2L 2F R 4F R 2F R 2F "
        + "2L 2F L 2F L 4F R 2F R 2F 2L 4F R 2F R 2F 2L 2F R 2F R 4F R 2F L 2F R 2F L F";
        String cannonPath = "FRFLLFFRFFRFFLLFFFFRFFRFFFFLLFFRFFFFRFFRFFLLFFLFFLFFFFRFFRFFLLFFFFRFFRFFLLFFRFFRFFFFRFFLFFRFFLF";
        assertEquals(correctPath, player.getFactorized().trim()); 
        assertEquals(cannonPath, player.getCanonical().trim());
        assertEquals(player.getCol(), maze.getWidth() - 1); // Reached right side
    }


    // Test 3: Valid path verification
    // Note: change player to non-static to test multiple paths separately
    // Note: make checkPath testable by returning boolean instead of system.exit(1)
    @Test
    public void testValidPathVerification() {
        String correctPath = "F R F 2L 2F R 2F R 2F 2L 4F R 2F R 4F 2L 2F R 4F R 2F R 2F "
        + "2L 2F L 2F L 4F R 2F R 2F 2L 4F R 2F R 2F 2L 2F R 2F R 4F R 2F L 2F R 2F L F";
        explore.checkPath(correctPath); // Assert true by not throwing exception
        //assertThrows(RuntimeException.class, () -> explore.checkPath(correctPath + "F"));
        //explore.checkPath(correctPath+"F"); // Valid path for simple maze
    }

    // Test 4: CLI argument parsing (-i and -p flags)
    @Test
    public void testCliParsing() {
        String[] args = {"-i", "small.straight.txt", "-p", "FFFF"};
        Main.main(args); // Should execute without errors
    }

    
    /*
    // Test 5: Path connectivity (algorithm reaches exit)
    @Test
    public void testPathReachesExit() {
        // find entry and exit points
        this.maze.findEntryRow();
        this.maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(this.maze.getEntryRow());

        // Assert player moved correctly
        assertEquals(player.getCol(), maze.getWidth() - 1); // Reached right side
    }
    @Test
    public void testInvalidPathThrowsExceptionWithMessage() {
        String correctPath = "F R F 2L 2F R 2F R 2F 2L 4F R 2F R 4F 2L 2F R 4F R 2F R 2F "
            + "2L 2F L 2F L 4F R 2F R 2F 2L 4F R 2F R 2F 2L 2F R 2F R 4F R 2F L 2F R 2F L F";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            explore.checkPath(correctPath + "F");
        });

        assertEquals("Invalid path detected", exception.getMessage());
    }


    // Test 7: Invalid path verification
    @Test
    public void testInvalidPathVerification() {
        loadSimpleMaze();
        assertThrows(RuntimeException.class, () -> {
            explore.checkPath("LFF"); // Invalid path (turns into wall)
        });
    }

    

    // Test 9: Entry/exit detection consistency
    @Test
    public void testEntryExitConsistency() {
        loadComplexMaze();
        explore.findEntryRow();
        explore.findExitRow();
        assertNotEquals(-1, explore.getMaze().getEntryRow());
        assertNotEquals(-1, explore.getMaze().getExitRow());
    }

    // Test 10: Path connectivity (algorithm reaches exit)
    @Test
    public void testPathReachesExit() {
        loadComplexMaze();
        explore.setAlgorithm(new rightHandExploration());
        explore.explore();
        assertTrue(player.getCol() == explore.getMaze().getWidth() - 1); // Reached right side
    }
    */
}
