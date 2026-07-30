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
}
