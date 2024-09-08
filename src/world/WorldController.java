package world;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.util.ArrayList;

public class WorldController {
    GamePanel gp;

    public String mapPath = "src/resource/maps/worlds/001.txt";
    public int[][] tileMap;
    public ArrayList<Tile> tiles;

//    public int[][] objectMap;
//    public ArrayList<Tile> objects;

    private final WorldPainter tileBuilder;
    //private final WorldPainter objectBuilder;

    public WorldController(GamePanel gp){
        this.gp = gp;
        tileBuilder = new WorldPainter(true);
        //objectBuilder = new WorldPainter(false);
    }
    public void setup() {
        tileMap = UtilityTool.loadMap(mapPath);
        tiles = new WorldLoader().loadAssets(gp.tileSize, gp.tileSize, "src/resource/worldtiles");
        gp.worldWidth = tileMap[0].length;
        gp.worldHeight = tileMap.length;
    }

    public void changeWorld(String mapPath){
        this.mapPath = mapPath;
        tileMap = UtilityTool.loadMap(mapPath);
        gp.worldWidth = tileMap[0].length;
        gp.worldHeight = tileMap.length;
        if (tiles.size() != 0){
            tiles = new WorldLoader().loadAssets(gp.tileSize, gp.tileSize, "src/resource/worldtiles");
        }
    }

    public void draw(Graphics2D g2){
        tileBuilder.draw(g2, gp, tileMap, tiles);
    }

}
