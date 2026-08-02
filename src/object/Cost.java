package object;

public class Cost {

    public static double getElectricityCost(double electricity){
        return electricity*0.1206; // default electricity rates
    }

    public static double getNaturalGasCost(double naturalGas){
        return naturalGas*1.595; // default natural gas rates
    }

    public static double getTotalCost(double electricity, double naturalGas){
        return (electricity*0.1206)+(naturalGas*1.595);
    }

    public static double getPercentageDiffInCostFromLastMonth(String currMonth, String lastMonth, UsageSorter usageSorter){
        int indexOfCurrMonth = usageSorter.getSortedMonths().indexOf(currMonth);
        int indexOfLastMonth = usageSorter.getSortedMonths().indexOf(lastMonth);
        double costForCurrMonth = getTotalCost(usageSorter.getSortedElectricityUsed().get(indexOfCurrMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfCurrMonth));
        double costForLastMonth = getTotalCost(usageSorter.getSortedElectricityUsed().get(indexOfLastMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfLastMonth));
        double diff = ((costForCurrMonth - costForLastMonth)/costForLastMonth)*100;
        return diff;
    }
}
