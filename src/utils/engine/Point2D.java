package utils.engine;

import java.awt.*;

/**
 * @author BingoIO_OI233
 */
public class Point2D
{
    public double x, y;

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point2D()
    {
        this(0, 0);
    }

    public Point2D(int x, int y)
    {
        this((double) x, (double) y);
    }

    public int getX()
    {
        return (int) x;
    }

    public int getY()
    {
        return (int) y;
    }

    public Point2D add(Vector2D vector)
    {
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Point2D add(double dx, double dy)
    {
        x += dx;
        y += dy;
        return this;
    }

    public Point2D generateFromPoint(Point p)
    {
        this.x = p.x;
        this.y = p.y;
        return this;
    }

    public Point2D generateFromPoint2D(Point2D p)
    {
        this.x = p.x;
        this.y = p.y;
        return this;
    }

    public boolean isInAreaRect(double minX, double minY, double maxX, double maxY)
    {
        return x > minX && x < maxX && y > minY && y < maxY;
    }

    public Point2D limitToAreaRect(double minX, double minY, double maxX, double maxY)
    {
        x = Math.min(x, maxX);
        x = Math.max(x, minX);
        y = Math.min(y, maxY);
        y = Math.max(y, minY);
        return this;
    }

    public Point toPoint()
    {
        return new Point(this.getX(), this.getY());
    }

    @Override
    public String toString()
    {
        return "Point2D: (" + this.x + "," + this.y + ")";
    }
}
