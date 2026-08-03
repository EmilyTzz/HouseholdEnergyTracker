package object;

public class CarbonConvertor {

    public static double getElectricityCarbonFootprint(double electricity){
        return electricity*0.335; // estimated kg of carbon 1 kwh of electricity emits
    }

    public static double getNaturalGasCarbonFootprint(double naturalGas){
        return naturalGas*50; // estimated kg of carbon 1 gj of natural gas emits
    }

    public static double getEquivalentOfCO2Emission(double emission){
        return Math.round(emission/12.39); // An average household uses 12.39 kg of CO2 per day
    }

    public static double getTotalCarbonEmission(double electricity, double naturalGas){
        return (electricity*0.335)+(naturalGas*50);
    }

    public static double getPercentageDiffInCarbonFromLastMonth(String currMonth, String lastMonth, UsageSorter usageSorter){
        int indexOfCurrMonth = usageSorter.getSortedMonths().indexOf(currMonth);
        int indexOfLastMonth = usageSorter.getSortedMonths().indexOf(lastMonth);
        double carbonForCurrMonth = getTotalCarbonEmission(usageSorter.getSortedElectricityUsed().get(indexOfCurrMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfCurrMonth));
        double carbonForLastMonth = getTotalCarbonEmission(usageSorter.getSortedElectricityUsed().get(indexOfLastMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfLastMonth));
        double diff = ((carbonForCurrMonth - carbonForLastMonth)/carbonForLastMonth)*100;
        return diff;
    }
}
