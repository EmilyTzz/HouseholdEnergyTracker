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
}
