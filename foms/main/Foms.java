package foms.main;
//import foms.helper.UserIO;
import foms.fileio.FileIO;
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
        MainMenu.displayMainMenu();
        // WRITE ALL CHANGES TO DATABASE
        // UserIO.close(); // CLOSES SCANNER
    }
}