package utils;

import utils.engine.Point2D;

import java.awt.*;

/**
 * @author BingoIO_OI233
 */
public class MouseChaser implements Runnable
{
    private static final int TICK_TIME = 30;
    private boolean exit = false;
    public Point2D mousePosition = new Point2D();

    @Override
    public void run()
    {
        while (!exit)
        {
            try
            {
                Thread.sleep(TICK_TIME);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            PointerInfo pInfo = MouseInfo.getPointerInfo();
            mousePosition = new Point2D().generateFromPoint(pInfo.getLocation());
        }
    }

    public void stop()
    {
        this.exit = true;
    }

    public void reset()
    {
        this.exit = false;
    }

    public static Point2D getMousePos()
    {
        return new Point2D().generateFromPoint(MouseInfo.getPointerInfo().getLocation());
    }
}