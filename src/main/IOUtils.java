package main;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/**
 * Class that contains methods to read a CSV file and a properties file.
 * You may edit this as you wish.
 */
public class IOUtils {
    /**
     * Method that reads a CSV file and return a list of strings
     * @param csvFile: the path to the CSV file
     * @return List of strings from the input CSV file
     */
    public static ArrayList<String[]> readCsv(String csvFile) {
        /**
         * Heavily inspired by:
         * Le, Bach. (2024, Mar 19). Input and Output. SWEN20003 Object Oriented Software Development
         * [PowerPoint slides]. University of Melbourne.
         * https://canvas.lms.unimelb.edu.au/courses/189021/pages/lectures-4?module_item_id=5533359
         */
        try (Scanner csvReader = new Scanner(new FileReader(csvFile))) {
            ArrayList<String[]> data = new ArrayList<>();
            while (csvReader.hasNextLine()) {
                data.add(csvReader.nextLine().split(","));
            }
            return data;
        }
        catch (Exception e) {
            System.out.println("Invalid CSV file read: " + e);
            return null;
        }
    }

    /**
     * Method that reads a properties file and return a Properties object.
     * @param configFile: the path to the properties file.
     * @return Properties object.
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }

}