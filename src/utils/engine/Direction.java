package utils.engine;

/**
 * @author BingoIO_OI233
 * a polar coordinates based Direction
 * main angel output range: [-180, 180]
 */
public class Direction
{
    private double baseAngle = 0;
    private double dirAngle;

    private double spinAngle;

    public Direction(Point2D start, Point2D end)
    {
        this(new Vector2D(start, end));
    }

    public Direction(Vector2D directionVector)
    {
        double acos = Math.acos(directionVector.x / directionVector.scalar()) / Math.PI * 180;
        double asin = Math.asin(directionVector.y / directionVector.scalar()) / Math.PI * 180;
        if (acos <= 90)
        {
            this.dirAngle = asin;
        }
        else if (asin > 0)
        {
            this.dirAngle = acos;
        }
        else
        {
            this.dirAngle = -acos;
        }
        spinAngle = dirAngle - baseAngle;
    }

    public double getSpinAngle()
    {
        return spinAngle;
    }

    public static void main(String[] args)
    {
        System.out.println(Math.acos(-1));
    }
}
