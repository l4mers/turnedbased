package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args)  {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("WAJTE");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //LOADS
        //gamePanel.setupGame();
        gamePanel.startGameThread();

//        LoadAndSave las = new LoadAndSave();
//        Data save1 = new Data();
//        Data save2 = new Data();
//        Data save3 = new Data();
//
//        save1.setSlot(1);
//        save2.setSlot(2);
//        save3.setSlot(3);
//
//        las.test(save1);
//        las.test(save2);
//        las.test(save3);
    }
}