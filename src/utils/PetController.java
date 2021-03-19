package utils;

import utils.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author BingoIO_OI233
 */
public class PetController implements Runnable
{
    private JLabel turretLabel;
    private JLabel baseLabel;
    private JFrame frame;
    private boolean isOnTask;
    private Point2D position;
    private ImageIcon turretHead = new ImageIcon("assets/UntitledTurret/turret.png");
    private static ImageIcon effectFireExplosion = new ImageIcon("assets/UntitledTurret/fire-explosion.gif");
    private static ImageIcon effectBubble = new ImageIcon("assets/UntitledTurret/bubble.gif");
    private static ImageIcon[] effectSmoke = new ImageIcon[2];
    private static ImageIcon effectWarning = new ImageIcon("assets/UntitledTurret/warn.jpg");
    private static ImageIcon effectFunny = new ImageIcon("assets/UntitledTurret/funny.gif");
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Font LABEL_FONT = new Font("宋体", Font.BOLD, 16);

    private MouseChaser mouse;
    private static int tickTime = 30;

    private enum PetTasks
    {
        /**
         * Aim pointer but do nothing
         */
        STANDBY,
        /**
         * Fire on the pointer
         */
        ATTACK,
        /**
         * Deploy smoke to pointer area, blocking pointer from accessing applications under smoke
         */
        SMOKE,
        WARNING,
        FUNNY
    }

    public PetController(JFrame mainframe, JLabel jLabelBase, JLabel jLabelMotion)
    {
        this.turretLabel = jLabelMotion;
        this.baseLabel = jLabelBase;
        this.frame = mainframe;
        petSetup();
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                chooseRandomTask();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void petSetup()
    {
        mouse = new MouseChaser();
        isOnTask = true;
        position = new Point2D().generateFromPoint(baseLabel.getLocation());
        //Image setup
        turretHead.setImage(turretHead.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        effectSmoke[0] = new ImageIcon("assets/UntitledTurret/smoke1.png");
        effectSmoke[1] = new ImageIcon("assets/UntitledTurret/smoke2.png");
        //turret head setup
        turretLabel.setBounds(position.getX(), position.getY(), 100, 100);
        turretLabel.setIcon(turretHead);
        position.add(50, 50);
        setTask(PetTasks.STANDBY);
    }

    private void setTask(PetTasks task)
    {
        switch (task)
        {
            case STANDBY:
                System.out.println("============\nstandby");
                taskStandBy();
                break;
            case ATTACK:
                System.out.println("============\nattack");
                turretSay("开炮!");
                taskAttack();
                break;
            case SMOKE:
                System.out.println("============\nsmoke");
                turretSay("SMOKE!");
                taskSmoke();
                break;
            case WARNING:
                System.out.println("============\nwarning");
                turretSay("爬去学！");
                taskWarning();
                break;
            case FUNNY:
                System.out.println("============\nfunny");
                turretSay("捣乱");
                taskFunny();
                break;
            default:
                break;
        }
    }

    private void chooseRandomTask()
    {
        setTask(Enums.random(PetTasks.class));
    }

    private void turretLock(Direction dir)
    {
        BufferedImage bffimage = new BufferedImage(100, 100,
                BufferedImage.TYPE_INT_ARGB);
        bffimage.getGraphics().drawImage(turretHead.getImage(), 0, 0, turretHead.getIconWidth(), turretHead.getIconHeight(), null);
        bffimage = Animator.rotateImage(bffimage, dir.getSpinAngle());
        Image realImage = bffimage;
        ImageIcon icon = new ImageIcon(realImage);
        turretLabel.setIcon(icon);
    }

    private void turretSay(String wordShort)
    {
        Point2D labelPos = new Point2D().generateFromPoint2D(position).add(5, -50 - 60);
        JLabel text = new JLabel(wordShort, JLabel.CENTER);
        /*
         * text label DEBUG:
         * text.setOpaque(true);
         * text.setBackground(new Color(232, 89, 123));
         */
        text.setLocation(labelPos.toPoint());
        text.setFont(LABEL_FONT);
        text.setSize(90, 60);
        frame.add(text);
        text.setVisible(false);
        frame.validate();
        frame.repaint();
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                text.setVisible(true);
                frame.validate();
                frame.repaint();
            }
        }, 800);
        setEffect(labelPos.add(45, 30), effectBubble, 2800);
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                frame.remove(text);
                frame.validate();
                frame.repaint();
            }
        }, 2600);
    }

    /**
     * use to set visual effect
     *
     * @param target               effect position
     * @param effect               effect ImageIcon Object (please use .gif)
     * @param effectDurationMillis effect duration in milliseconds
     */
    private void setEffect(Point2D target, ImageIcon effect, long effectDurationMillis)
    {
        JLabel effectLabel = new JLabel(effect);
        effectLabel.setBounds(target.getX() - effect.getIconWidth() / 2, target.getY() - effect.getIconHeight() / 2, effect.getIconWidth(), effect.getIconHeight());
        effectLabel.setIcon(effect);
        frame.add(effectLabel);
        frame.validate();
        frame.repaint();
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                frame.remove(effectLabel);
                frame.validate();
                frame.repaint();
            }
        }, effectDurationMillis);
    }

    /**
     * knock back user's pointer
     * knock back distance = 50000000 / [turret-pointer distance]^2
     */
    private void mouseKnockBack()
    {
        Vector2D knockBackVector = new Vector2D(MouseChaser.getMousePos())
                .minus(new Vector2D(position));
        double knockBackDistance = Math.min(50000000.0 / Math.pow(knockBackVector.scalar(), 2), 500);
        knockBackVector.scaleTo(knockBackDistance);
        Point2D mouseDist = MouseChaser.getMousePos().add(knockBackVector);

        MouseMotion m = new MouseMotion()
                .setStartPoint(MouseChaser.getMousePos())
                .setEndPoint(mouseDist.limitToAreaRect(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height))
                .setVelocity(20);

        new Thread(m).start();
    }

    /**
     * turret auto aim
     */
    public void taskStandBy()
    {
        mouse.reset();
        new Thread(mouse).start();
        long startTime = System.currentTimeMillis();
        int randDuration = MathTools.randNextInt(2, 4) * 1000;
        try
        {
            while (System.currentTimeMillis() - startTime < randDuration)
            {
                Point2D mousePos = mouse.mousePosition;
                Direction dir = new Direction(position, mousePos);
                turretLock(dir);
                Thread.sleep(tickTime);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            mouse.stop();
        }
    }

    public void taskAttack()
    {
        mouse.reset();
        new Thread(mouse).start();
        long startTime = System.currentTimeMillis();
        long lastShotTime = startTime;
        int randDuration = MathTools.randNextInt(3, 5) * 1000;
        try
        {
            while (System.currentTimeMillis() - startTime < randDuration)
            {
                Point2D targetPos = mouse.mousePosition;
                Direction dir = new Direction(position, targetPos);
                //炮塔朝向锁定
                turretLock(dir);
                if (System.currentTimeMillis() - lastShotTime > 1500)
                {
                    System.out.println("===>> fire");
                    lastShotTime = System.currentTimeMillis();
                    setEffect(targetPos, effectFireExplosion, 500);
                    mouseKnockBack();
                }
                Thread.sleep(tickTime);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            mouse.stop();
        }
    }

    public void taskSmoke()
    {
        mouse.reset();
        new Thread(mouse).start();
        long startTime = System.currentTimeMillis();
        long lastShotTime = startTime;
        int randDuration = MathTools.randNextInt(2, 4) * 1000;
        try
        {
            while (System.currentTimeMillis() - startTime < randDuration)
            {
                Point2D targetPos = mouse.mousePosition;
                Direction dir = new Direction(position, targetPos);
                //炮塔朝向锁定
                turretLock(dir);
                if (System.currentTimeMillis() - lastShotTime > 1500)
                {
                    System.out.println("===>> fire");
                    lastShotTime = System.currentTimeMillis();
                    if(new Vector2D(position,targetPos).scalar()>=200)
                    {
                        setEffect(targetPos, effectSmoke[MathTools.randNextInt(0,1)], 10000);
                    }
                }
                Thread.sleep(tickTime);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            mouse.stop();
        }
    }

    public void taskWarning()
    {
        mouse.reset();
        new Thread(mouse).start();
        long startTime = System.currentTimeMillis();
        long lastShotTime = startTime;
        int randDuration = MathTools.randNextInt(2, 4) * 1000;
        try
        {
            while (System.currentTimeMillis() - startTime < randDuration)
            {
                Point2D targetPos = mouse.mousePosition;
                Direction dir = new Direction(position, targetPos);
                //炮塔朝向锁定
                turretLock(dir);
                if (System.currentTimeMillis() - lastShotTime > 2000)
                {
                    System.out.println("===>> warning");
                    lastShotTime = System.currentTimeMillis();
                    if(new Vector2D(position,targetPos).scalar()>=200)
                    {
                        setEffect(targetPos, effectWarning, 5000);
                    }
                }
                Thread.sleep(tickTime);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            mouse.stop();
        }
    }

    public void taskFunny()
    {
        mouse.reset();
        new Thread(mouse).start();
        long startTime = System.currentTimeMillis();
        long lastShotTime = startTime;
        int randDuration = MathTools.randNextInt(2, 4) * 1000;
        try
        {
            while (System.currentTimeMillis() - startTime < randDuration)
            {
                Point2D targetPos = mouse.mousePosition;
                Direction dir = new Direction(position, targetPos);
                //炮塔朝向锁定
                turretLock(dir);
                if (System.currentTimeMillis() - lastShotTime > 1500)
                {
                    System.out.println("===>> funny");
                    lastShotTime = System.currentTimeMillis();
                    if(new Vector2D(position,targetPos).scalar()>=200)
                    {
                        setEffect(targetPos, effectFunny, 500);
                    }
                }
                Thread.sleep(tickTime);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            mouse.stop();
        }
    }
}
