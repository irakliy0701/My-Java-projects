package Registration_Form;

import javax.swing.*;
import java.awt.*;

public class Helpform extends JFrame {
    private JPanel HeadPnl;
    private JPanel MiddlePnl;
    private JPanel TailPnl;
    private JLabel Support;
    private JLabel Middle_Icons;
    private JButton LeaveMessageButton;
    private JPanel MainHelpPnl;
    private JTextField SupportTextField;
    private JFrame jFrame;

    public Helpform(){
        jFrame = new JFrame("Helpform");
        jFrame.setTitle("");

//        Image im = new ImageIcon("img/Registration/HeadPanel/jetBrains_icon.png").getImage();
//        jFrame.setIconImage(im);

//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(300, 400));
        jFrame.setBounds(1481, 625, 0, 0);
        jFrame.setResizable(false);

        jFrame.add(MainHelpPnl);
        jFrame.pack();
 
        LeaveMessageButton.setFocusable(false);

        JPopupMenu jPopupMenu = new JPopupMenu();
//        Font font = new Font("Arial Unicode MS", Font.BOLD, 14);
//        jPopupMenu.setFont(font);
        jPopupMenu.setBorderPainted(true);

        JMenuItem j1 = jPopupMenu.add(new JMenuItem("Назад"));

        JMenuItem j2 = jPopupMenu.add(new JMenuItem("Вперед"));

        JMenuItem j3 =jPopupMenu.add(new JMenuItem("Перегрузить"));
        j3.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        jPopupMenu.addSeparator();

        JMenuItem j4 =jPopupMenu.add(new JMenuItem("Сохранить как..."));
        j4.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        JMenuItem j5 =jPopupMenu.add(new JMenuItem("Печать..."));
        j5.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));

        JMenuItem j6 =jPopupMenu.add(new JMenuItem("Трансляция..."));
        jPopupMenu.addSeparator();

        JMenuItem j7 =jPopupMenu.add(new JMenuItem("Создать QR-код для этой страницы",
                new ImageIcon("F://studies//Java_learn//MyProjects//GUI_SwingProjects//Password _interceptor_uni//src//img//QR-code.png")));
        jPopupMenu.addSeparator();

        JMenuItem j8 =jPopupMenu.add(new JMenuItem("Перевести на русский"));
        jPopupMenu.addSeparator();

        JMenuItem j9 =jPopupMenu.add(new JMenuItem("Просмотр кода страницы"));
        j9.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));

        JMenuItem j10 =jPopupMenu.add(new JMenuItem("Посмотреть код"));


        MainHelpPnl.setComponentPopupMenu(jPopupMenu);

        jFrame.setVisible(true);
    }
}
