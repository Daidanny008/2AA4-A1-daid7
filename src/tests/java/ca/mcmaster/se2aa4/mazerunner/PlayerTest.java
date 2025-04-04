package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private Maze maze;
    private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3; // final constants for directions

    @BeforeEach
    public void setUp() {
        // load a straight maze for testing
        maze = new Maze();
        maze.addRow("#####");
        maze.addRow("     "); // Test maze with a straight path
        maze.addRow("#####");

        // Initialize player at the entry point (1,0)
        maze.findEntryRow(); // Entry at (1,0)
        player = new Player(maze.getEntryRow());
    }

    @Test
    public void testInitialState() {
        // Check initial position and direction
        assertEquals(1, player.getRow());
        assertEquals(0, player.getCol());
        assertEquals(EAST, player.predictMove()[1]); // Facing east
    }

    @Test
    public void testBasicMovement() {
        // Test single forward move
        player.moveForward();
        assertEquals(1, player.getRow());
        assertEquals(1, player.getCol());

        // Verify predictMove shows next position
        int[] nextPos = player.predictMove();
        assertEquals(1, nextPos[0]);
        assertEquals(2, nextPos[1]);
    }

    @Test
    public void testTurnSequence() {
        // Turn right (should face south)
        player.turnRight();
        player.moveForward();
        assertEquals(2, player.getRow());
        assertEquals(0, player.getCol());

        // Turn left twice (should face north)
        player.turnLeft();
        player.turnLeft();
        player.moveForward();
        assertEquals(1, player.getRow()); // Back to original row
    }

    @Test
    public void testDirectionWrapping() {
        // Test direction wrapping (4 turns right = original direction)
        player.turnRight();
        player.turnRight();
        player.turnRight();
        player.turnRight();
        assertEquals(EAST, player.predictMove()[1]);

        // Test direction wrapping (4 turns left = original direction)
        player.turnLeft();
        player.turnLeft();
        player.turnLeft();
        player.turnLeft();
        assertEquals(EAST, player.predictMove()[1]);
    }

    @Test
    public void testPositionAfterFullRotation() {
        // Test position after a full rotation (4 right turns)
        int initialRow = player.getRow();
        int initialCol = player.getCol();
        
        // Full rotation should return to original position
        player.turnRight();
        player.turnRight();
        player.turnRight();
        player.turnRight();
        player.moveForward();
        
        assertEquals(initialRow, player.getRow());
        assertEquals(initialCol + 1, player.getCol()); // Should still move east
    }
}