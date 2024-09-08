package entities.player;

import entities.Component;
import entities.Entity;
import entities.EntityLoader;
import item.Item;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    public int screenX;
    public int screenY;
    //private final int playerScale = 3;
    //public final int playerSize = 32 * playerScale;
    public List<Item> inventory = new ArrayList<>();


    public Player(GamePanel gp){
        isPlayer = true;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        spriteSheet = new EntityLoader().loadSpriteSheet(
                "/characters/knight/sprite_sheet.png",
                gp.playerSize * 5,
                gp.playerSize * 5);

        component = new ArrayList<>();
        component.add(new Component(
                "/characters/knight/sprite_sheet.png",
                "Adaman", 12, 2, 2, 2, 3));

        component.get(0).width = gp.playerSize;
        component.get(0).height = gp.playerSize;

        component.add(new Component(
                "/characters/knight/sprite_sheet.png",
                "Adaman", 12, 2, 2, 2, 3));

        component.get(1).width = gp.playerSize;
        component.get(1).height = gp.playerSize;

        component.add(new Component(
                "/characters/knight/sprite_sheet.png",
                "Adaman", 12, 2, 2, 2, 3));

        component.get(2).width = gp.playerSize;
        component.get(2).height = gp.playerSize;

        component.add(new Component(
                "/characters/knight/sprite_sheet.png",
                "Adaman", 12, 2, 2, 2, 3));

        component.get(3).width = gp.playerSize;
        component.get(3).height = gp.playerSize;
        setup(gp.playerSize);
    }

    protected void setup(int playerSize) {
        //spawn
        worldX = 100;
        worldY = 100;

        //HIT BOX
        hitBox.x = 28;
        hitBox.y = playerSize / 4;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 38;
        hitBox.height = 38;

        speed = 3;

        inventory.add(new Item("BEAR", true));
        inventory.add(new Item("MJÖD", true));
        inventory.add(new Item("BRÖD", true));
        inventory.add(new Item("PASTA", true));
        inventory.add(new Item("GRÅSTARR", true));

        component.get(0).image = spriteSheet;
    }
}
