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
        sortFromHighestToLowestTotalCost();
        Collections.reverse(months);
        Collections.reverse(electricityUsed);
        Collections.reverse(naturalGasUsed);

    }

    public void sortFromHighestToLowestTotalCost(){
        List<String> sortedMonths = new ArrayList<>();
        List<Double> sortedElectricityUsed = new ArrayList<>();
        List<Double> sortedNaturalGasUsed = new ArrayList<>();
        while (months.size() != 0){
            int indexOfMonthWithHighestTotalCost = sortFromHighestToLowestTotalCostHelper();
            sortedMonths.add(months.get(indexOfMonthWithHighestTotalCost));
            sortedElectricityUsed.add(electricityUsed.get(indexOfMonthWithHighestTotalCost));
            sortedNaturalGasUsed.add(naturalGasUsed.get(indexOfMonthWithHighestTotalCost));
            months.remove(months.get(indexOfMonthWithHighestTotalCost));
            electricityUsed.remove(electricityUsed.get(indexOfMonthWithHighestTotalCost));
            naturalGasUsed.remove(naturalGasUsed.get(indexOfMonthWithHighestTotalCost));
        }
        months = sortedMonths;
        electricityUsed = sortedElectricityUsed;
        naturalGasUsed = sortedNaturalGasUsed;
    }

    private int sortFromHighestToLowestTotalCostHelper(){
        int indexOfMonthWithHighestTotalCost = 0;
        if (months.size() > 1){
            for (int i = 0; i < months.size()-1; i++){
                if (Cost.getTotalCost(electricityUsed.get(indexOfMonthWithHighestTotalCost), naturalGasUsed.get(indexOfMonthWithHighestTotalCost))<Cost.getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i))){
                    indexOfMonthWithHighestTotalCost = i;
                }

            }
        }
        return indexOfMonthWithHighestTotalCost;
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
