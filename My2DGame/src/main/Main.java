package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //可以关闭窗口
        window.setResizable(false);  //可以设置窗口大小
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); /*top container & sub container*/

        window.pack();/*causes this window to be sized to fit the settings of subcomponent*/

        window.setLocationRelativeTo(null);   //不设置窗口出现的位置，默认居中
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
