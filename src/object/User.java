package object;

import main.Menu;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static List<String> months = new ArrayList<>();

    private static List<Double> electricityUsed = new ArrayList<>();

    private static List<Double> naturalGasUsed = new ArrayList<>();

    public User() {
        this.months = months;
        this.electricityUsed = electricityUsed;
        this.naturalGasUsed = naturalGasUsed;
    }

    public List<String> getMonths() {
        return months;
    }

    public List<Double> getElectricityUsed() {
        return electricityUsed;
    }

    public List<Double> getNaturalGasUsed() {
        return naturalGasUsed;
    }

    public void addMonth(String month) {
        months.add(month);
    }

    public void addElectricityUsage(double electricity) {
        electricityUsed.add(electricity);
    }

    public void addNaturalGasUsage(double naturalGas){
        naturalGasUsed.add(naturalGas);
    }

    public void sortInfoAccordingToMonths(){
        List<String> sortedMonths = new ArrayList<>();
        List<Double> sortedElectricityUsed = new ArrayList<>();
        List<Double> sortedNaturalGasUsed = new ArrayList<>();
        for (int i = 0; i < Menu.validMonths.size(); i++){
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
}
