package object;

import main.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsageSorter {

    public static List<String> months;

    public static List<Double> electricityUsed;

    public static List<Double> naturalGasUsed;

    public UsageSorter(List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        this.months = months;
        this.electricityUsed = electricityUsed;
        this.naturalGasUsed = naturalGasUsed;
    }

    // Sorts months in the traditional January - December order
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

    // Adds the month and its electricity and natural gas usage to the same order
    private void sorterHelper(List<String> sortedMonths, List<Double> sortedElectricityUsed, List<Double> sortedNaturalGasUsed, String month){
        for (int i = 0; i < months.size(); i ++){
            if (months.get(i).equals(month)){
                sortedMonths.add(months.get(i));
                sortedElectricityUsed.add(electricityUsed.get(i));
                sortedNaturalGasUsed.add(naturalGasUsed.get(i));
            }
        }
    }

    // Reverses the highest to lowest monthly costs order
    public void sortFromLowestToHighestTotalCost(Cost cost){
        sortFromHighestToLowest(Menu.TOTAL_COST, cost, null);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
    }

    // Reverses the highest to lowest monthly emission order
    public void sortFromLowestToHighestTotalEmission(CarbonConvertor carbonConvertor){
        sortFromHighestToLowest(Menu.TOTAL_EMISSION, null, carbonConvertor);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
    }

    // Reverses the highest to lowest monthly electricity costs order
    public void sortFromLowestToHighestElectricityCost(Cost cost){
        sortFromHighestToLowest(Menu.ELECTRICITY_COST, cost, null);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
    }

    // Reverses the highest to lowest monthly natural gas costs order
    public void sortFromLowestToHighestNaturalGasCost(Cost cost){
        sortFromHighestToLowest(Menu.NATURAL_GAS_COST, cost, null);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);
    }

    // sort from highest to lowest total cost or electricity cost or natural gas cost or total emission %
    public void sortFromHighestToLowest(String energyData, Cost cost, CarbonConvertor carbonConvertor){
        List<String> sortedMonths = new ArrayList<>();
        List<Double> sortedElectricityUsed = new ArrayList<>();
        List<Double> sortedNaturalGasUsed = new ArrayList<>();
        while (!months.isEmpty()){
            int indexOfMonthWithHighestEnergyData = 0;
            if (energyData.equals(Menu.TOTAL_COST)) {
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestTotalCostHelper(cost);
            }
            else if (energyData.equals(Menu.TOTAL_EMISSION)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestTotalEmissionHelper(carbonConvertor);
            }
            else if (energyData.equals(Menu.ELECTRICITY_COST)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestElectricityHelper();
            }
            else if (energyData.equals(Menu.NATURAL_GAS_COST)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestNaturalGasHelper();
            }
            sortedMonths.add(months.get(indexOfMonthWithHighestEnergyData));
            sortedElectricityUsed.add(electricityUsed.get(indexOfMonthWithHighestEnergyData));
            sortedNaturalGasUsed.add(naturalGasUsed.get(indexOfMonthWithHighestEnergyData));
            months.remove(months.get(indexOfMonthWithHighestEnergyData));
            electricityUsed.remove(electricityUsed.get(indexOfMonthWithHighestEnergyData));
            naturalGasUsed.remove(naturalGasUsed.get(indexOfMonthWithHighestEnergyData));
        }
        months = sortedMonths;
        electricityUsed = sortedElectricityUsed;
        naturalGasUsed = sortedNaturalGasUsed;
    }

    // Helper that finds the month with the highest total cost to add to the new list one by one
    private int sortFromHighestToLowestTotalCostHelper(Cost cost){
        int indexOfMonthWithHighestTotalCost = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size(); i++){
                if (cost.getTotalCost(electricityUsed.get(indexOfMonthWithHighestTotalCost), naturalGasUsed.get(indexOfMonthWithHighestTotalCost))<cost.getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i))){
                    indexOfMonthWithHighestTotalCost = i;
                }

            }
        }
        return indexOfMonthWithHighestTotalCost;
    }

    // Helper that finds the month with the highest emission to add to the new list one by one
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

    // Helper that finds the month with the highest electricity cost to add to the new list one by one
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

    // Helper that finds the month with the highest natural gas cost to add to the new list one by one
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

    public List<String> getSortedMonths() {
        return new ArrayList<>(months);
    }

    public List<Double> getSortedElectricityUsed() {
        return new ArrayList<>(electricityUsed);
    }

    public List<Double> getSortedNaturalGasUsed() {
        return new ArrayList<>(naturalGasUsed);
    }

}
