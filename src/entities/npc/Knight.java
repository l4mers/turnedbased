package entities.npc;

import entities.Entity;
import entities.EntityLoader;
import entities.component.BlueKnight;
import main.GamePanel;

import java.util.ArrayList;

public class Knight extends Entity {

    public Knight(GamePanel gp, int x, int y){
        spriteSheet = new EntityLoader().loadSpriteSheet(
                "/characters/npc/blue_knight.png",
                gp.playerSize * 6,
                gp.playerSize * 6);

        worldY = y;
        worldX = x;

        component = new ArrayList<>();
        component.add(new BlueKnight());
        component.get(0).width = gp.playerSize;
        component.get(0).height = gp.playerSize;

        //HIT BOX
        hitBox.x = 28;
        hitBox.y = gp.playerSize / 4;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 40;
        hitBox.height = 40;
        setup();
    }
    protected void setup() {
        direction = "west";
        speed = 1;
    }
}
