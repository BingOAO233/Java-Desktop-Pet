package utils.engine;

import javax.crypto.interfaces.PBEKey;

/**
 * @author BingoIO_OI233
 */
public class Vector2D
{
    double x, y;

    public Vector2D(Point2D p)
    {
        this.x = p.x;
        this.y = p.y;
    }

    public Vector2D(Point2D a, Point2D b)
    {
        this.x = b.x - a.x;
        this.y = b.y - a.y;
    }

    public Vector2D()
    {
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return (int) x;
    }

    public int getY()
    {
        return (int) y;
    }

    public Vector2D add(Vector2D vec)
    {
        return new Vector2D(this.x + vec.x, this.y + vec.y);
    }

    public Vector2D minus(Vector2D vec)
    {
        return new Vector2D(this.x - vec.x, this.y - vec.y);
    }

    public Vector2D multiple(double ratio)
    {
        x *= ratio;
        y *= ratio;
        return this;
    }

    public Vector2D scaleTo(double distScalar)
    {
        double ratio = distScalar / scalar();
        return multiple(ratio);
    }

    public double scalar()
    {
        return Math.sqrt(x * x + y * y);
    }

    public double dotX(Vector2D vec)
    {
        return (this.x * vec.x + this.y * vec.y);
    }

    /**
     * Vector2D only can get the scalar of the cross product
     *
     * @param vec another operand vector
     * @return scalar of the cross product
     */
    public double crossX(Vector2D vec)
    {
        return (this.x * vec.x + this.y * vec.y);
    }

    public Point2D toPoint()
    {
        return new Point2D(this.x, this.y);
    }

    @Override
    public String toString()
    {
        return "Vector2D: (" + x + "," + y + ")";
    }
}
