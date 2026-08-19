package object;
import java.util.List;

public class Cost {

    private static double costPerKwH = 0;

    private static double costPerGJ = 0;

    public Cost(){
        this.costPerKwH = costPerKwH;
        this.costPerGJ = costPerGJ;
    }

    public double getCostPerKwH() {
        return costPerKwH;
    }

    public double getCostPerGJ() {
        return costPerGJ;
    }

    public void setCostPerKwH(double price){
        costPerKwH = price;
    }

    public void setCostPerGJ(double price){
        costPerGJ = price;
    }

    public double getElectricityCost(double electricity){
        return RoundingHelper.roundingHelper( electricity*costPerKwH); // default electricity rates
    }

    public double getNaturalGasCost(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*costPerGJ); // default natural gas rates
    }

    public double getTotalCost(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*costPerKwH)+(naturalGas*costPerGJ));
    }

    public double getPercentageDiffInCostFromLastMonth(String currMonth, String lastMonth, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        int indexOfCurrMonth = months.indexOf(currMonth);
        int indexOfLastMonth = months.indexOf(lastMonth);
        double costForCurrMonth = getTotalCost(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth));
        double costForLastMonth = getTotalCost(electricityUsed.get(indexOfLastMonth), naturalGasUsed.get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((costForCurrMonth - costForLastMonth)/costForLastMonth)*100);
    }

    public double getMonthlyCostPercentage(String month, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCostOfAllMonths = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalCostOfAllMonths += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        int indexOfCurrMonth = months.indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCost(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth)))/totalCostOfAllMonths)*100);
    }

    public double getAverageCost(List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCost = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalCost += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        return RoundingHelper.roundingHelper(totalCost/electricityUsed.size());
    }

}
