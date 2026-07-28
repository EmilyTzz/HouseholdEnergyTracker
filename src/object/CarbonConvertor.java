package object;

public class CarbonConvertor {

    public static double getElectricityCarbonFootprint(double electricity){
        return electricity*0.335; // estimated kg of carbon 1 kwh of electricity emits
    }

    public static double getNaturalGasCarbonFootprint(double naturalGas){
        return naturalGas*50; // estimated kg of carbon 1 gj of natural gas emits
    }
}
