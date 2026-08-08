package object;

import java.util.List;

public class CarbonConvertor {

    public double getElectricityCarbonFootprint(double electricity){
        return RoundingHelper.roundingHelper(electricity*0.335); // estimated kg of carbon 1 kwh of electricity emits
    }

    public double getNaturalGasCarbonFootprint(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*50); // estimated kg of carbon 1 gj of natural gas emits
    }

    public double getEquivalentOfCO2Emission(double emission){
        return Math.round(emission/12.39); // An average household uses 12.39 kg of CO2 per day
    }

    public double getTotalCarbonEmission(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*0.335)+(naturalGas*50));
    }

    public double getPercentageDiffInCarbonFromLastMonth(String currMonth, String lastMonth, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        int indexOfCurrMonth = months.indexOf(currMonth);
        int indexOfLastMonth = months.indexOf(lastMonth);
        double carbonForCurrMonth = getTotalCarbonEmission(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth));
        double carbonForLastMonth = getTotalCarbonEmission(electricityUsed.get(indexOfLastMonth), naturalGasUsed.get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((carbonForCurrMonth - carbonForLastMonth)/carbonForLastMonth)*100);
    }


    public double getMonthlyEmissionPercentage(String month, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCarbonOfAllMonths = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalCarbonOfAllMonths += getTotalCarbonEmission(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        int indexOfCurrMonth = months.indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCarbonEmission(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth)))/totalCarbonOfAllMonths)*100);
    }


    public double getAverageEmission(List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalEmission = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){
            totalEmission += getTotalCarbonEmission(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        return RoundingHelper.roundingHelper(totalEmission/electricityUsed.size());
    }
}
