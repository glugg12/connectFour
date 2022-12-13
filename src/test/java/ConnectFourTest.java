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

}
