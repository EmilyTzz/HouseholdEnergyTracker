package object;

import java.util.List;

public class CarbonConvertor {

    /**
     * This method helps to get the amount of emission produced from the amount of electricity used
     * @param electricity the amount of electricity the user used
     * @return the amount of emission produced
     */
    public double getElectricityCarbonFootprint(double electricity){
        return RoundingHelper.roundingHelper(electricity*0.335); // estimated kg of carbon 1 kwh of electricity emits
    }

    /**
     * This method helps to get the amount of emission produced from the amount of natural gas used
     * @param naturalGas the amount of natural gas the user used
     * @return the amount of emission produced
     */
    public double getNaturalGasCarbonFootprint(double naturalGas){
        return RoundingHelper.roundingHelper(naturalGas*50); // estimated kg of carbon 1 gj of natural gas emits
    }

    /**
     * This method helps to get the number of households that the total emission the user produced can be used to power
     * @param emission the amount of emission the user produced
     * @return the number of households that the total emission the user produced can be used to power
     */
    public double getEquivalentOfCO2Emission(double emission){
        return Math.round(emission/12.39); // An average household uses 12.39 kg of CO2 per day
    }

    /**
     * This method helps to find the total emission of both the electricity + natural gas emission
     * @param electricity amount of electricity used
     * @param naturalGas amount of natural gas used
     * @return total emission of both the electricity + natural gas emission
     */
    public double getTotalCarbonEmission(double electricity, double naturalGas){
        return RoundingHelper.roundingHelper((electricity*0.335)+(naturalGas*50));
    }

    /**
     * This method helps to find the % of how much the emission from the current month increased or decreased from the previous month
     * @param currMonth the current month entered
     * @param lastMonth the closest month before the current month
     * @param months list of months that the user has entered
     * @param electricityUsed list of electricity amount that the user has entered
     * @param naturalGasUsed list of natural gas amount that the user has entered
     * @return  % of how much the emission from the current month increased or decreased from the previous month
     */
    public double getPercentageDiffInCarbonFromLastMonth(String currMonth, String lastMonth, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        int indexOfCurrMonth = months.indexOf(currMonth);
        int indexOfLastMonth = months.indexOf(lastMonth);
        double carbonForCurrMonth = getTotalCarbonEmission(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth));
        double carbonForLastMonth = getTotalCarbonEmission(electricityUsed.get(indexOfLastMonth), naturalGasUsed.get(indexOfLastMonth));
        return RoundingHelper.roundingHelper(((carbonForCurrMonth - carbonForLastMonth)/carbonForLastMonth)*100); // finds the percentage difference
    }

    /**
     * This method helps to find how much % a month contribute to the overall emission the user produced
     * @param month the month we want to see how much % it contribute to the overall emission
     * @param months a list of all the months the user entered
     * @param electricityUsed list of electricity amount that the user has entered
     * @param naturalGasUsed list of natural gas amount that the user has entered
     * @return how much % the month contribute to the overall emission the user produced
     */
    public double getMonthlyEmissionPercentage(String month, List<String> months, List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalCarbonOfAllMonths = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){ // finds the total emission from all of the months by adding their individual emissions
            totalCarbonOfAllMonths += getTotalCarbonEmission(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        int indexOfCurrMonth = months.indexOf(month);
        return RoundingHelper.roundingHelper(((getTotalCarbonEmission(electricityUsed.get(indexOfCurrMonth), naturalGasUsed.get(indexOfCurrMonth)))/totalCarbonOfAllMonths)*100);
    }

    /**
     * This method helps to get the average emission the user produced from all of the months entered
     * @param electricityUsed list of electricity amount that the user has entered
     * @param naturalGasUsed list of natural gas amount that the user has entered
     * @return the average emission the user produced from all of the months entered
     */
    public double getAverageEmission(List<Double> electricityUsed, List<Double> naturalGasUsed){
        double totalEmission = 0;
        for (int i = 0; i < electricityUsed.size(); i ++){ // finds the total emission from all of the months by adding their individual emissions
            totalEmission += getTotalCarbonEmission(electricityUsed.get(i), naturalGasUsed.get(i));
        }
        return RoundingHelper.roundingHelper(totalEmission/electricityUsed.size());
    }
}
