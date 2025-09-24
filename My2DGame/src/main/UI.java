package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage key_image;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat df = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("arial", Font.PLAIN, 40);
        arial_80B = new Font("arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        key_image =  key.image;
    }

    public void showMessage(String message) {

        this.message = message;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if(gameFinished) {

            String text;
            int textLength;
            int x;
            int y;


            // Text1
            g2.setFont(arial_40);
            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - gp.tileSize * 3;
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // PLAYTIME
            g2.setColor(Color.white);
            text = "Your time is:" + df.format(playTime) + "!";

            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + gp.tileSize * 3;
            g2.drawString(text, x, y);

            // Text2
            g2.setColor(Color.yellow);
            g2.setFont(arial_80B);
            text = "Congratulation!!";

            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + gp.tileSize * 2;
            g2.drawString(text, x, y);

            // END THE GAME
            gp.gameThread = null;

        }else{

            // KEY
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(key_image, 10, 10,gp.tileSize,gp.tileSize, null);
            g2.drawString(" =" + gp.player.hasKey,50,50 );

            // TIME
            playTime += (double)1/60;
            g2.drawString("Time:" + df.format(playTime),gp.tileSize*11,50);

            // MESSAGE
            if(messageOn) {
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message,2,gp.screenHeight/2);

                messageCounter++;

                if(messageCounter> 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }


    }
}
