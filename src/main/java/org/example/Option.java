package org.example;

/**
 * An option menu holds a text string to show when it's in a menu, and a "menuOutcome".
 * menuOutcome is a runnable, which can than be called to run by a menu if the option is selected.
 *
 * -Oli
 */
public class Option {
    //the text to be shown for the option
    String text;
    //the function which should be run when this option is selected
    Runnable menuOutcome;
    //constructor
    public Option(Runnable menuOutcome, String text) {
        this.menuOutcome = menuOutcome;
        this.text = text;
    }
}
