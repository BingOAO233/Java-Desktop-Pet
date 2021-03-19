package utils.engine;

import java.util.Random;

/**
 * @author BingoIO_OI233
 */
public class MathTools
{
    private static Random rand = new Random();

    /**
     * @return random int
     */
    public static int randNextInt()
    {
        return rand.nextInt();
    }

    /**
     * @param lowerBound a
     * @param upperBound b
     * @return random int in [a,b]
     */
    public static int randNextInt(int lowerBound, int upperBound)
    {
        return rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    public static int randNextInt(int upperBound)
    {
        return randNextInt(0, upperBound);
    }
}
