import utils.PetController;
import utils.engine.MathTools;
import utils.engine.Point2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author BingoIO_OI233
 */
public class UIManager
{
    private JFrame frame;
    private JLabel baseLabel = new JLabel();
    private JLabel animationLabel = new JLabel();
    private PetController controller;
    private Thread animationThread;
    private static final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    public UIManager()
    {
        frame = new JFrame("DesktopPet");
        System.out.println(dimension);
        frame.setLocation(0, 0);
        frame.setAlwaysOnTop(true);

        frame.setSize(dimension.width, dimension.height);
        frame.getContentPane().setLayout(null);
        frame.setTitle("DesktopPet");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setType(JFrame.Type.UTILITY);

        Point2D randPoint2D = new Point2D(
                MathTools.randNextInt(
                        (int) (dimension.getWidth() * 0.33),
                        (int) (dimension.getWidth() * 0.66)
                ), MathTools.randNextInt(
                (int) (dimension.getHeight() * 0.33),
                (int) (dimension.getHeight() * 0.66)
        ));
        baseLabel.setLocation(randPoint2D.toPoint());
        animationLabel.setLocation(randPoint2D.toPoint());
        setPetBase(randPoint2D);
        frame.add(animationLabel);
        frame.add(baseLabel);

        setPetTray();

        //背景透明
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, true));
        frame.setVisible(true);

        controller = new PetController(frame, baseLabel, animationLabel);
        animationThread = new Thread(controller);
        animationThread.start();
    }

    private void setPetBase(Point2D pos)
    {
        ImageIcon icon = new ImageIcon("assets/UntitledTurret/turret_bottom.png");
        int picWidth = 100;
        int picHeight = 100;
        icon.setImage(icon.getImage().getScaledInstance(picWidth, picHeight, Image.SCALE_DEFAULT));
        baseLabel.setBounds(pos.getX(), pos.getY(), picWidth, picHeight);
        baseLabel.setIcon(icon);
    }

    private void setPetTray()
    {
        if (SystemTray.isSupported())
        {
            SystemTray traySet = SystemTray.getSystemTray();
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exit = new MenuItem("piss off you idiot!!");
            exit.addActionListener(e -> System.exit(0));
            popupMenu.add(exit);

            ImageIcon icon = new ImageIcon("girl1.png");
            Image image = icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT);

            TrayIcon trayIcon = new TrayIcon(image, "就是这家伙在搞事", popupMenu);
            trayIcon.setImageAutoSize(true);

            try
            {
                traySet.add(trayIcon);
            }
            catch (AWTException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        new UIManager();
    }
}