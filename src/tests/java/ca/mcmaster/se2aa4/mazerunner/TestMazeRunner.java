package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.se2aa4.mazerunner.ObserverPattern.*;
import ca.mcmaster.se2aa4.mazerunner.CommandPattern.*;

public class TestMazeRunner {
    private Maze maze;
    private Player player;
    private PathRecorder recorder;
    private PathExecutor executor;
    private PathValidator validator;
    private RightHandExploration rightHandExploration;
    private Explore explore;

    @BeforeEach
    public void setUp() {
        maze = new Maze();
        // Build small maze
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
        
        // Initialize all sub classes needed
        maze.findEntryRow();
        maze.findExitRow();
        player = new Player(maze.getEntryRow());
        recorder = new PathRecorder();
        executor = new PathExecutor();
        validator = new PathValidator(maze, player);
        explore = new Explore();
        explore.setMaze(maze); // inject maze into explore
        rightHandExploration = new RightHandExploration(player, maze, recorder);
    }

    @Test
    public void testEntryExitPoints() {
        // Verify entry and exit points
        assertEquals(8, maze.getEntryRow());
        assertEquals(5, maze.getExitRow());
    }

    @Test
    public void testRightHandExploration() {
        rightHandExploration.explore(maze, player);
        
        // Verify path was recorded
        assertFalse(recorder.getCanonicalPath().isEmpty());
        assertFalse(recorder.getFactorizedPath().isEmpty());
        
        // Verify player reached exit
        assertEquals(maze.getWidth() - 1, player.getCol());
    }

    @Test
    public void testCommandExecution() {
        // create commands and add observer
        MoveForwardCommand forwardCmd = new MoveForwardCommand(player);
        forwardCmd.addObserver(recorder);
        
        TurnRightCommand rightCmd = new TurnRightCommand(player);
        rightCmd.addObserver(recorder);
        
        // execute commands
        executor.executeCommand(forwardCmd);
        executor.executeCommand(rightCmd);
        
        // Verify player path after commands
        assertEquals("FR", recorder.getCanonicalPath());
    }

    @Test
    public void testPathValidation() {
        // Test valid path
        String validPath = "R L F R F R 3L F R L F R F R L F R F R L F R 3L F R L F R L F R L F R F R L F R F R L F R L F R L F R 3L F R L F R F R L F R L F R L F R F R L F R F R L F R 3L F R L F R 2L F R L F R 2L F R L F R L F R L F R F R L F R F R L F R 3L F R L F R L F R L F R F R L F R F R L F R 3L F R L F R F R L F R F R L F R L F R L F R F R L F R 2L F R L F R F R L F R 2L F ";
        assertTrue(validator.checkPath(validPath)); // Should return true
        
        // Test invalid path (hits wall)
        String invalidPath = "FFFFFF";
        assertFalse(validator.checkPath(invalidPath)); // Should return false
    }

    @Test
    public void testPlayerMovement() {
        // Initial position
        assertEquals(8, player.getRow());
        assertEquals(0, player.getCol());
        
        // Move forward
        player.moveForward();
        assertEquals(8, player.getRow());
        assertEquals(1, player.getCol());
        
        // Turn right and move
        player.turnRight();
        player.moveForward();
        assertEquals(9, player.getRow());
        assertEquals(1, player.getCol());
    }

    @Test
    public void testMazeDimensions() {
        // Check maze dimensions
        assertEquals(11, maze.getHeight());
        assertEquals(11, maze.getWidth());
    }

    @Test
    public void testWallDetection() {
        // Check wall detection
        assertTrue(maze.isWall(0, 0)); // Corner wall
        assertFalse(maze.isWall(8, 1)); // Valid path
        assertTrue(maze.isWall(8, 2)); // Wall
    }

    @Test
    public void testPathRecorder() {
        // Test path recording
        recorder.recordAction('F');
        recorder.recordAction('F');
        recorder.recordAction('L');
        
        assertEquals("FFL", recorder.getCanonicalPath());
        assertEquals("2F L", recorder.getFactorizedPath().trim());
    }

    @Test
    public void testPlayerPredictMove() {
        // Test player move prediction
        int[] predicted = player.predictMove();
        assertEquals(8, predicted[0]); // Same row
        assertEquals(1, predicted[1]); // Next column
        
        player.turnRight();
        predicted = player.predictMove();
        assertEquals(9, predicted[0]); // Next row
        assertEquals(0, predicted[1]); // Same column
    }
}