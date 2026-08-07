package object;

public class CarbonConvertor {

    public static double getElectricityCarbonFootprint(double electricity){
        return RoundingHelper.roundingHelper(electricity*0.335); // estimated kg of carbon 1 kwh of electricity emits
    }

    public static double getNaturalGasCarbonFootprint(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*50); // estimated kg of carbon 1 gj of natural gas emits
    }

    public static double getEquivalentOfCO2Emission(double emission){
        return Math.round(emission/12.39); // An average household uses 12.39 kg of CO2 per day
    }

    public static double getTotalCarbonEmission(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*0.335)+(naturalGas*50));
    }

    public static double getPercentageDiffInCarbonFromLastMonth(String currMonth, String lastMonth, UsageSorter usageSorter){
        int indexOfCurrMonth = usageSorter.getSortedMonths().indexOf(currMonth);
        int indexOfLastMonth = usageSorter.getSortedMonths().indexOf(lastMonth);
        double carbonForCurrMonth = getTotalCarbonEmission(usageSorter.getSortedElectricityUsed().get(indexOfCurrMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfCurrMonth));
        double carbonForLastMonth = getTotalCarbonEmission(usageSorter.getSortedElectricityUsed().get(indexOfLastMonth), usageSorter.getSortedNaturalGasUsed().get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((carbonForCurrMonth - carbonForLastMonth)/carbonForLastMonth)*100);
    }

    public static double getMonthlyEmissionPercentage(String month, Usage usage){
        double totalCarbonOfAllMonths = 0;
        for (int i = 0; i < usage.getMonths().size(); i ++){
            totalCarbonOfAllMonths += getTotalCarbonEmission(usage.getElectricityUsed().get(i), usage.getNaturalGasUsed().get(i));
        }
        int indexOfCurrMonth = usage.getMonths().indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCarbonEmission(usage.getElectricityUsed().get(indexOfCurrMonth), usage.getNaturalGasUsed().get(indexOfCurrMonth)))/totalCarbonOfAllMonths)*100);
    }
}
