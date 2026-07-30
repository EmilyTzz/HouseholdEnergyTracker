package object;

import main.Menu;

import java.util.ArrayList;
import java.util.List;

public class Usage {

    private static List<String> months = new ArrayList<>();

    private static List<Double> electricityUsed = new ArrayList<>();

    private static List<Double> naturalGasUsed = new ArrayList<>();

    public Usage() {
        this.months = months;
        this.electricityUsed = electricityUsed;
        this.naturalGasUsed = naturalGasUsed;
    }

    public List<String> getMonths() {
        return new ArrayList<>(months);
    }

    public List<Double> getElectricityUsed() {
        return new ArrayList<>(electricityUsed);
    }

    public List<Double> getNaturalGasUsed() {
        return new ArrayList<>(naturalGasUsed);
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



}
