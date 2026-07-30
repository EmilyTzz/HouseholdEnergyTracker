package object;

import main.Menu;

import java.util.ArrayList;
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
