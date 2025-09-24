package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea =  new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = solidArea.x, solidAreaDefaultY = solidArea.y;

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY; //确保任务绘制在屏幕中心

        if(worldX > gp.player.worldX - gp.player.screenX -gp.tileSize &&
                worldX < gp.player.worldX + gp.player.screenX +gp.tileSize &&
                worldY  > gp.player.worldY - gp.player.screenY -gp.tileSize &&
                worldY < gp.player.worldY + gp.player.screenY +gp.tileSize){
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

        }
    }
}
