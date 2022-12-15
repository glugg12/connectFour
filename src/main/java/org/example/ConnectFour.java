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
    boolean playingB = false;
    boolean playingT = false;
    public boolean win;

    /**
     * Getter for the array of board spaces
     * @return int[][] is an array of space states
     */
    public int[][] getSpaces()
    {
        return playBoard.space;
    }

    public void setSpaces(int[][] inArray) {
        //Was having issues with pass by reference stuff in java wrt array.
        //See the train of thought over in ConnectFourTest.testBlitz() that led to the loops.

        //don't think I can do a for each where I pull out of two different vars.
        //maybe maps? Rings a bell? Will come back to this if I have time.
        for(int column = 0; column < 7; column++)
        {
            for(int row = 0; row < 6; row++)
            {
                playBoard.space[column][row] = inArray[column][row];
            }
        }
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
        int[][] rows = {{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        this.playBoard = new Board();
        this.playBoard.space = rows;
        this.cursor = -1;
        win = false;
    }

    /**
     * Begins the game loop for this connect four object.
     */
    public void startGame()
    {
        gameLoop();
    }

    /**
     * The update loop for the  game. Will continue asking for inputs and checking win conditions until a winner is found.
     */
    private void gameLoop()
    {
        updateScreen(false);
        while(!win)
        {
            requestInput();
            if(!playingT && !playingB)
            {
                boolean bombPresent = false;
                //I'm just checking spaces for bombs. Could store bombs and their positions in a list instead to save doing this each time.
                //Probably better, but saving it as a "tidy up" kind of job
                for(int column[]:playBoard.space)
                {
                    for(int space:column)
                    {
                        if(space <=9 && space >=6)
                        {
                            bombPresent = true;
                        }
                    }
                }
                if(bombPresent)
                {
                    showBombStatus();
                    //I could update bomb values every time, but we only really need to do it if there's a bomb on board
                    updateBombs();
                    showBombStatus();
                    //we need to fire off any bombs with a 0 (actual value 5) value.
                    igniteBombs();
                    updateScreen(false);

                }
            }
            checkWinCondition();
        }
    }

    private void showBombStatus()
    {
        updateScreen(true);
        updateScreen(false);
        updateScreen(true);
        updateScreen(false);
    }

    public void updateBombs()
    {
        //for each loop seems to give me values not reference? Can't believe I actually miss a pointer system.

//        for(int column[]:playBoard.space)
//        {
//            for(int space:column)
//            {
//                if(space <=9 && space >=6)
//                {
//                    //if we find a bomb in a space, tick it down a step
//                    space-=1;
//                }
//            }
//        }

        for(int col = 0; col < 7; col++)
        {
            for(int row = 0; row<6; row++)
            {
                if(playBoard.space[col][row] <=9 && playBoard.space[col][row] >=6)
                {
                    playBoard.space[col][row]--;
                }
            }
        }
    }
    public void igniteBombs()
    {
        //if bomb timers are 0 pop em
        //in our system we're looking for 5
        for(int col = 0; col < 7; col++)
        {
            for(int row = 0; row<6; row++)
            {
                if(playBoard.space[col][row] == 5)
                {
                    //we need to set spaces all around this thing to 3 first for fun animations, then set it to 0.
                    //spaces to hit
                    //[x-1,y+1] [x, y+1] [x+1,y+1]
                    //[x-1,y+0] B O M B  [x+1,y+0]
                    //[x-1,y-1] [x, y-1] [x+1,y-1]

                    //also need to be aware of out of bounds index here
                    //check if our value is at any edges, and if we are, don't try and change anything past that bounds
                    if(col != 0)
                    {
                        playBoard.space[col-1][row] = 3;
                        if(row != 0)
                        {
                            playBoard.space[col-1][row-1] = 3;
                        }
                        if(row != 5)
                        {
                            playBoard.space[col-1][row+1] = 3;
                        }
                    }
                    if(col != 6)
                    {
                        playBoard.space[col+1][row] = 3;
                        if(row != 0)
                        {
                            playBoard.space[col+1][row-1] = 3;
                        }
                        if(row != 5)
                        {
                            playBoard.space[col+1][row+1] = 3;
                        }
                    }
                    if(row != 0)
                    {
                        playBoard.space[col][row-1] = 3;
                    }
                    if(row != 5)
                    {
                        playBoard.space[col][row+1] = 3;
                    }
                    //this one should never be out of bounds.
                    playBoard.space[col][row] = 3;

                    updateScreen(false);
                    if(col != 0)
                    {
                        playBoard.space[col-1][row] = 0;
                        if(row != 0)
                        {
                            playBoard.space[col-1][row-1] = 0;
                        }
                        if(row != 5)
                        {
                            playBoard.space[col-1][row+1] = 0;
                        }
                    }
                    if(col != 6)
                    {
                        playBoard.space[col+1][row] = 0;
                        if(row != 0)
                        {
                            playBoard.space[col+1][row-1] = 0;
                        }
                        if(row != 5)
                        {
                            playBoard.space[col+1][row+1] = 0;
                        }
                    }
                    if(row != 0)
                    {
                        playBoard.space[col][row-1] = 0;
                    }
                    if(row != 5)
                    {
                        playBoard.space[col][row+1] = 0;
                    }
                    //this one should never be out of bounds.
                    playBoard.space[col][row] = 0;

                }
            }
        }
    }

    /**
     * Asks for an input of which column to play. If a column is not correctly selected, nothing happens.
     * If B or T or input, activate special pieces.
     */
    private void requestInput()
    {
        //Use switch cases to enforce requested control scheme in brief.
        if(playingB)
        {
            System.out.printf("Blitz! Please choose column to clear, %d > ", activePlayer);
        }
        else if(playingT)
        {
            System.out.printf("Time Bomb! Please choose column to place it in, %d > ", activePlayer);
        }
        else
        {
            System.out.printf("Player %d, Please choose column to play in > ", activePlayer);
        }
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
                //if we're currently playing a special piece, we need to do different things.
                //putting if statement here allows me to reuse this switch quite handily
                if(playingB)
                {
                    setCursor(Integer.parseInt(input));
                    playColumnClear();
                    playingB = false;
                    swapPlayer();
                }
                else if(playingT)
                {
                    setCursor(Integer.parseInt(input));
                    playTimeBomb();
                    playingT = false;
                    swapPlayer();
                }
                else
                {
                    setCursor(Integer.parseInt(input));
                    playPiece(activePlayer);
                    swapPlayer();
                }
                break;
            case("B"):
            case("b"):
                //activate special move
                //only want to do stuff if we're not already in B mode
                if(!playingB) {
                    playingB = true;
                }
                break;
            case("T"):
            case("t"):
                if(!playingT)
                {
                    playingT = true;
                }
            default:
        }

    }

    public void playColumnClear()
    {
        //don't need to differentiate between players for this one.
        //The blitz is indiscriminate in its destruction.
        //can animate this one too! Let's loop through each space in the column, where each space "explodes" or something
        for(int row = 5; row >= 0; row--)
        {
            playBoard.space[cursor -1][row] = 3;
            updateScreen(false);
        }
        //once we've had our fun, need to set all pieces back to empty
        for(int row = 5; row >= 0; row--)
        {
            playBoard.space[cursor -1][row] = 0;
        }
        //one last screen update
        updateScreen(false);
    }

    public void playTimeBomb()
    {
        //this gets played like any other piece, counts down for two opposing turns, then pops.
        //effectively, this is 4 whole moves regardless of player - 1 plays (0), 2 (1) > 1 (2) > 2 (3) > pop
        //going to place a 9 and when it hits six we can initiate destruction of the helpless connect four pieces
        //ooh I can reuse the play piece and give it a "player" code 9, which should place it in, then after each move,
        //but before win condition checks, pop it. Means a winning set can be ruined by a bomb, which would be funny.
        playPiece(9);
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
            updateScreen(false);
        }


    }

    /**
     * Plays a piece for the specified player. Swaps to the other player at the end of the turn.
     * @param player the player the piece is to be played by
     */
    public void playPiece(int player)
    {
        playBoard.space[cursor-1][5] = player;
        updateScreen(false);
        animatePieceFalling();
    }

    /**
     * Swaps between players.
     */
    public void swapPlayer()
    {
        if(activePlayer == 1)
        {
            activePlayer = 2;
        }
        else if(activePlayer == 2)
        {
            activePlayer = 1;
        }
    }

    /**
     * Checks for vertical, horizontal and diagonal wins for a player.
     */
    public void checkWinCondition()
    {
        //check column direction wins
        for(int column[] : playBoard.space)
        {
            //only need to check to fourth space from the bottom as any closer to the top, and it's not possible for a win
            for (int row = 0; row < 3; row++)
            {
                if(column[row] == column[row+1] &&
                        column[row] == column[row+2] &&
                        column[row] == column[row+3] &&
                        (column[row] == 1 || column[row] == 2))
                {
                    System.out.printf("Player %d wins!",column[row]);
                    win = true;
                }
            }
        }
        //check horizontal wins
        for(int row = 0; row < 6; row++)
        {
            //only need to check to fourth col from the right as any further and not possible for a win
            for(int col = 0; col < 4; col++)
            {
                if(playBoard.space[col][row] == playBoard.space[col + 1][row] &&
                        playBoard.space[col][row] == playBoard.space[col + 2][row] &&
                        playBoard.space[col][row] == playBoard.space[col + 3][row] &&
                        (playBoard.space[col][row] == 1 || playBoard.space[col][row] == 2))
                {
                    System.out.printf("Player %d wins!",playBoard.space[col][row]);
                    win = true;
                }
            }
        }

        //diagonals - this one is probably the trickiest. Need to check diag left and diag right.
        //work up columns, then move across rows
        //using a col index rather than for each column, can swap to checking both on space 4 (ind 3) then checking diag left afterwards.
        //saves writing two loops, as diagonal right only occurs beginning cols 1 - 4, diagonal left cols 4 - 7.
        for(int col = 0; col < 7; col++)
        {
            for(int row = 0; row < 3; row++)
            {
                if(col < 3)
                {
                    //we check diag rights
                    if(playBoard.space[col][row] == playBoard.space[col+1][row+1] &&
                            playBoard.space[col][row] == playBoard.space[col+2][row+2] &&
                            playBoard.space[col][row] == playBoard.space[col+3][row+3] &&
                            (playBoard.space[col][row] == 1 || playBoard.space[col][row] == 2))
                    {
                        System.out.printf("Player %d wins!",playBoard.space[col][row]);
                        win = true;
                    }
                }
                else if(col == 3)
                {
                    //if we hit the middle col, need to check both diag directions
                    //so diag right
                    if(playBoard.space[col][row] == playBoard.space[col+1][row+1] &&
                            playBoard.space[col][row] == playBoard.space[col+2][row+2] &&
                            playBoard.space[col][row] == playBoard.space[col+3][row+3] &&
                            (playBoard.space[col][row] == 1 || playBoard.space[col][row] == 2))
                    {
                        System.out.printf("Player %d wins!",playBoard.space[col][row]);
                        win = true;
                    }
                    //diag left
                    else if(playBoard.space[col][row] == playBoard.space[col-1][row+1] &&
                            playBoard.space[col][row] == playBoard.space[col-2][row+2] &&
                            playBoard.space[col][row] == playBoard.space[col-3][row+3] &&
                            (playBoard.space[col][row] == 1 || playBoard.space[col][row] == 2))
                    {
                        System.out.printf("Player %d wins!",playBoard.space[col][row]);
                        win = true;
                    }
                }
                else if(col > 3)
                {
                    //only diag left possible here
                    if(playBoard.space[col][row] == playBoard.space[col-1][row+1] &&
                        playBoard.space[col][row] == playBoard.space[col-2][row+2] &&
                        playBoard.space[col][row] == playBoard.space[col-3][row+3] &&
                        (playBoard.space[col][row] == 1 || playBoard.space[col][row] == 2))
                    {
                    System.out.printf("Player %d wins!",playBoard.space[col][row]);
                    win = true;
                    }
                }
            }
        }

    }

    /**
     * unused currently. Held for if appropriate command for console input without pressing enter is found. Can then control cursor like a menu using this one.
     */
    public void moveCursor(int movement)
    {
        setCursor(cursor + movement);
        updateScreen(false);
    }

    /**
     * updateScreen clears console and redraws with new states. Does not clear console in IDE console.
     */
    public void updateScreen(boolean flashBombStatus)
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
                System.out.print(cursorDisp + 1 + " ");
            }
        }
        System.out.print("\n");
        for(int row = 5; row >= 0; row--)
        {
            for(int column[]:playBoard.space)
            {
                switch (column[row])
                {
                    case 0:
                        System.out.print("- ");
                        break;
                    case 1:
                        System.out.print("X ");
                        break;
                    case 2:
                        System.out.print("O ");
                        break;
                    case 3:
                        System.out.print("§ ");
                        break;
                    case 9:
                        if(flashBombStatus)
                        {
                            System.out.print("4 ");
                        }
                        else
                        {
                            System.out.print("* ");
                        }
                        break;
                    case 8:
                        if(flashBombStatus)
                        {
                            System.out.print("3 ");
                        }
                        else
                        {
                            System.out.print("* ");
                        }
                        break;
                    case 7:
                        if(flashBombStatus)
                        {
                            System.out.print("2 ");
                        }
                        else
                        {
                            System.out.print("* ");
                        }
                        break;
                    case 6:
                        if(flashBombStatus)
                        {
                            System.out.print("1 ");
                        }
                        else
                        {
                            System.out.print("* ");
                        }
                        break;
                    case 5:
                        if(flashBombStatus)
                        {
                            System.out.print("0 ");
                        }
                        else
                        {
                            System.out.print("* ");
                        }
                        break;

                }

            }
            System.out.print("\n");
        }
    }


}
