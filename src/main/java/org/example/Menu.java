package org.example;
import java.util.*;
//Oli's super awesome menu related classes
//Probably over the top? It was fun to write though.

/**
 * A Menu object requires a "greeting" string to show at the top of the menu.
 * It also requires to have a List of Option objects which are all printed after the menu greeting
 * A Menu also holds a runnable "runMenu" which will run the menu's main function on execution with .Run().
 *
 * -Oli
 */
public class Menu
{
    private Scanner menuScanner = new Scanner(System.in);
    //store the menu text
    String myMessage;
    //store the options
    List<Option> optionList;
    public void printMenu()
    {
        //print the menu and the options, and add a number to the options
        System.out.println();
        System.out.println(myMessage);
        int optionCount = 0;
        if(optionList != null)
        {
            for(Option e : optionList)
            {
                optionCount++;
                System.out.println(optionCount + ") "+ e.text);

            }
            //get an option input
            getInputFromUser();
        }

    }

    //hold a menus "entry point" as a runnable, so that Option objects can call other menus
    Runnable runMenu = this::printMenu;
    public void getInputFromUser()
    {
        boolean menuChosen = false;
        //take input and validate it
        //cheeky validation for menu option range - if an input int is not an option,
        //it'll throw out of bounds error on the list call, rather than needing to store the number of options.
        //Might be some other stuff you could do with menus would need the number of options, then would probs switch to a list there

        while(!menuChosen)
        {
            int chosen = -1;
            String input = menuScanner.nextLine();
            try
            {
                chosen = Integer.parseInt(input);
            }
            catch (Exception e)
            {
                System.out.println("Invalid Input.");
            }
            if(chosen!=-1)
            {
                try
                {
                    optionList.get(chosen - 1).menuOutcome.run();
                    menuChosen = true;
                }
                catch (Exception e)
                {
                    System.out.println("Invalid Input.");
                }
            }

        }
    }
    //set the optoins list
    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
    //constructors.
    public Menu(String message) {
        myMessage = message;
    }

    public Menu(String message, List<Option> options)
    {
        myMessage = message;
        setOptionList(options);
    }


}