package object;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static List<String> months = new ArrayList<>();

    private static List<Integer> electricityUsed = new ArrayList<>();

    private static List<Double> naturalGasUsed = new ArrayList<>();

    private User(List<String> months, List<Integer> electricityUsed, List<Double> naturalGasUsed) {
        this.months = months;
        this.electricityUsed = electricityUsed;
        this.naturalGasUsed = naturalGasUsed;
    }

    public static List<String> getMonths() {
        return months;
    }

    public static List<Integer> getElectricityUsed() {
        return electricityUsed;
    }

    public static List<Double> getNaturalGasUsed() {
        return naturalGasUsed;
    }

    public static void addMonth(String month) {
        months.add(month);
    }

    public static void addElectricityUsage(int electricity) {
        electricityUsed.add(electricity);
    }

    public static void addNaturalGasUsage(double naturalGas){
        naturalGasUsed.add(naturalGas);
    }
}
