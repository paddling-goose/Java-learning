package main;

import Tile.TileManager;
import entity.Player;
import object.SuperObject;

import javax.swing.*;

import java.awt.*;

//子类，继承jpanel的功能
//一个容器，用于组织图标按键etc
//用作游戏屏幕
public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTitleSize = 16;  //16x16 tile(屏幕的每一小块的尺寸，复古2d标准）
    final int scale = 3;  // 16x16  -->  48x48

    public final int tileSize = originalTitleSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //SYSTEM
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    public UI ui = new UI(this);
    public TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread; /*keep the prog running, fps = 60 useful */

    //ENTITIES AND OBJECTS
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    //set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);/*this gamePanel can be focused to receive key input*/
    }

    //希望在游戏开始之前就调用
    public void setupGame(){

        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread(){

        gameThread = new Thread(this);  /* pass gamePanel to it, 实例化线程*/
        gameThread.start(); /*auto call run method*/

    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS ;
        double nextDrawTime = System.nanoTime() + drawInterval;   /*金手指调整时间的原理*/

        while(gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;    //if all the time is used, no need to sleep
                }

                Thread.sleep((long) remainingTime);//sleep default is ms, but we use nano.

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){

        player.update();

    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);  /*super 表示该类的父类，是JPanel*/

        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);

        //OBJECT
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2,this);
            }
        }

        // PLAYER
        player.draw(g2);

        //UI
        ui.draw(g2);

        g2.dispose();


    }

    public void playMusic(int i){

        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void playSE(int i){

        sound.setFile(i);
        sound.play();
    }
    public void stopMusic(){
        sound.stop();
    }
}
