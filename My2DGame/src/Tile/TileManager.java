package Tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap(); //顺序
    }

    public void getTileImage(){

        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass01.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water01.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road00.png"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(){

        try{
            InputStream is = getClass().getResourceAsStream("/res/maps/world01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col =0;
            int row =0;

            while(col< gp.maxWorldCol && row< gp.maxWorldRow){

                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]); //将其从字符串改为整数

                    mapTileNum[row][col] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col =0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

//        g2.drawImage(tile[0].image, 0, 0, gp.tileSize,gp.tileSize, null);
//        g2.drawImage(tile[1].image, 48, 0, gp.tileSize,gp.tileSize, null);
//        g2.drawImage(tile[2].image, 96, 0, gp.tileSize,gp.tileSize, null);

       int tileNum = 0;

       for(int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++){
           for(int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++){
               tileNum = mapTileNum[worldRow][worldCol];

               int worldX = worldCol * gp.tileSize;
               int worldY = worldRow * gp.tileSize;
               int screenX = worldX - gp.player.worldX + gp.player.screenX;
               int screenY = worldY - gp.player.worldY + gp.player.screenY; //确保人物绘制在屏幕中心

               if(worldX > gp.player.worldX - gp.player.screenX -gp.tileSize &&
                       worldX < gp.player.worldX + gp.player.screenX +gp.tileSize &&
                       worldY  > gp.player.worldY - gp.player.screenY -gp.tileSize &&
                       worldY < gp.player.worldY + gp.player.screenY +gp.tileSize){
                   g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
               }
           }
       }
    }
}
