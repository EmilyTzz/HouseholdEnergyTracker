package object;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cost {

    private static double costPerKwH;

    private static double costPerGJ;

    public Cost(){
        this.costPerKwH = 0;
        this.costPerGJ = 0;
    }

    public static double getCostPerKwH() {
        return costPerKwH;
    }

    public static double getCostPerGJ() {
        return costPerGJ;
    }

    public static void setCostPerKwH(double price){
        costPerGJ = price;
    }

    public static void setCostPerGJ(double price){
        costPerGJ = price;
    }

    public static double getElectricityCost(double electricity){
        return RoundingHelper.roundingHelper( electricity*costPerKwH); // default electricity rates
    }

    public static double getNaturalGasCost(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*costPerGJ); // default natural gas rates
    }

    public static double getTotalCost(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*costPerKwH)+(naturalGas*costPerGJ));
    }

    public static double getPercentageDiffInCostFromLastMonth(String currMonth, String lastMonth, UsageSorter usageSorter){
        int indexOfCurrMonth = usageSorter.getSortedMonths().indexOf(currMonth);
        int indexOfLastMonth = usageSorter.getSortedMonths().indexOf(lastMonth);
        double costForCurrMonth = getTotalCost(usageSorter.getSortedElectricityUsed().get(indexOfCurrMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfCurrMonth));
        double costForLastMonth = getTotalCost(usageSorter.getSortedElectricityUsed().get(indexOfLastMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((costForCurrMonth - costForLastMonth)/costForLastMonth)*100);
    }

    public static double getMonthlyCostPercentage(String month, Usage usage){
        double totalCostOfAllMonths = 0;
        for (int i = 0; i < usage.getMonths().size(); i ++){
            totalCostOfAllMonths += getTotalCost(usage.getElectricityUsed().get(i), usage.getNaturalGasUsed().get(i));
        }
        int indexOfCurrMonth = usage.getMonths().indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCost(usage.getElectricityUsed().get(indexOfCurrMonth), usage.getNaturalGasUsed().get(indexOfCurrMonth)))/totalCostOfAllMonths)*100);
    }

    public static double getAverageCost(List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCost = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalCost += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        return RoundingHelper.roundingHelper(totalCost/electricityUsed.size());
    }

}
