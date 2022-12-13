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
        assertEquals(4, testObj.getCursor());
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
        assertEquals(4,testObj.getCursor());
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
        int[] innerArray = {0,0,0,0,0,0};
        int[] pieceColumn = {1,0,0,0,0,0};
        int testArray[][] = {innerArray,innerArray,innerArray,pieceColumn,innerArray,innerArray,innerArray};
        testObj.playPiece(1);
        assertArrayEquals(testArray,testObj.getSpaces());
    }

}
