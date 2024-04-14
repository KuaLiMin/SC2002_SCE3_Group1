package foms.main;

import foms.fileio.FileIO;
import foms.tools.OrderCleanupScheduler;
import foms.tools.ScannerCheck;
import foms.view.MainMenu;

/**
 * Represents the Food Ordering Management System (FOMS).
 * The FOMS class contains the main function to run the Food Ordering Management System.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class Foms {

    /**
     * The main method of the application.
     * Initializes the {@code FileIO} object to manage data loading and saving.
     * Starts the order cleanup task using {@code OrderCleanupScheduler}.
     * Displays the main menu using {@code MainMenu}.
     * Saves the data using the {@code saveData} method of {@code FileIO}.
     * Closes the {@code Scanner} instance using {@code ScannerCheck}.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args){
        FileIO fileIO = new FileIO();
        OrderCleanupScheduler.startOrderCleanupTask();
        MainMenu.displayMainMenu();
        fileIO.saveData();
        ScannerCheck.close();
    }
}