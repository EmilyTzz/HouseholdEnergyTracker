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
        for (int i = 0; i < menuOptions.size(); i++){ // list out all menu options
            System.out.println(i+1 + ". " + menuOptions.get(i));
        }
        int option;
        while (true){ // check if the option the user chooses even exist
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("Option: ");
                option = scanner.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Invalid Option");
            }
        }
        switch (option){
            case 1:
                consumptionInputMenu();
                break;
            case 2:
                System.out.println("Summary");
                break;
            case 3:
                System.out.println("Save");
                break;
            case 4:
                System.out.println("Load");
                break;
            case 5:
                System.out.println("Quit");
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }

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
