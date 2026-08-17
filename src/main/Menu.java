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

    public static final String TOTAL_COST = "Total Cost";

    public static final String ELECTRICITY_COST = "Electricity Cost";

    public static final String NATURAL_GAS_COST = "Natural Gas Cost";
    
    public static final String KEEP_CURRENT_PRICES = "Y";
    
    public static final String DONT_KEEP_CURRENT_PRICES = "N";

    public static ArrayList<String> validMonths = new ArrayList<>(); // stores all the valid months

    private static final ArrayList<String> menuOptions = new ArrayList<>(); // stores all the main menu options

    private static final ArrayList<String> summaryOptions = new ArrayList<>(); // stores all the summary options

    private static final ArrayList<String> sortOptions = new ArrayList<>(); // stores all the sort options

    private static Usage usage = new Usage();

    private final static Cost cost = new Cost();

    private final static CarbonConvertor carbonConvertor = new CarbonConvertor();


    static{
        validMonths.addAll(Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"));
        menuOptions.addAll(Arrays.asList("Enter This Month's Usage", "View Usage Summary", "Edit Price/kwH or Price/GJ",
                "Save", "Load", "Quit"));
        summaryOptions.addAll(Arrays.asList("Monthly Overview", "View Statistics", "View Graphs", "Back"));
        sortOptions.addAll(Arrays.asList("Highest to Lowest Total Cost", "Lowest to Highest Total Cost", "Highest to Lowest Electricity Cost",
                "Lowest to Highest Electricity Cost", "Highest to Lowest Natural Gas Cost", "Lowest to Highest Natural Gas Cost", "Quit"));
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
                    System.out.println("--------------Set Costs--------------");
                    cost.setCostPerKwH(setCostPerKwHHelper());
                    cost.setCostPerGJ(setCostPerGJHelper());
                    break;
                case 4:
                    saveInfo();
                    break;
                case 5:
                    loadInfo();
                    break;
                case 6:
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
                    viewStatistics();
                    break;
                case 3:
                    //System.out.println("");
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    private static void viewStatistics(){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        usageSorter.sortFromHighestToLowest(TOTAL_COST, cost);
        System.out.println("\n--------------Statistics--------------");
        System.out.println("Average Usage Cost : $" + cost.getAverageCost(usage.getElectricityUsed(), usage.getNaturalGasUsed()));
        System.out.println("Average Usage Emissions : " + carbonConvertor.getAverageEmission(usage.getElectricityUsed(), usage.getNaturalGasUsed()) + " kg");
        System.out.println("\nCost Percentage of Each Month");
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            System.out.println(usageSorter.getSortedMonths().get(i) + ": " + cost.getMonthlyCostPercentage(usageSorter.getSortedMonths().get(i), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed()) + " %");
        }
        System.out.println("\nEmission Percentage of Each Month");
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            System.out.println(usageSorter.getSortedMonths().get(i) + ": " + carbonConvertor.getMonthlyEmissionPercentage(usageSorter.getSortedMonths().get(i), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed()) + " %");
        }
        boolean isRunning = true;
        while (isRunning){
            System.out.println("\nSort Options:");
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
                    usageSorter.sortFromHighestToLowest(TOTAL_COST, cost);
                    sortedDataDisplayHelper(usageSorter);
                    break;
                case 2:
                    usageSorter.sortFromLowestToHighestTotalCost(cost);
                    sortedDataDisplayHelper(usageSorter);
                    break;
                case 3:
                    usageSorter.sortFromHighestToLowest(ELECTRICITY_COST, cost);
                    sortedDataDisplayHelper(usageSorter);
                    break;
                case 4:
                    usageSorter.sortFromLowestToHighestElectricityCost(cost);
                    sortedDataDisplayHelper(usageSorter);
                    break;
                case 5:
                    usageSorter.sortFromHighestToLowest(NATURAL_GAS_COST, cost);
                    sortedDataDisplayHelper(usageSorter);
                    break;
                case 6:
                    usageSorter.sortFromLowestToHighestNaturalGasCost(cost);
                    sortedDataDisplayHelper(usageSorter);
                    break;
                case 7:
                    isRunning = false;
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
        if (usage.getMonths().isEmpty()){
            System.out.println("No Data Here Yet...");
        }
        else{
            sortedDataDisplayHelper(usageSorter);
        }

    }

    private static void sortedDataDisplayHelper(UsageSorter usageSorter){
        for (int i = 0; i < usageSorter.getSortedMonths().size(); i ++){
            System.out.println("\n----------------------------\n");
            System.out.println(usageSorter.getSortedMonths().get(i) + ":");
            estimations(usageSorter.getSortedElectricityUsed().get(i), usageSorter.getSortedNaturalGasUsed().get(i));
            System.out.println();
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
                writer.saveInfo(file, usageSorter, cost);
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
        boolean validAnswer = false;
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
            String answer = "";
            scanner = new Scanner(System.in);
            while (!validAnswer){
                System.out.println("\n*Do you want to still use the current electricity/natural gas price? (Y/N)");
                answer = scanner.nextLine().toUpperCase();
                if (answer.equals("Y")){
                    answer = "Y";
                    validAnswer = true;
                }
                else if (answer.equals("N")){
                    answer = "N";
                    validAnswer = true;
                }
            }
            Reader reader = new Reader();
            usage = new Usage();
            reader.loadInfo(file, usage, cost, answer);
            validFile = true;
        }
    }

    private static void consumptionInputMenu(){
        System.out.println("--------------Your Energy Consumption This Month--------------");
        if (cost.getCostPerKwH() == 0 && cost.getCostPerGJ() == 0){
            cost.setCostPerKwH(setCostPerKwHHelper());
            cost.setCostPerGJ(setCostPerGJHelper());
        }
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
        estimations(electricity, naturalGas);
        double totalEmission = (carbonConvertor.getElectricityCarbonFootprint(electricity) + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas));
        System.out.println("\n💡 That is the same amount of energy required to power " + carbonConvertor.getEquivalentOfCO2Emission(totalEmission) + " homes for a day!");
        System.out.println("...");
        compareCostFromLastMonth(month);
        compareCarbonEmissionFromLastMonth(month);

    }

    public static double setCostPerKwHHelper(){
        double costPerKwH;
        while (true){
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("Price per KwH: ");
                costPerKwH = scanner.nextDouble();
                break;
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
        return costPerKwH;
    }

    public static double setCostPerGJHelper(){
        double costPerGJ;
        while (true){
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("Price per GJ: ");
                costPerGJ = scanner.nextDouble();
                break;
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
        return costPerGJ;
    }

    private static void compareCostFromLastMonth(String monthEntered){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        usageSorter.sortInfoAccordingToMonths();
        if (!usageSorter.getSortedMonths().getFirst().equals(monthEntered)){
            int indexOfMonthBefore = usageSorter.getSortedMonths().indexOf(monthEntered)-1;
            double costDiff = cost.getPercentageDiffInCostFromLastMonth(monthEntered, usageSorter.getSortedMonths().get(indexOfMonthBefore), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed());
            if (costDiff < 0){
                System.out.println("The costs for this month decreased by " + costDiff*(-1) + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else if (costDiff > 0){
                System.out.println("The costs for this month increased by " + costDiff + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else{
                System.out.println("The cost for this month has not changed compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore) + "\n");
            }
        }
    }

    private static void compareCarbonEmissionFromLastMonth(String monthEntered){
        UsageSorter usageSorter = new UsageSorter(usage.getMonths(), usage.getElectricityUsed(), usage.getNaturalGasUsed());
        usageSorter.sortInfoAccordingToMonths();
        if (!usageSorter.getSortedMonths().getFirst().equals(monthEntered)){
            int indexOfMonthBefore = usageSorter.getSortedMonths().indexOf(monthEntered)-1;
            double carbonDiff = carbonConvertor.getPercentageDiffInCarbonFromLastMonth(monthEntered, usageSorter.getSortedMonths().get(indexOfMonthBefore), usageSorter.getSortedMonths(), usageSorter.getSortedElectricityUsed(), usageSorter.getSortedNaturalGasUsed());
            if (carbonDiff < 0){
                System.out.println("The carbon emission for this month decreased by " + carbonDiff*(-1) + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else if (carbonDiff > 0){
                System.out.println("The carbon emission for this month increased by " + carbonDiff + "% compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore)+ "\n");
            }
            else{
                System.out.println("The carbon emission for this month has not changed compared to " + usageSorter.getSortedMonths().get(indexOfMonthBefore) + "\n");
            }
        }
    }

    private static void estimations(double electricity, double naturalGas){
        System.out.println("\nEstimated Electricity Cost: $" + cost.getElectricityCost(electricity));
        System.out.println("Estimated CO2 Emission from Electricity used: " + carbonConvertor.getElectricityCarbonFootprint(electricity) + "kg\n");
        System.out.println("Estimated Natural Gas Cost: $" + cost.getNaturalGasCost(naturalGas));
        System.out.println("Estimated CO2 Emission from Natural Gas used: " + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas) + "kg\n");
        System.out.println("Total cost: $" + (cost.getTotalCost(electricity, naturalGas)));
        double totalEmission = (carbonConvertor.getElectricityCarbonFootprint(electricity) + carbonConvertor.getNaturalGasCarbonFootprint(naturalGas));
        System.out.println("Total CO2 Emission: " + totalEmission + " kg");
    }

}
