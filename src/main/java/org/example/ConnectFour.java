package org.example;

import java.io.IOException;
import java.util.Scanner;

class Board
{
    public int[][] space = new int[7][6];
}
public class ConnectFour {
    Scanner inStream = new Scanner(System.in);
    public Board playBoard;
    //cursor highlights a column - row selection might be needed down the line
    protected int cursor;
    int activePlayer = 1;

    /**
     * Getter for the array of board spaces
     * @return int[][] is an array of space states
     */
    public int[][] getSpaces()
    {
        return playBoard.space;
    }

    /**
     * Setter for cursor position clamps to 1 <= newCursor <= 7
     * @param newCursor
     * The new cursor position
     */
    public void setCursor(int newCursor)
    {
        if(newCursor > 7) {
            newCursor = 7;
        }
        else if(newCursor < 1)
        {
            newCursor = 1;
        }
        this.cursor = newCursor;
    }

    /**
     * Getter for cursor position
     * @return returns current cursor position.
     */
    public int getCursor()
    {
        return this.cursor;
    }

    /**
     * Constructor for Connect Four. Builds a playBoard using 0 values - representing empty spaces.
     * Could have used a multidimensional array of chars I suppose, but numbers work fine as is. I like ints.
     */
    public ConnectFour() {
        int[] column ={0,0,0,0,0,0};
        int[][] rows = {{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        this.playBoard = new Board();
        this.playBoard.space = rows;
        this.cursor = -1; //four is the middle of a connect four board. Nice spot to start.
        gameLoop();
    }

    private void gameLoop()
    {
        while(true)
        {
            updateScreen();
            requestInput();
        }
    }

    private void requestInput()
    {
        //Use switch cases to enforce requested control scheme in brief.
        System.out.printf("Player %d, Please choose column to play in > ", activePlayer);
        String input = inStream.nextLine();
        switch(input)
        {
            case("1"):
            case("2"):
            case("3"):
            case("4"):
            case("5"):
            case("6"):
            case("7"):
                setCursor(Integer.parseInt(input));
                playPiece(activePlayer);
                break;
            default:
        }

    }

    /**
     * Just some fun stuff. Function to "animate" a piece falling down a column. Doubles as placing a piece in a column.
     * Without wanting to animate within console, the best way would probably be to just search for first empty space in chosen column (0).
     * Potential refactor candidate here - could do above, then animate separately.
     */
    public void animatePieceFalling()
    {
        boolean earlyExit = false;
        while(!earlyExit)
        {
            earlyExit = true;
            for(int row = 0; row < 5; row++)
            {
                for(int column[]: playBoard.space)
                {
                    if(column[row] == 0 && column[row+1] !=0)
                    {
                        earlyExit = false;
                        column[row]=column[row+1];
                        column[row+1]=0;
                    }
                }
            }
            updateScreen();
        }


    }
    public void playPiece(int player)
    {
        playBoard.space[cursor-1][5] = player;
        animatePieceFalling();
    }

    public void moveCursor(int movement)
    {
        setCursor(cursor + movement);
        updateScreen();
    }

    /**
     * updateScreen clears console and redraws with new states. Does not clear console in IDE console.
     */
    public void updateScreen()
    {
        try
        {
            //almost certainly not great practice but just wanted to wait the console for a bit. For fun, y'know.
            Thread.sleep(500);
        }catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        ConsoleUtilities.clearConsole();
        for(int cursorDisp = 0; cursorDisp < 7; cursorDisp++)
        {
            if(cursor -1 == cursorDisp)
            {
                System.out.print("v ");
            }
            else
            {
                System.out.print(". ");
            }
        }
        System.out.print("\n");
        for(int row = 5; row >= 0; row--)
        {
            for(int column[]:playBoard.space)
            {
                System.out.print(column[row] + " ");
            }
            System.out.print("\n");
        }
    }


}
