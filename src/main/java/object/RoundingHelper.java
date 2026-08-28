package object;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class is a rounding helper that helps to round values to a certain decimal point
 */
public class RoundingHelper {

    /**
     * This method helps to round any number to only 2 decimals
     * @param number the number that needs to be rounded
     * @return rounded number with 2 decimal points
     */
    public static double roundingHelper(double number){
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.HALF_UP); // rounds number to 2 decimals
        return bd.doubleValue();
    }
}
