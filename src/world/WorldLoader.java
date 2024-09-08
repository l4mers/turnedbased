package world;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class WorldLoader {

    public ArrayList<Tile> loadAssets(int width, int height, String assetPath){
        ArrayList<Tile> tiles = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(assetPath),"*.{png}")){
            for (Path file: stream) {
                if (file.toString().endsWith("c.png")){
                    tiles.add(new Tile(loadImage(file.toString(), width, height), true));
                }else{
                    tiles.add(new Tile(loadImage(file.toString(), width, height), false));
                }
            }
        }catch (IOException | DirectoryIteratorException e){
            e.printStackTrace();
        }
        return tiles;
    }

    public int[][] loadMap(String mapPath){
        return UtilityTool.loadMap(mapPath);
    }
    private BufferedImage loadImage(String path, int width, int height){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        }catch (IOException e){
            e.printStackTrace();
        }
        return UtilityTool.scaleImage(image, width, height);
    }
}
