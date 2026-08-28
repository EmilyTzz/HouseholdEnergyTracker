package object;
import java.util.List;

/**
 * This class represents the costs/kwH and GJ
 */
public class Cost {

    private static double costPerKwH = 0;

    private static double costPerGJ = 0;

    /**
     * Initializes the cost per kwH and GJ
     */
    public Cost(){
        this.costPerKwH = costPerKwH;
        this.costPerGJ = costPerGJ;
    }

    /**
     * This method helps to return the current cost/kwH
     * @return current cost/kwH
     */
    public double getCostPerKwH() {
        return costPerKwH;
    }

    /**
     * This method helps to return the current cost/GJ
     * @return current cost/GJ
     */
    public double getCostPerGJ() {
        return costPerGJ;
    }

    /**
     * This method sets the current price/kwH to the price entered
     * @param price new price entered by user
     */
    public void setCostPerKwH(double price){
        costPerKwH = price;
    }

    /**
     * This method sets the current price/GJ to the price entered
     * @param price new price entered by user
     */
    public void setCostPerGJ(double price){
        costPerGJ = price;
    }

    /**
     * This method helps to get the total costs of the amount of electricity the user used in a month
     * @param electricity the amount of electricity the user used in a month
     * @return total costs of the amount of electricity the user used in a month
     */
    public double getElectricityCost(double electricity){
        return RoundingHelper.roundingHelper( electricity*costPerKwH); // times electricity amount by its price/kwH
    }

    /**
     * This method helps to get the total costs of the amount of natural gas the user used in a month
     * @param naturalGas the amount of natural gas the user used in a month
     * @return total costs of the amount of natural gas the user used in a month
     */
    public double getNaturalGasCost(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*costPerGJ);
    }

    /**
     * This method helps to find the total cost of both the electricity + natural gas costs
     * @param electricity amount of electricity used
     * @param naturalGas amount of natural gas used
     * @return total cost of both the electricity + natural gas costs
     */
    public double getTotalCost(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*costPerKwH)+(naturalGas*costPerGJ));
    }

    /**
     * This method helps to find the % of how much the total usage costs from the current month increased or decreased from the previous month
     * @param currMonth the current month entered
     * @param lastMonth the closest month before the current month
     * @param months list of months that the user has entered
     * @param electricityUsed list of electricity amount that the user has entered
     * @param naturalGasUsed list of natural gas amount that the user has entered
     * @return  % of how much the total cost from the current month increased or decreased from the previous month
     */
    public double getPercentageDiffInCostFromLastMonth(String currMonth, String lastMonth, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        int indexOfCurrMonth = months.indexOf(currMonth);
        int indexOfLastMonth = months.indexOf(lastMonth);
        double costForCurrMonth = getTotalCost(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth));
        double costForLastMonth = getTotalCost(electricityUsed.get(indexOfLastMonth), naturalGasUsed.get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((costForCurrMonth - costForLastMonth)/costForLastMonth)*100);
    }

    /**
     * This method helps to find how much % a month contribute to the overall total cost of all the months
     * @param month the month we want to see how much % it contribute to the overall total cost
     * @param months a list of all the months the user entered
     * @param electricityUsed list of electricity amount that the user has entered
     * @param naturalGasUsed list of natural gas amount that the user has entered
     * @return how much % the month contribute to the overall total cost of all the months
     */
    public double getMonthlyCostPercentage(String month, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCostOfAllMonths = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){ // finds the total costs from all of the months by adding their individual total costs
            totalCostOfAllMonths += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        int indexOfCurrMonth = months.indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCost(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth)))/totalCostOfAllMonths)*100);
    }

    /**
     * This method helps to get the average total usage cost from all of the months entered
     * @param electricityUsed list of electricity amount that the user has entered
     * @param naturalGasUsed list of natural gas amount that the user has entered
     * @return the average total usage cost from all of the months entered
     */
    public double getAverageCost(List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCost = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){ // finds the total costs from all of the months by adding their individual total costs
            totalCost += getTotalCost(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        return RoundingHelper.roundingHelper(totalCost/electricityUsed.size());
    }

}
