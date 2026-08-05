package object;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cost {

    public static double getElectricityCost(double electricity){
        return RoundingHelper.roundingHelper( electricity*0.1206); // default electricity rates
    }

    public static double getNaturalGasCost(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*1.595); // default natural gas rates
    }

    public static double getTotalCost(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*0.1206)+(naturalGas*1.595));
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

}
