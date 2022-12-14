package org.example;

import java.io.IOException;

//Building console utilities here. Seemed like fun to build out to a jar.
public class ConsoleUtilities {
    /**
     * Clears the console in a jar output. Does not work inside the IDE.
     */
    public static void clearConsole()
    {
//        try
//        {
//            final String os = System.getProperty("os.name");
//            if (os.contains("Windows"))
//            {
//                Runtime.getRuntime().exec("cls");
//            }
//            else
//            {
//                System.out.print("\033[H\033[2J");
//                System.out.flush();
//            }
//        }
//        catch (IOException e)
//        {
//            System.out.println(e.getMessage());
//        }

        //todo: see if this works on mac side at some point
        try {

            if (System.getProperty("os.name").contains("Windows")) {

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                Runtime.getRuntime().exec("clear");
            }

        } catch (IOException | InterruptedException ex) {}
    }
}
