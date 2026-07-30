package main;

import file.Reader;
import file.Writer;
import object.CarbonConvertor;
import object.Cost;
import object.Usage;
import object.UsageSorter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static ArrayList<String> validMonths = new ArrayList<>(); // stores all the valid months

    private static ArrayList<String> menuOptions = new ArrayList<>(); // stores all the main menu options

    private static ArrayList<String> summaryOptions = new ArrayList<>(); // stores all the summary options

    private static ArrayList<String> sortOptions = new ArrayList<>(); // stores all the sort options

    private static Usage usage = new Usage();


    static{
        validMonths.addAll(Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"));
        menuOptions.addAll(Arrays.asList("Enter This Month's Usage", "View Usage Summary",
                "Save", "Load", "Quit"));
        summaryOptions.addAll(Arrays.asList("Monthly Overview", "View Statistics", "View Graphs", "Back"));
        sortOptions.addAll(Arrays.asList("Highest to Lowest Cost", "Lowest to Highest Cost", "Quit"));
    }

    public static void mainMenu(){
        boolean isRunning = true;
        while (isRunning){
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
                    //System.out.println("Summary");
                    summaryMenu();
                    break;
                case 3:
                    //System.out.println("Save");
                    saveInfo();
                    break;
                case 4:
                    //System.out.println("Load");
                    loadInfo();
                    break;
                case 5:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }

        }
    }

    private static void summaryMenu(){
        boolean isRunning = true;
        while (isRunning){
            System.out.println("--------------Summary--------------");
            for (int i = 0; i < summaryOptions.size(); i ++){
                System.out.println(i+1 + ". " + summaryOptions.get(i));
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
            switch (option) {
                case 1:
                    overviewMenu();
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 3:
                    System.out.println("");
                    break;
                case 4:
                    isRunning = false;
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    private static void overviewMenu(){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        usageSorter.sortInfoAccordingToMonths();
        System.out.println("\n--------------Overview--------------");
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            System.out.println("\n----------------------------\n");
            System.out.println(usageSorter.getSortedMonths().get(i) + ":");
            estimations(usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i));
            System.out.println();
        }
        boolean isRunning = true;
        while (isRunning){
            System.out.println("Sort Options:");
            for (int i = 0; i < sortOptions.size(); i ++){
                System.out.println(i+1 + ". " + sortOptions.get(i));
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
            switch (option) {
                case 1:
                    usageSorter.sortFromHighestToLowestTotalCost();
                    for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
                        System.out.println("\n----------------------------\n");
                        System.out.println(usageSorter.getSortedMonths().get(i) + ":");
                        estimations(usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i));
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }


    private static void saveInfo(){
        Scanner scanner = new Scanner(System.in);
        String filename;
        boolean validFile = false;
        System.out.println("--------------Save Data--------------");
        while (!validFile) {
            System.out.println("Enter the name of the File you want to save to: ");
            filename = scanner.nextLine().trim();
            if (filename.isEmpty()) {
                System.out.println("\n* The name of the File cannot be empty. Please try again *");
            }
            if (!(filename.toLowerCase().endsWith(".csv"))) {
                System.out.println("* Error: File needs to end with .csv *");
            }
            File file = new File(filename);
            try {
                Writer writer = new Writer();
                UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
                writer.saveInfo(file, usageSorter);
                validFile = true;
            } catch (Exception e) {
                System.out.println("* ERROR: An unexpected error occurred while handling the file *");
            }
        }
    }

    private static void loadInfo(){
        Scanner scanner = new Scanner(System.in);
        String filename;
        boolean validFile = false;
        System.out.println("--------------Load Data--------------");
        while (!validFile) {
            System.out.println("Enter the name of the File you want to load from: ");
            filename = scanner.nextLine().trim();

            if (filename.isEmpty()){
                System.out.println("\n* ERROR: File name cannot be empty. Please try again. *");
                continue;
            }
            if (!filename.toLowerCase().endsWith(".csv")){
                System.out.println("\n* ERROR: File name must end with '.csv'. Please try again. *\"");
            }
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("\n* ERROR: That file does not exist. Please check the name and try again. *");
                continue;
            }
            Reader reader = new Reader();
            usage = new Usage();
            reader.loadInfo(file, usage);
        }
    }

    private static void consumptionInputMenu(){
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
                if (usage.getMonths().contains(month)){ // makes sure there are no duplicate months
                    System.out.println("Error: You Have Already Entered this Month's bills");
                }
                else{
                    usage.addMonth(month);
                    break;
                }
            }
        }
        //System.out.println(month);
        // Electricity Input
        while (true){
            try{
                System.out.print("Electricity Used (kWh): ");
                Scanner scanner = new Scanner(System.in);
                electricity = scanner.nextDouble();
                if (electricity >= 0){
                    usage.addElectricityUsage(electricity);
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
        //System.out.println(electricity);
        // Natural Gas Input
        while (true){
            try{
                System.out.print("Natural Gas Used (GJ): ");
                Scanner scanner = new Scanner(System.in);
                naturalGas = scanner.nextDouble();
                if (naturalGas >= 0){
                    usage.addNaturalGasUsage(naturalGas);
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
        //System.out.println(naturalGas);
        estimations(electricity, naturalGas);
        double totalEmission = (CarbonConvertor.getElectricityCarbonFootprint(electricity) + CarbonConvertor.getNaturalGasCarbonFootprint(naturalGas));
        System.out.println("💡 That is the same amount of energy required to power " + CarbonConvertor.getEquivalentOfCO2Emission(totalEmission) + " homes for a day!\n");
    }

    private static void estimations(double electricity, double naturalGas){
        System.out.println("\nEstimated Electricity Cost: $" + Cost.getElectricityCost(electricity));
        System.out.println("Estimated CO2 Emission from Electricity used: " + CarbonConvertor.getElectricityCarbonFootprint(electricity) + "kg\n");
        System.out.println("Estimated Natural Gas Cost: $" + Cost.getNaturalGasCost(naturalGas));
        System.out.println("Estimated CO2 Emission from Natural Gas used: " + CarbonConvertor.getNaturalGasCarbonFootprint(naturalGas) + "kg\n");
        System.out.println("Total cost: $" + (Cost.getTotalCost(electricity, naturalGas)));
        double totalEmission = (CarbonConvertor.getElectricityCarbonFootprint(electricity) + CarbonConvertor.getNaturalGasCarbonFootprint(naturalGas));
        System.out.println("Total CO2 Emission: " + totalEmission + "kg");
    }

}
