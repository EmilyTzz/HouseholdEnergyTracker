package object;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingHelper {
    public static double roundingHelper(double number){
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.HALF_UP); // rounds number to 2 decimals
        return bd.doubleValue();
    }
}
