package org.example;

class Board
{
    public int[][] space = new int[7][6];
}
public class ConnectFour {
    public Board playBoard;

    public int[][] getSpaces()
    {
        return playBoard.space;
    }

    /**
     * Constructor for Connect Four. Builds a playBoard using 0 values - representing empty spaces.
     * Could have used a multi-dimensional array of chars I suppose, but numbers work fine as is. I like ints.
     */
    public ConnectFour() {
        int[] column ={0,0,0,0,0,0};
        int[][] rows = {column,column,column,column,column,column,column};
        this.playBoard = new Board();
        this.playBoard.space = rows;
    }
}
