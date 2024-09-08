package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class UtilityTool {
    static public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    static public BufferedImage getSubImage(BufferedImage sprite, int col, int row, int width, int height){
        return sprite.getSubimage((col * width) - width, (row * height) - height, width, height);
    }

    static public int[][] loadMap(String mapPath){
        int[][] world = getMapWidthAndHeight(mapPath);
        int height = 0;
        try(Stream<String> stream = Files.lines(Paths.get(mapPath))){
            for(String line : (Iterable<String>) stream::iterator){
                String[] tileID = line.split(" ");
                int width = 0;
                for (String id : tileID) {
                    int n = Integer.parseInt(id);
                    world[width][height] = n;
                    width++;
                }
                height++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return world;
    }

    static private int[][] getMapWidthAndHeight(String mapPath){
        try(Stream<String> stream = Files.lines(Paths.get(mapPath))){
            List<String> list = stream
                    .map(s -> s.replace(" ", ""))
                    .toList();
            return new int[list.get(0).length()][list.size()];
        } catch (IOException e){
            e.printStackTrace();
        }
        return new int[50][50];
    }

    static public Font loadFont(String fontPath){
        try(InputStream stream = UtilityTool.class.getResourceAsStream(fontPath)){
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        }catch(FontFormatException | IOException e){
            e.printStackTrace();
        }
        return new Font("Times Roman", Font.PLAIN,Font.BOLD);
    }
}
