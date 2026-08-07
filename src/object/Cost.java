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

    public static double getPercentageDiffInCostFromLastMonth(String currMonth, String lastMonth, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        int indexOfCurrMonth = months.indexOf(currMonth);
        int indexOfLastMonth = months.indexOf(lastMonth);
        double costForCurrMonth = getTotalCost(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth));
        double costForLastMonth = getTotalCost(electricityUsed.get(indexOfLastMonth), naturalGasUsed.get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((costForCurrMonth - costForLastMonth)/costForLastMonth)*100);
    }

    public static double getMonthlyCostPercentage(String month, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCostOfAllMonths = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalCostOfAllMonths += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        int indexOfCurrMonth = months.indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCost(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth)))/totalCostOfAllMonths)*100);
    }

    public static double getAverageCost(List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCost = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalCost += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        return RoundingHelper.roundingHelper(totalCost/electricityUsed.size());
    }

}
