package interact;

import entities.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class CollisionValidator {

    public boolean checkTile(Entity entity, GamePanel gp){

        int mobileLeftWorldX = entity.worldX + entity.hitBox.x;
        int mobileRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int mobileTopWorldY = entity.worldY + entity.hitBox.y;
        int mobileBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int hitBoxWest = mobileLeftWorldX / gp.tileSize;
        int hitBoxEast = mobileRightWorldX / gp.tileSize;
        int hitBoxNorth = mobileTopWorldY / gp.tileSize;
        int hitBoxSouth = mobileBottomWorldY / gp.tileSize;

        //int tileNumOne, tileNumTwo;

        switch (entity.direction){
            case "north" -> {
                return tileCollisionNS((mobileTopWorldY - entity.speed) / gp.tileSize,
                        hitBoxWest, hitBoxEast, gp);
            }
            case "south" ->{
                return tileCollisionNS((mobileBottomWorldY + entity.speed) / gp.tileSize,
                        hitBoxWest, hitBoxEast, gp);
            }
            case "west" ->{
                return tileCollisionEW((mobileLeftWorldX - entity.speed) / gp.tileSize,
                        hitBoxNorth, hitBoxSouth, gp);
            }
            default ->{
                return tileCollisionEW((mobileRightWorldX - entity.speed) / gp.tileSize,
                        hitBoxNorth, hitBoxSouth, gp);
            }
        }
    }
    private boolean tileCollisionNS(int hitBox,int hitBox2,int hitBox3, GamePanel gp){
        int tileNumOne = gp.worldController.tileMap[hitBox2][hitBox];
        int tileNumTwo = gp.worldController.tileMap[hitBox3][hitBox];
        return gp.worldController.tiles.get(tileNumOne).collision || gp.worldController.tiles.get(tileNumTwo).collision;
    }
    private boolean tileCollisionEW(int hitBox,int hitBox2,int hitBox3, GamePanel gp){
        int tileNumOne = gp.worldController.tileMap[hitBox][hitBox2];
        int tileNumTwo = gp.worldController.tileMap[hitBox][hitBox3];
        return gp.worldController.tiles.get(tileNumOne).collision || gp.worldController.tiles.get(tileNumTwo).collision;
    }

    public boolean checkPlayer(Entity entity, GamePanel gp){

        boolean contactPlayer = false;

        //player hit box
        entity.hitBox.x = entity.worldX + entity.hitBox.x;
        entity.hitBox.y = entity.worldY + entity.hitBox.y;
        //mobiles hit box
        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;

        setHitBox(entity);

        if(entity.hitBox.intersects(gp.player.hitBox)){
            contactPlayer = true;
            gp.player.collision = true;
        }

        entity.hitBox.x = entity.hitBoxDefaultX;
        entity.hitBox.y = entity.hitBoxDefaultY;
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;

        return contactPlayer;
    }
    public int checkMobile(Entity entity, ArrayList<Entity> target){

        int index = 999;

        for (int i = 0; i < target.size(); i++) {

            if(target.get(i) != null){

                //player hit box
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;
                //mobiles hit box
                target.get(i).hitBox.x = target.get(i).worldX + target.get(i).hitBox.x;
                target.get(i).hitBox.y = target.get(i).worldY + target.get(i).hitBox.y;

                setHitBox(entity);

                if(entity.hitBox.intersects(target.get(i).hitBox)){
                    //Om mobilen inte är sig själv ingen collision
                    if (target.get(i) != entity) {
                        entity.collision = true;
                        index = i;
                    }
                }
                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                target.get(i).hitBox.x = target.get(i).hitBoxDefaultX;
                target.get(i).hitBox.y = target.get(i).hitBoxDefaultY;
            }
        }
        return index;
    }

    private void setHitBox(Entity entity) {
        switch(entity.direction){
            case "north" -> entity.hitBox.y -= entity.speed;
            case "south" -> entity.hitBox.y += entity.speed;
            case "west" -> entity.hitBox.x -= entity.speed;
            case "east" ->entity.hitBox.x += entity.speed;
        }
    }
}
