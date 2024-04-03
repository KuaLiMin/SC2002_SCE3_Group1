package foms.main;
// import java.util.Scanner;

//import foms.helper.UserIO;
import foms.fileio.FileIO;
import foms.tools.OrderCleanupScheduler;
import foms.tools.ScannerCheck;
import foms.view.MainMenu;

/**
 * 
 * @author :i Min
 */
public class Foms {

    /**
     * Main function to run Food Ordering Management System (FOMS)
     */
    public static void main(String[] args){
        FileIO fileIO = new FileIO();
        OrderCleanupScheduler.startOrderCleanupTask();
        MainMenu.displayMainMenu();
        fileIO.saveData();
        ScannerCheck.close();
    }
}