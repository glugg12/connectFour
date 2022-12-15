package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectFour conFour = new ConnectFour();
        conFour.startGame();
        boolean playOnceMore = true;
        while(playOnceMore)
        {
            System.out.print("Play again? Y/N > ");
            String input = scan.nextLine();
            if(input.equals("Y") || input.equals("y") )
            {
                conFour = new ConnectFour();
                conFour.startGame();
            }
            else if(input.equals("N") || input.equals("n"))
            {
                playOnceMore = false;
            }
            else
            {
                System.out.print("Invalid input\n");
            }
        }
    }
}