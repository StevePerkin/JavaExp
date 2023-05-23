package ui;

import edu.hitsz.application.game.DifficultGame;
import edu.hitsz.application.game.EasyGame;
import edu.hitsz.application.game.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.game.NormalGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class StartMenu {
    private JButton easyMode;
    private JButton usualMode;
    private JButton difficultMode;
    private JPanel mainPanel;
    private JPanel easyPanel;
    private JPanel soundPanel;
    private JPanel usualPanel;
    private JPanel difficultPanel;
    private JComboBox sound;
    private JPanel stk1;
    private JPanel stk4;
    private JPanel stk3;
    private JPanel stk2;
    public static String file_name;
    public boolean isMusicOpen=true;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new StartMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public static String getFile_name(){
        return file_name;
    }
    public boolean isMusicOpen(){
        return isMusicOpen;
    }
    public StartMenu() {

        easyMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game=new EasyGame();
                file_name="rank.dot";
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                }catch (IOException e2) {
                    e2.printStackTrace();
                    System.exit(-1);
                }
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });
        usualMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game=new NormalGame();
                file_name="rank2.dot";
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                }catch (IOException e2) {
                    e2.printStackTrace();
                    System.exit(-1);
                }
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });
        difficultMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game=new DifficultGame();
                file_name="rank3.dot";
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
                }catch (IOException e2) {
                    e2.printStackTrace();
                    System.exit(-1);
                }
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });

        sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMusicOpen= sound.getItemAt(0).equals(sound.getSelectedItem());
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
