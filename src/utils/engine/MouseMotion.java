package utils.engine;

import utils.MouseChaser;

import java.awt.*;
import java.math.RoundingMode;

/**
 * @author BingoIO_OI233
 */
public class MouseMotion implements Runnable
{
    private double velocity;
    private Point2D start, end;
    private Point2D objPosition;
    public boolean isEnd = false;

    public MouseMotion()
    {
    }

    public MouseMotion setStartPoint(Point2D start)
    {
        return setStartPoint(start.getX(), start.getY());
    }

    public MouseMotion setStartPoint(int x, int y)
    {
        this.start = new Point2D(x, y);
        this.objPosition = new Point2D(x, y);
        return this;
    }

    public MouseMotion setEndPoint(Point2D end)
    {
        this.end = end;
        return this;
    }

    public MouseMotion setVelocity(double velocity)
    {
        this.velocity = velocity;
        return this;
    }

    @Override
    public void run()
    {
        //位移向量
        Vector2D motionVector = new Vector2D(start, end);

        //距离
        double length = motionVector.scalar();

        //单位化
        motionVector = motionVector.multiple(velocity / length);

        //单位化
        double deltaLength = motionVector.scalar();

        try
        {
            //Robot
            Robot robot = new Robot();
            while (length > 0)
            {
                Thread.sleep(5);
                objPosition.add(motionVector);
                robot.mouseMove(objPosition.getX(), objPosition.getY());
                length -= deltaLength;
            }
            isEnd = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
