package object;

import main.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the energy usage that the user enters in every month
 */
public class Usage {

    private static List<String> months = new ArrayList<>();

    private static List<Double> electricityUsed = new ArrayList<>();

    private static List<Double> naturalGasUsed = new ArrayList<>();

    /**
     * Initializes the lists of months, electricity, and natural gas entered
     */
    public Usage() {
        this.months = months;
        this.electricityUsed = electricityUsed;
        this.naturalGasUsed = naturalGasUsed;
    }

    /**
     * This method returns a copy of the list of months the user has entered
     * @return copy of the list of months the user has entered
     */
    public List<String> getMonths() {
        return new ArrayList<>(months);
    }

    /**
     * This method returns a copy of the list of electricity amounts the user has entered
     * @return copy of the list of electricity amounts the user has entered
     */
    public List<Double> getElectricityUsed() {
        return new ArrayList<>(electricityUsed);
    }

    /**
     * This method returns a copy of the list of natural gas amounts the user has entered
     * @return copy of the list of natural gas amounts the user has entered
     */
    public List<Double> getNaturalGasUsed() {
        return new ArrayList<>(naturalGasUsed);
    }

    /**
     * This method helps to add the month entered to the list of months
     * @param month month to be added
     */
    public void addMonth(String month) {
        months.add(month);
    }

    /**
     * This method helps to add the electricity entered to the list of electricity amounts
     * @param electricity electricity amount to be added
     */
    public void addElectricityUsage(double electricity) {
        electricityUsed.add(electricity);
    }

    /**
     * This method helps to add the natural gas entered to the list of natural gas amounts
     * @param naturalGas natural gas amount to be added
     */
    public void addNaturalGasUsage(double naturalGas){
        naturalGasUsed.add(naturalGas);
    }



}
