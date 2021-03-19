package utils.engine;

/**
 * @author BingoIO_OI233
 */
public class Enums
{
    public static <T extends Enum<T>> T random(Class<T> ec)
    {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values)
    {
        return values[MathTools.randNextInt(values.length-1)];
    }
}
