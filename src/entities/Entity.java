package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Entity {

    public boolean isPlayer = false;

    public BufferedImage spriteSheet;
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);

    public int hitBoxDefaultX, hitBoxDefaultY;

    public boolean collision = false;

    public int worldX;
    public int worldY;

    public int spriteCount;
    public String direction = "south";

    protected String name;
    public int speed;

    public List<Component> component;

    public Entity(){

    }

    protected void setup(){

    }
}
