package object;

import main.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    private void sorterHelper(List<String> sortedMonths, List<Double> sortedElectricityUsed, List<Double> sortedNaturalGasUsed, String month){
        for (int i = 0; i < months.size(); i ++){
            if (months.get(i).equals(month)){
                sortedMonths.add(months.get(i));
                sortedElectricityUsed.add(electricityUsed.get(i));
                sortedNaturalGasUsed.add(naturalGasUsed.get(i));
            }
        }
    }


    public void sortFromLowestToHighestTotalCost(){
        sortFromHighestToLowest(Menu.TOTAL_COST);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);

    }

    public void sortFromLowestToHighestElectricityUsage(){
        sortFromHighestToLowest(Menu.ELECTRICITY_COST);
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);

    }

    public void sortFromHighestToLowest(String energyData){
        List<String> sortedMonths = new ArrayList<>();
        List<Double> sortedElectricityUsed = new ArrayList<>();
        List<Double> sortedNaturalGasUsed = new ArrayList<>();
        while (months.size() != 0){
            int indexOfMonthWithHighestEnergyData = 0;
            if (energyData.equals(Menu.TOTAL_COST)) {
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestTotalCostHelper();
            }
            else if (energyData.equals(Menu.ELECTRICITY_COST)){
                indexOfMonthWithHighestEnergyData = sortFromHighestToLowestElectricityHelper();
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

    private int sortFromHighestToLowestTotalCostHelper(){
        int indexOfMonthWithHighestTotalCost = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size(); i++){
                if (Cost.getTotalCost(electricityUsed.get(indexOfMonthWithHighestTotalCost), naturalGasUsed.get(indexOfMonthWithHighestTotalCost))<Cost.getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i))){
                    indexOfMonthWithHighestTotalCost = i;
                }

            }
        }
        return indexOfMonthWithHighestTotalCost;
    }

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
