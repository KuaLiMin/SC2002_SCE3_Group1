package foms.fileio;

import foms.models.Branch;
import foms.models.Employee;
import foms.models.MenuItem;
import foms.models.Staff;
import foms.models.Admin;
import foms.models.Manager;
import foms.models.Order;
import foms.models.Payment;
import foms.enums.UserRole;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;



/**
 * This class provides file input/output operations for the FOMS application.
 * It allows reading and writing Employee objects to/from the save files.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class FileIO {
    /**
     * The path to the folder where persistent data is stored.
     */
    private static final String PERSISTENT_DATA_FOLDER = "foms/persistentdata/";

    /**
     * The path to the original file containing the list of branches.
     */
    private static final String ORIGINAL_FILES_BRANCH = "foms/originalfiles/branch_list.csv";

    /**
     * The path to the original file containing the list of menu items.
     */
    private static final String ORIGINAL_FILES_MENU = "foms/originalfiles/menu_list.csv";

    /**
     * The path to the original file containing the list of staff.
     */
    private static final String ORIGINAL_FILES_STAFF = "foms/originalfiles/staff_list.csv";
    
    /**
     * The list of Branch objects loaded from the persistent data.
     */
    private static ArrayList<Branch> branchList = new ArrayList<>();

    /**
     * The list of Employee objects loaded from the persistent data.
     */
    private static ArrayList<Employee> employeeList = new ArrayList<>();

    /**
     * The list of Order objects loaded from the persistent data.
     */
    private static ArrayList<Order> orderList = new ArrayList<>();

    /**
     * Gets the list of branches.
     * 
     * @return The list of branches.
     */
    public static ArrayList<Branch> getBranchList() {
        return branchList;
    }

    /**
     * Gets the list of employees.
     * 
     * @return The list of employees.
     */    
    public static ArrayList<Employee> getEmployeeList() {
        return employeeList;
    } 

    /**
     * Gets the list of orders.
     * 
     * @return The list of orders.
     */
    public static ArrayList<Order> getOrderList() {
        return orderList;
    }

    /**
     * Constructs a new FileIO object.
     * This constructor loads the branch list (along with payment list), employee list, and order list from the save files.
     */
    public FileIO() {
        

        try {
            branchList = (ArrayList<Branch>) deserializeObject("branchList.ser");
            // Deserialize employeeList
            employeeList = (ArrayList<Employee>) deserializeObject("employeeList.ser");
            // Deserialize orderList
            orderList = (ArrayList<Order>) deserializeObject("orderList.ser");

            if(branchList == null || employeeList == null || orderList == null) {
                throw new Exception("No valid saves detected.");
            }else{
                Branch.paymentList = (ArrayList<Payment>) deserializeObject("paymentList.ser");
                if(Branch.paymentList == null){
                    throw new Exception("No valid saves detected.");
                }
            }           


        } catch (Exception e) {
            System.err.println("Loading original files...");
            branchList = new ArrayList<Branch>();
            employeeList = new ArrayList<Employee>();
            orderList = new ArrayList<Order>();
            loadoriginalfiles();
        }
    }

    /**
     * Serializes an object and saves it to a file.
     * 
     * @param fileName The name of the file to save the object to.
     * @param object The object to be serialized and saved.
     */
    public static void serializeObject(String fileName, Serializable object) {
        String filePath = PERSISTENT_DATA_FOLDER + fileName;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
             FileOutputStream fos = new FileOutputStream(filePath)) {

            oos.writeObject(object);
            oos.flush();

            // Convert ByteArrayOutputStream to byte array
            byte[] byteArray = baos.toByteArray();

            // Encode byte array to Base64 string
            String base64String = Base64.getEncoder().encodeToString(byteArray);

            // Write Base64 string to file (optional)
            fos.write(base64String.getBytes());
            fos.flush();

            System.out.println("Object serialized and saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Deserializes an object from a file.
     * 
     * @param fileName The name of the file to read the object from.
     * @return The deserialized object.
     * @throws Exception If an error occurs during deserialization.
     */
    public static Object deserializeObject(String fileName) throws Exception {
        String filePath = PERSISTENT_DATA_FOLDER + fileName;

        try (FileInputStream fis = new FileInputStream(filePath)) {

            // Read Base64 string from file
            byte[] encodedBytes = fis.readAllBytes();
            String base64String = new String(encodedBytes);

            // Decode Base64 string to byte array
            byte[] byteArray = Base64.getDecoder().decode(base64String);

            // Deserialize byte array to object
            try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {

                Object object = ois.readObject();
                System.out.println("Object deserialized from " + filePath);
                return object;
            } catch (ClassNotFoundException e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the original files (branch list, menu list, and staff list) into the application.
     */
    private static void loadoriginalfiles() {
        try{
            // create an empty arraylist of menu items
            ArrayList<MenuItem> menuList = new ArrayList<>();


            // Load menu list
            BufferedReader br = new BufferedReader(new FileReader(ORIGINAL_FILES_MENU));
            br.readLine(); // Skip the first line
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String description = ""; 
                boolean availability = true;
                MenuItem menuItem = new MenuItem(values[0], Double.parseDouble(values[1]), values[2], values[3], description, availability);
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
                // print line
                String[] values = line.split(",");
                Branch branch = new Branch(values[0], values[1],Integer.parseInt(values[2]));
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


                if (UserRole.valueOf(values[2]) == UserRole.A) {
                    Admin admin = new Admin("A", values[0], values[3], Integer.parseInt(values[4]),values[1], null);
                    employeeList.add(admin);
                } else if (UserRole.valueOf(values[2]) == UserRole.M) {
                    Manager manager = new Manager("M", values[0], values[3], Integer.parseInt(values[4]), values[1], "");
                    manager.setBranch(values[5]);
                    employeeList.add(manager);
                } else if (UserRole.valueOf(values[2]) == UserRole.S){
                    Staff staff = new Staff("S", values[0], values[3], Integer.parseInt(values[4]), values[1], "");
                    staff.setBranch(values[5]);
                    employeeList.add(staff);
                }
                else{
                    System.err.println("Invalid role, skipping employee record...");
                }
            }
            br.close();

            System.err.println("Staff list size: " + employeeList.size());
        

        } catch (IOException e) {
            System.err.println("Error loading original files");
            e.printStackTrace();
        }


    }
    
    /**
     * Saves the branch list, payment list, employee list, and order list to the save files.
     */
    public static void saveData() {
            // Create persistent data folder if it does not exist
            if (!Files.exists(Paths.get(PERSISTENT_DATA_FOLDER))) {
                try {
                    Files.createDirectories(Paths.get(PERSISTENT_DATA_FOLDER));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // delete all files in the persistent data folder
            File folder = new File(PERSISTENT_DATA_FOLDER);

            for (File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }

            // Serialize branchList
            serializeObject("branchList.ser", branchList);
            // Serialize paymentList
            serializeObject("paymentList.ser", Branch.getPaymentList());
            // Serialize employeeList
            serializeObject("employeeList.ser", employeeList);
            // Serialize orderList
            serializeObject("orderList.ser", orderList);
            
    }


}




