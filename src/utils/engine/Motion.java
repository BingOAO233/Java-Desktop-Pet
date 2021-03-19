package utils.engine;

/**
 * @author BingoIO_OI233
 */
public class Motion implements Runnable
{
    private double velocity;
    private Point2D start, end;
    private Point2D objPosition;
    public boolean isEnd = false;

    public Motion(Point2D objectPos)
    {
        this.start = objectPos;
        this.objPosition = objectPos;
    }

    public Motion setEndPoint(Point2D end)
    {
        this.end = end;
        return this;
    }

    public Motion setVelocity(double velocity)
    {
        this.velocity = velocity;
        return this;
    }

    @Override
    public void run()
    {
        //位移向量
        Vector2D motionVector = new Vector2D(end)
                .minus(new Vector2D(start));

        //距离
        double length = motionVector.scalar();

        //单位化
        motionVector = motionVector.multiple(velocity / length);

        //单位化
        double deltaLength = motionVector.scalar();

        try
        {
            while (length > 0)
            {
                Thread.sleep(5);
                objPosition.add(motionVector);
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
