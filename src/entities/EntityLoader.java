package entities;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EntityLoader {

    public BufferedImage loadSpriteSheet(String spritePath, int width, int height){
        BufferedImage img = null;
        try{
            img = UtilityTool.scaleImage(ImageIO.read(getClass().getResource(spritePath)), width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return img;
    }

//    public ArrayList<Entity> loadAssets(int[][] map, int tileSize, String assetPath){
//        ArrayList<Entity> NPCs = new ArrayList<>();
//
//        int col = 0;
//        int row = 0;
//
//        while (col < map[0].length && row < map.length) {
//            int assetID = map[col][row];
//            int worldX = col * tileSize;
//            int worldY = row * tileSize;
//
//            switch (assetID) {
//                case 1 -> {
//                    Knight knight = new Knight();
//                    knight.worldX = worldX;
//                    knight.worldY = worldY;
//                    NPCs.add(knight);
//                }
//            }
//            col++;
//            if (col == map[0].length) {
//                col = 0;
//                row++;
//            }
//        }
//        return NPCs;
//    }

    public int[][] loadMap(String mapPath){
        return UtilityTool.loadMap(mapPath);
    }
}
