package org.example;
//Building console utilities here. Seemed like fun to build out to a jar.
public class ConsoleUtilities {
    /**
     * Clears the console in a jar output. Does not work inside the IDE.
     */
    public static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
