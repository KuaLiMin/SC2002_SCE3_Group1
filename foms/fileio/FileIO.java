package foms.fileio;

import foms.models.Branch;
import foms.models.Employee;
import foms.models.MenuItem;
import foms.models.Staff;
import foms.models.Admin;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Iterator;

// Li Min

public class FileIO {
    private static final String PERSISTENT_DATA_FOLDER = "/path/to/persistentdata";
    private static final String ORIGINAL_FILES_BRANCH = "foms/originalfiles/branch_list.csv";
    private static final String ORIGINAL_FILES_MENU = "foms/originalfiles/menu_list.csv";
    private static final String ORIGINAL_FILES_STAFF = "foms/originalfiles/staff_list.csv";
    
    private static ArrayList<Branch> branchList = new ArrayList<>();
    private static ArrayList<Employee> employeeList = new ArrayList<>();




    // getbranchlist method
    public static ArrayList<Branch> getBranchList() {
        return branchList;
    }
    // get employeelist method
    public static ArrayList<Employee> getEmployeeList() {
        return employeeList;
    } 


    public FileIO() {
        // Create persistent data folder if it does not exist
        // try {
        //     Files.createDirectories(Paths.get(PERSISTENT_DATA_FOLDER));
        // } catch (IOException e) {
        //     System.err.println("Error creating persistent data folder");
        //     e.printStackTrace();
        // }

        // Load original files
        loadoriginalfiles();
    }



    // original files
    public static void loadoriginalfiles() {
        try{
            // create an empty arraylist of menu items
            ArrayList<MenuItem> menuList = new ArrayList<>();


            // Load menu list
            BufferedReader br = new BufferedReader(new FileReader(ORIGINAL_FILES_MENU));
            br.readLine(); // Skip the first line
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                MenuItem menuItem = new MenuItem(values[0], Double.parseDouble(values[1]), values[2], values[3]);
                // add menu item to menu list
                menuList.add(menuItem);
            }
            br.close();

            System.err.println("Menu list size: " + menuList.size());


            // Load branch list
            br = new BufferedReader(new FileReader(ORIGINAL_FILES_BRANCH));
            br.readLine(); // Skip the first line

            // Check if the file is empty
            if (br.readLine() == null) {
                System.err.println("Branch file is empty.");
                br.close();
                return;
            }

            // Reset the reader to the beginning of the file
            br = new BufferedReader(new FileReader(ORIGINAL_FILES_BRANCH));
            br.readLine(); // Skip the first line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Branch branch = new Branch(values[0], values[1], Integer.parseInt(values[2]));
                // add menu items to branch
                Iterator<MenuItem> iterator = menuList.iterator();
                while (iterator.hasNext()) {
                    MenuItem menuItem = iterator.next();
                    if (menuItem.getBranch().equals(branch.getName())) {
                        branch.addMenuItem(menuItem);
                    }
                }
                branchList.add(branch);
            }
            br.close();
            System.err.println("Branch list size: " + branchList.size());

            // Load staff list
            br = new BufferedReader(new FileReader(ORIGINAL_FILES_STAFF));
            br.readLine(); // Skip the first line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values[2].equals("A")) {
                    Admin admin = new Admin(values[2], values[0], values[3], Integer.parseInt(values[4]), values[1]);
                    employeeList.add(admin);
                }else{
                    Staff staff = new Staff(values[2], values[0], values[3], Integer.parseInt(values[4]), values[1], values[5]);
                    employeeList.add(staff);
                }
            }
            br.close();

            System.err.println("Staff list size: " + employeeList.size());


        } catch (IOException e) {
            System.err.println("Error loading original files");
            e.printStackTrace();
        }
    }


}




