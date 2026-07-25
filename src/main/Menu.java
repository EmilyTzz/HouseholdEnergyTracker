package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private static ArrayList<String> validMonths = new ArrayList<>(); // stores all the valid months

    private static ArrayList<String> menuOptions = new ArrayList<>(); // stores all the main menu options

    static{
        validMonths.addAll(Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"));
        menuOptions.addAll(Arrays.asList("Enter This Month's Energy Consumption", "View Consumption Summary",
                "Save", "Load", "Quit"));
    }

    public static void mainMenu(){
        System.out.println("--------------Household Energy Tracker--------------");

        consumptionInputMenu();
    }

    public static void consumptionInputMenu(){
        System.out.println("--------------Your Energy Consumption This Month--------------");
        String month;
        double electricity;
        double naturalGas;
        // Month Input
        while (true){
            System.out.print("Month: ");
            Scanner scanner = new Scanner(System.in);
            month = scanner.next().toUpperCase();
            if (validMonths.contains(month)) {
                break;
            }
        }
        System.out.println(month);
        // Electricity Input
        while (true){
            try{
                System.out.println("Electricity Used (kW): ");
                Scanner scanner = new Scanner(System.in);
                electricity = scanner.nextDouble();
                if (electricity >= 0){
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
        System.out.println(electricity);
        // Natural Gas Input
        while (true){
            try{
                System.out.println("Natural Gas Used (GJ): ");
                Scanner scanner = new Scanner(System.in);
                naturalGas = scanner.nextDouble();
                if (naturalGas >= 0){
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
        System.out.println(naturalGas);
    }

}
