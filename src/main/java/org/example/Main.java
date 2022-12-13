package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Hello world!");
        ConsoleUtilities.clearConsole();
        System.out.print("Flushed!");
        scan.nextLine();
    }
}