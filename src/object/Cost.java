package object;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
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

    public static void setCostPerKwH(){
        while (true){
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("Price per KwH: ");
                costPerKwH = scanner.nextDouble();
                break;
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
    }

    public static void setCostPerGJ(){
        while (true){
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("Price per GJ: ");
                costPerGJ = scanner.nextDouble();
                break;
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid amount");
            }
        }
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
