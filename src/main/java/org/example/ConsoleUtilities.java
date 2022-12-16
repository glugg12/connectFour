package org.example;

import java.io.IOException;

//Building console utilities here. Seemed like fun to build out to a jar.
public class ConsoleUtilities {
    /**
     * Clears the console in a jar output. Does not work inside the IDE.
     */
    public static void clearConsole()
    {

        //not my discovery, dug around the net for this stuff
        //runs clear command for windows console, otherwise runs something I forget the name of. Ansii escape?
        //pulled from stackoverflow iirc? need to find that link again
        try {

            if (System.getProperty("os.name").contains("Windows")) {

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

        } catch (IOException | InterruptedException ex) {}
    }
}
