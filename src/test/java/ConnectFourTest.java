import org.example.ConnectFour;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectFourTest {
    @Test
    /**
     * Tests to see if the playboard initialises correctly.
     */
    public void constructConnectFour()
    {
        int[] innerArray = {0,0,0,0,0,0};
        int testArray[][] = {innerArray,innerArray,innerArray,innerArray,innerArray,innerArray,innerArray};
        ConnectFour testObj = new ConnectFour();
        int[][] outcomeArray = testObj.getSpaces();
        assertArrayEquals(testArray,outcomeArray);
    }

    /**
     * Tests to see if the game's cursor can be set and retrieved
     */
    @Test
    public void testCursorSetGet()
    {
        ConnectFour testObj = new ConnectFour();
        //test constructor pos
        assertEquals(-1, testObj.getCursor());
        //test setting next number
        testObj.setCursor(5);
        assertEquals(5, testObj.getCursor());
        //test setting number based on current cursor position
        testObj.setCursor(testObj.getCursor()-1);
        assertEquals(4, testObj.getCursor());
        //test setting to number not close to current
        testObj.setCursor(1);
        assertEquals(1,testObj.getCursor());
    }

    /**
     * Tests to see if the cursor value clamps correctly
     */
    @Test
    public void testCursorClamping()
    {
        ConnectFour testObj = new ConnectFour();
        assertEquals(-1,testObj.getCursor());
        //test large value
        testObj.setCursor(123456);
        assertEquals(7,testObj.getCursor());
        //test small value
        testObj.setCursor(-123456);
        assertEquals(1,testObj.getCursor());
        //test values just out of range
        testObj.setCursor(0);
        assertEquals(1,testObj.getCursor());
        testObj.setCursor(8);
        assertEquals(7, testObj.getCursor());
    }

    /**
     * Tests that a piece can be played in a column.
     * Effectively also tests the funny little animate function that moves pieces from top to bottom
     */
    @Test
    public void testPlayPiece()
    {
        ConnectFour testObj = new ConnectFour();
        testObj.setCursor(4);
        int[] innerArray = {0,0,0,0,0,0};
        int[] pieceColumn = {1,0,0,0,0,0};
        int testArray[][] = {innerArray,innerArray,innerArray,pieceColumn,innerArray,innerArray,innerArray};
        testObj.playPiece(1);
        assertArrayEquals(testArray,testObj.getSpaces());
    }

    /**
     * Tests to see if various states are checked appropriately for win conditions.
     */
    @Test
    public void testWinChecking()
    {
        ConnectFour testObj = new ConnectFour();
        //can test win conditions by just manually placing winning pieces in a board
        //checking column wins
        int[] winningCol = {0,0,1,1,1,1};
        int[] emptyCol = {0,0,0,0,0,0};
        int[][] fullTestArray = {emptyCol,winningCol,emptyCol,emptyCol,emptyCol,emptyCol,emptyCol};
        testObj.setSpaces(fullTestArray);
        assertEquals(false, testObj.win);
        testObj.checkWinCondition();
        assertEquals(true, testObj.win);
        //manually reset win tracker in testObj ready for next test
        testObj.win = false;
        //horizontal wins next
        int[] horWin = {0,0,0,0,0,1};
        int[][] horizontalWinArray = {horWin, horWin, horWin, horWin, emptyCol, emptyCol, emptyCol};
        testObj.setSpaces(horizontalWinArray);
        testObj.checkWinCondition();
        assertEquals(true, testObj.win);
        testObj.win = false;
        //diag right wins
        int[] diagRow1 = {1,0,0,0,0,0};
        int[] diagRow2 = {0,1,0,0,0,0};
        int[] diagRow3 = {0,0,1,0,0,0};
        int[] diagRow4 = {0,0,0,1,0,0};
        int[][] diagRightArray = {diagRow1, diagRow2, diagRow3, diagRow4, emptyCol, emptyCol, emptyCol};
        testObj.setSpaces(diagRightArray);
        testObj.checkWinCondition();
        assertEquals(true, testObj.win);
        testObj.win = false;
        //diag left wins
        int[][] diagLeftArray = {diagRow4, diagRow3, diagRow2, diagRow1, emptyCol, emptyCol, emptyCol};
        testObj.setSpaces(diagLeftArray);
        testObj.checkWinCondition();
        assertEquals(true, testObj.win);
        testObj.win = false;
        //no win at all
        int[][] noWinArray = {diagRow1, diagRow3, diagRow4, diagRow2, diagRow1, diagRow1, diagRow2};
        testObj.setSpaces(noWinArray);
        testObj.checkWinCondition();
        assertEquals(false, testObj.win);
    }

    /**
     * Tests if a blitz clears a column correctly
     */
    @Test
    public void testBlitz()
    {
        ConnectFour testObj = new ConnectFour();
        int[] innerArray = {0,0,0,0,0,0};
        int[] pieceColumn = {1,2,1,2,1,2};
        //java does this weird thing where certain things are passed by reference even if I don't want it to be >:(
        //I'm annoyed about it at this point of coding, so I'm investigating
        //wonder if there's a way to just pass the values of a variable rather than the variable as a whole...
        //hmm I could do a new int[][] inside playboard setSpace, but can I use a passed in var to initialise it? seems like a no.
        //from digging around the net sounds like most people just pass through a loop to set values
        //feels clunky, but I'll do that inside playBoard.setSpaces. Leaving notes here and will point here from there.
        int testArray[][] = {innerArray,innerArray,pieceColumn,pieceColumn,pieceColumn,innerArray,innerArray};
        testObj.setSpaces(testArray);
        testObj.setCursor(4);
        testObj.playColumnClear();
        int outcomeArray[][] = {innerArray,innerArray,pieceColumn,innerArray,pieceColumn,innerArray,innerArray};
        assertArrayEquals(outcomeArray,testObj.getSpaces());
    }

    @Test
    public void testTimeBomb()
    {
        ConnectFour testObj = new ConnectFour();
        int[] emptyRow = {0,0,0,0,0,0};
        int[] checkerRow = {1,2,1,2,1,2};
        int[] solidRow = {1,1,1,1,1,1};
        int[][] mockBoard = {checkerRow, checkerRow, emptyRow, {1,1,5,1,1,1}, solidRow, solidRow, emptyRow};
        //I have to visualise this one lol
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        //
        //Nice. If we were to place a bomb say... here...
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        // 2 2 0 1 1 1 0
        // 1 1 0 * 1 1 0
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        //
        //When it fires off, we should clear hor, ver, and diag to get...
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        // 2 2 § § § 1 0
        // 1 1 § * § 1 0
        // 2 2 § § § 1 0
        // 1 1 0 1 1 1 0
        //
        // 2 2 0 1 1 1 0
        // 1 1 0 1 1 1 0
        // 2 2 0 0 0 1 0
        // 1 1 0 0 0 1 0
        // 2 2 0 0 0 1 0
        // 1 1 0 1 1 1 0
        //
        int[][] outcome = {{1,2,1,2,1,2},{1,2,1,2,1,2}, {0,0,0,0,0,0}, {1,0,0,0,1,1},{1,0,0,0,1,1},{1,1,1,1,1,1},{0,0,0,0,0,0}};
        testObj.setSpaces(mockBoard);
        //bomb should ignite just fine
        testObj.igniteBombs();
        assertArrayEquals(outcome,testObj.getSpaces());
        //going to test against some (literal) edge cases next
        //bottom left corner
        outcome = new int[][]{{0,0,1,2,1,2},{0,0,1,2,1,2}, {0,0,0,0,0,0}, {1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{0,0,0,0,0,0}};
        int[][] mockBoard2 = {{5,2,1,2,1,2},{1,2,1,2,1,2}, {0,0,0,0,0,0}, {1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{0,0,0,0,0,0}};
        testObj.setSpaces(mockBoard2);
        testObj.igniteBombs();
        assertArrayEquals(outcome,testObj.getSpaces());
        //far right edge
        int[][] mockBoard3 = {{1,2,1,2,1,2},{1,2,1,2,1,2}, {0,0,0,0,0,0}, {1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{0,0,0,5,0,0}};
        outcome = new int[][]{{1,2,1,2,1,2},{1,2,1,2,1,2}, {0,0,0,0,0,0}, {1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,0,0,0,1},{0,0,0,0,0,0}};
        testObj.setSpaces(mockBoard3);
        testObj.igniteBombs();
        assertArrayEquals(outcome,testObj.getSpaces());
    }
}
