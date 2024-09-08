package world;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class WorldPainter {

    private final boolean buildingTiles;

    public WorldPainter(boolean buildingTiles){
        this.buildingTiles = buildingTiles;
    }

    public void draw(Graphics2D g2, GamePanel gp, int[][] map, ArrayList<Tile> tiles) {
        int worldWidth = 0;
        int worldHeight = 0;

        while (worldWidth < gp.worldWidth && worldHeight < gp.worldHeight){
            int tileNumber = map[worldWidth][worldHeight];

            int worldX = worldWidth * gp.tileSize;
            int worldY = worldHeight * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                if(buildingTiles){
                    g2.drawImage(tiles.get(tileNumber).image, screenX, screenY, null);
                } else {
                    if (tileNumber != 0){
                        g2.drawImage(tiles.get(tileNumber).image, screenX, screenY, null);
                    }
                }
            }

            worldWidth++;
            if (worldWidth  == gp.worldWidth){
                worldWidth = 0;
                worldHeight++;
            }
        }
    }
}
