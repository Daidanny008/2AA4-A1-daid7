package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ===== TEST CLASS =====
public class TestMazeRunner {

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

    // Test 5: Is wall function of maze
    @Test
    public void testIsWall() {
        // Check if wall
        assertTrue(maze.isWall(0, 0)); // Top left corner
        assertTrue(maze.isWall(0, 1)); // Top left wall
        assertTrue(maze.isWall(1, 0)); // Left wall
        assertTrue(maze.isWall(1, 10)); // Right wall
        assertTrue(maze.isWall(10, 10)); // Bottom right corner
        assertFalse(maze.isWall(5, 5)); // Middle of maze
    }

    // Test 6: maze height and width
    @Test
    public void testMazeHeightWidth() {
        assertEquals(11, maze.getHeight()); // Height of maze
        assertEquals(11, maze.getWidth()); // Width of maze
    }

    // Test 7: Player predictMove function
    @Test
    public void testPlayerPredictMove() {
        // Check if player can predict move
        player = new Player(maze.getEntryRow());
        assertEquals(8, player.predictMove()[0]); // Predict move to right
        assertEquals(1, player.predictMove()[1]); // Predict move to right
        player.moveForward(); // Move forward
        assertEquals(8, player.getRow()); // Check if moved correctly
        assertEquals(1, player.getCol()); // Check if moved correctly
    }

    // Test 8: Player position
    @Test
    public void testPlayerPosition() {
        // Check if player can predict move
        player = new Player(maze.getEntryRow());
        assertEquals(8, player.getRow()); // Check if moved correctly
        assertEquals(0, player.getCol()); // Check if moved correctly
    }

    // Test 9: Player movement
    @Test
    public void testPlayerMovement() {

        // Find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // current position should be (7,1)
        int row = player.getRow();
        int col = player.getCol();
        assertEquals(8, row); // Row should remain the same
        assertEquals(0, col); // Column should increase by 1

        // Move forward
        player.moveForward();

        assertEquals(8, player.getRow()); // Row should remain the same
        assertEquals(1, player.getCol()); // Column should increase by 1

        // Update current position
        row = player.getRow();
        col = player.getCol();

        // Turn right and move
        player.turnRight();
        player.moveForward();
        assertEquals(player.getRow(), row + 1); // Row should increase by 1
        assertEquals(player.getCol(), col); // Column should remain the same

        // Update current position
        row = player.getRow();
        col = player.getCol();

        // Turn left and move
        player.turnLeft();
        player.moveForward();
        assertEquals(player.getRow(), row); // Row should remain the same
        assertEquals(player.getCol(), col+1); // Column should increase by 1
    }

    // Test 10: Player turn movement does not change position
    @Test
    public void testPlayerTurnMovement() {
        // Find entry and exit points
        maze.findEntryRow();
        maze.findExitRow();

        // Instantiate player class when entry point is found
        player = new Player(maze.getEntryRow());

        // current position should be (8,0)
        int row = player.getRow();
        int col = player.getCol();
        assertEquals(8, row); // Row should remain the same
        assertEquals(0, col); // Column should increase by 1

        // Turn right
        player.turnRight();
        assertEquals(player.getRow(), row); // Row should remain the same
        assertEquals(player.getCol(), col); // Column should remain the same

        // Turn left
        player.turnLeft();
        assertEquals(player.getRow(), row); // Row should remain the same
        assertEquals(player.getCol(), col); // Column should remain the same
    }
}
