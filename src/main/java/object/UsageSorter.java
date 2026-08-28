package object;

import main.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class helps to sort the lists of months, electricity usage, natural gas usage in different sorting orders
 */
public class UsageSorter {

    public static List<String> months;

    public static List<Double> electricityUsed;

    public static List<Double> naturalGasUsed;

    public static List<Double> sortedTotalCost;

    public static List<Double> sortedElectricityCost;

    public static List<Double> sortedEmission;

    public static List<Double> sortedNaturalGasCost;

    /**
     * This method initializes the current list of months, electricity usage, and natural gas usage from the usage object
     * to be sorted
     *
     * @param months list of months
     * @param electricityUsed list of electricity usage
     * @param naturalGasUsed list of natural gas usage
     */
    public UsageSorter(List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        this.months = months;
        this.electricityUsed = electricityUsed;
        this.naturalGasUsed = naturalGasUsed;
        this.sortedTotalCost = new ArrayList<>();
        this.sortedElectricityCost = new ArrayList<>(); // initializes list of sorted electricity costs
        this.sortedNaturalGasCost = new ArrayList<>(); // initializes list of sorted natural gas costs
        this.sortedEmission = new ArrayList<>(); // initializes list of sorted total emission
    }

    /**
     * This method sorts the months and its usages in the traditional January - December order
     */
    public void sortInfoAccordingToMonths(){
        List<String> sortedMonths = new ArrayList<>();
        List<Double> sortedElectricityUsed = new ArrayList<>();
        List<Double> sortedNaturalGasUsed = new ArrayList<>();
        for (int i = 0; i < Menu.validMonths.size(); i++){ // makes sure the months are in order
            if (months.contains(Menu.validMonths.get(i))){
                sorterHelper(sortedMonths, sortedElectricityUsed, sortedNaturalGasUsed, Menu.validMonths.get(i));
            }
        }
        months = sortedMonths;
        electricityUsed = sortedElectricityUsed;
        naturalGasUsed = sortedNaturalGasUsed;
    }

    /**
     * This method adds the month and its electricity and natural gas usage to the same order
     * @param sortedMonths list to add the sorted months
     * @param sortedElectricityUsed list to add the sorted electricity usage
     * @param sortedNaturalGasUsed list to add the sorted natural gas usage
     * @param month month to help get the index of its usages
     */
    private void sorterHelper(List<String> sortedMonths, List<Double> sortedElectricityUsed, List<Double> sortedNaturalGasUsed, String month){
        for (int i = 0; i < months.size(); i ++){
            if (months.get(i).equals(month)){ // iterate through all the months to find the index of the month we want to add to the sorted lists
                sortedMonths.add(months.get(i));
                sortedElectricityUsed.add(electricityUsed.get(i));
                sortedNaturalGasUsed.add(naturalGasUsed.get(i));
            }
        }
    }

    /**
     * This method reverses the highest to lowest monthly costs order
     * @param cost cost object
     */
    public void sortFromLowestToHighestTotalCost(Cost cost){
        sortFromHighestToLowest(Menu.TOTAL_COST, cost, null);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
        Collections.reverse(sortedTotalCost);
    }

    /**
     * Reverses the highest to lowest monthly emission order
     * @param carbonConvertor carbon convertor object
     */
    public void sortFromLowestToHighestTotalEmission(CarbonConvertor carbonConvertor){
        sortFromHighestToLowest(Menu.TOTAL_EMISSION, null, carbonConvertor);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
        Collections.reverse(sortedEmission);
    }

    /**
     * Reverses the highest to lowest monthly electricity costs order
     * @param cost cost object
     */
    public void sortFromLowestToHighestElectricityCost(Cost cost){
        sortFromHighestToLowest(Menu.ELECTRICITY_COST, cost, null);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
        Collections.reverse(sortedElectricityCost);
    }

    /**
     * Reverses the highest to lowest monthly natural gas costs order
     * @param cost cost object
     */
    public void sortFromLowestToHighestNaturalGasCost(Cost cost){
        sortFromHighestToLowest(Menu.NATURAL_GAS_COST, cost, null);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
        Collections.reverse(sortedNaturalGasCost);
    }

    /**
     * This method helps to sort from highest to lowest total cost or electricity cost or natural gas cost or total emission %
     * @param energyData the type of energy data we are sorting the data in
     * @param cost cost object
     * @param carbonConvertor carbon convertor object
     */
    public void sortFromHighestToLowest(String energyData, Cost cost, CarbonConvertor carbonConvertor){
        // Creates empty lists to store the sorted data in
        List<String> sortedMonths = new ArrayList<>();
        List<Double> sortedElectricityUsed = new ArrayList<>();
        List<Double> sortedNaturalGasUsed = new ArrayList<>();
        // Clear the current sorted costs/emissions lists
        sortedTotalCost.clear();
        sortedElectricityCost.clear();
        sortedNaturalGasCost.clear();
        sortedEmission.clear();
        while (!months.isEmpty()){ // go through all the elements in each data list
            int indexOfMonthWithHighestEnergyData = 0; // set the current index with the highest value to be the 1st
            if (energyData.equals(Menu.TOTAL_COST)) {
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestTotalCostHelper(cost); // finds the index with the highest value and add to the list one by one
                sortedTotalCost.add(cost.getTotalCost(electricityUsed.get(indexOfMonthWithHighestEnergyData), naturalGasUsed.get(indexOfMonthWithHighestEnergyData)));
            }
            else if (energyData.equals(Menu.TOTAL_EMISSION)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestTotalEmissionHelper(carbonConvertor);
                sortedEmission.add(carbonConvertor.getTotalCarbonEmission(electricityUsed.get(indexOfMonthWithHighestEnergyData), naturalGasUsed.get(indexOfMonthWithHighestEnergyData)));
            }
            else if (energyData.equals(Menu.ELECTRICITY_COST)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestElectricityHelper();
                sortedElectricityCost.add(cost.getElectricityCost(electricityUsed.get(indexOfMonthWithHighestEnergyData)));
            }
            else if (energyData.equals(Menu.NATURAL_GAS_COST)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestNaturalGasHelper();
                sortedNaturalGasCost.add(cost.getNaturalGasCost(naturalGasUsed.get(indexOfMonthWithHighestEnergyData)));
            }
            // add the highest value index to each of the usage list
            sortedMonths.add(months.get(indexOfMonthWithHighestEnergyData));
            sortedElectricityUsed.add(electricityUsed.get(indexOfMonthWithHighestEnergyData));
            sortedNaturalGasUsed.add(naturalGasUsed.get(indexOfMonthWithHighestEnergyData));
            // remove the index that just got added to the sorted list, so we can keep iterating through the usage list and find the next highest element
            months.remove(months.get(indexOfMonthWithHighestEnergyData));
            electricityUsed.remove(electricityUsed.get(indexOfMonthWithHighestEnergyData));
            naturalGasUsed.remove(naturalGasUsed.get(indexOfMonthWithHighestEnergyData));
        }
        // set the usage lists of the usage sorter object to the sorted ones
        months = sortedMonths;
        electricityUsed = sortedElectricityUsed;
        naturalGasUsed = sortedNaturalGasUsed;
    }

    /**
     * This method finds the month with the highest total cost to add to the new list one by one
     * @param cost cost object
     * @return the index with the highest total cost
     */
    private int sortFromHighestToLowestTotalCostHelper(Cost cost){
        int indexOfMonthWithHighestTotalCost = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size(); i++){ // finds the month with the highest total cost and get its index
                if (cost.getTotalCost(electricityUsed.get(indexOfMonthWithHighestTotalCost), naturalGasUsed.get(indexOfMonthWithHighestTotalCost))<cost.getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i))){
                    indexOfMonthWithHighestTotalCost = i;
                }

            }
        }
        return indexOfMonthWithHighestTotalCost;
    }

    /**
     * This method finds the month with the highest emission to add to the new list one by one
     * @param carbonConvertor carbon convertor object
     * @return index of the month with the highest emission
     */
    private int sortFromHighestToLowestTotalEmissionHelper(CarbonConvertor carbonConvertor){
        int indexOfMonthWithHighestEmission = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size(); i++){
                if (carbonConvertor.getTotalCarbonEmission(electricityUsed.get(indexOfMonthWithHighestEmission), naturalGasUsed.get(indexOfMonthWithHighestEmission)) < carbonConvertor.getTotalCarbonEmission(electricityUsed.get(i), naturalGasUsed.get(i))){
                    indexOfMonthWithHighestEmission = i;
                }

            }
        }
        return indexOfMonthWithHighestEmission;
    }

    /**
     * This method finds the month with the highest electricity cost to add to the new list one by one
     * @return the index with the highest electricity used
     */
    private int sortFromHighestToLowestElectricityHelper(){
        int indexOfMonthWithHighestElectricity = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size(); i++){
                if (electricityUsed.get(indexOfMonthWithHighestElectricity)<electricityUsed.get(i)){
                    indexOfMonthWithHighestElectricity = i;
                }

            }
        }
        return indexOfMonthWithHighestElectricity;
    }

    /**
     * This method finds the month with the highest natural gas cost to add to the new list one by one
     * @return the index with the highest natural gas used
     */
    private int sortFromHighestToLowestNaturalGasHelper(){
        int indexOfMonthWithHighestNaturalGas = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size(); i++){
                if (naturalGasUsed.get(indexOfMonthWithHighestNaturalGas)<naturalGasUsed.get(i)){
                    indexOfMonthWithHighestNaturalGas = i;
                }

            }
        }
        return indexOfMonthWithHighestNaturalGas;
    }

    /**
     * This method returns the sorted list of months
     * @return sorted list of months
     */
    public List<String> getSortedMonths() {
        return new ArrayList<>(months);
    }

    /**
     * This method returns the sorted list of electricity usage
     * @return sorted list of electricity usage
     */
    public List<Double> getSortedElectricityUsed() {
        return new ArrayList<>(electricityUsed);
    }

    /**
     * This method returns the sorted list of natural gas usage
     * @return sorted list of natural gas usage
     */
    public List<Double> getSortedNaturalGasUsed() {
        return new ArrayList<>(naturalGasUsed);
    }

    /**
     * This method returns a sorted list of total costs
     * @return sorted list of total costs
     */
    public List<Double> getSortedTotalCost(){return new ArrayList<>(sortedTotalCost);}

    /**
     * This method returns a sorted list of emissions
     * @return sorted list of total emissions
     */
    public List<Double> getSortedEmission() {
        return sortedEmission;
    }

    /**
     * This method returns a sorted list of electricity costs
     * @return a sorted list of electricity costs
     */
    public List<Double> getSortedElectricityCost() {
        return sortedElectricityCost;
    }

    /**
     * This method returns a sorted list of natural gas costs
     * @return a sorted list of natural gas costs
     */
    public List<Double> getSortedNaturalGasCost() {
        return sortedNaturalGasCost;
    }
}
