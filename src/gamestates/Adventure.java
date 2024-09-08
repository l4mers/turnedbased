package gamestates;

import entities.Entity;
import interact.CollisionValidator;
import interact.EventHandler;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;

public class Adventure implements UpdateAndDraw{

    private final GamePanel gp;

    CollisionValidator collisionValidator = new CollisionValidator();
    EventHandler eventHandler = new EventHandler();

    public Adventure(GamePanel gp) {
        this.gp = gp;
        gp.inputHandler.select = false;
    }

    @Override
    public void update() {
        //MENU
        if(gp.inputHandler.select){
            gp.controller.setCurrentUpdateAndDraw(new GameMenu(gp));
        }
        //PLAYER UPDATE
        playerUpdate();
        //NPC UPDATE
        entityUpdate();
    }

    private void playerUpdate() {
        gp.player.spriteCount++;
        if(gp.inputHandler.northPressed ||
                gp.inputHandler.southPressed ||
                gp.inputHandler.westPressed ||
                gp.inputHandler.eastPressed ||
                gp.inputHandler.interact){
            setDirection();
            gp.player.collision = false;
            int index = collisionValidator.checkMobile(gp.player, gp.entityList);
            //gp.player.collision = collisionValidator.checkTile(gp.player, gp);
            if(!gp.player.collision){
                walk();
            } else if (gp.inputHandler.interact){
                eventHandler.interactWithNPC(index, gp);
            }

//            if(!gp.player.collision){
//                gp.player.collision = collisionValidator.checkTile(gp.player, gp);
//                if(!gp.player.collision){
//                    walk();
//                }
//            }
        }
        if(gp.player.spriteCount > 48){
            gp.player.spriteCount = 0;
        }
        gp.inputHandler.interact = false;
    }

    private void walk() {
        switch(gp.player.direction){
            case "north" -> gp.player.worldY -= gp.player.speed;
            case "south" -> gp.player.worldY += gp.player.speed;
            case "west" -> gp.player.worldX -= gp.player.speed;
            default -> gp.player.worldX += gp.player.speed;
        }
    }

    private void setDirection() {
        if (gp.inputHandler.northPressed) {
            gp.player.direction = "north";
        } else if (gp.inputHandler.southPressed) {
            gp.player.direction = "south";
        } else if (gp.inputHandler.westPressed) {
            gp.player.direction = "west";
        } else if (gp.inputHandler.eastPressed) {
            gp.player.direction = "east";
        }
    }

    private void entityUpdate() {
        gp.entityList.forEach(entity ->{
            if(!entity.isPlayer) {
                entity.spriteCount++;
                if (entity.spriteCount > 48) {
                    entity.spriteCount = 0;
                }
            }
        });
    }

    @Override
    public void draw(Graphics2D g2) {

        gp.entityList.sort(Comparator.comparingInt(entity -> entity.worldY));

        gp.entityList.forEach(entity -> {
            if(entity.isPlayer){
                g2.drawImage(drawPlayer(), gp.player.screenX, gp.player.screenY,null);
                g2.drawRect(gp.player.screenX + gp.player.hitBox.x,
                        gp.player.screenY + gp.player.hitBox.y,
                        gp.player.hitBox.width,
                        gp.player.hitBox.height);
            } else if (entityInScreen(entity)){
                g2.drawImage(drawEntity(entity), entity.worldX - gp.player.worldX + gp.player.screenX,
                        entity.worldY - gp.player.worldY + gp.player.screenY,null);
                g2.drawRect(entity.worldX - gp.player.worldX + gp.player.screenX+ entity.hitBox.x,
                        entity.worldY - gp.player.worldY + gp.player.screenY + entity.hitBox.y,
                        entity.hitBox.width,
                        entity.hitBox.height);
            }
        });
    }

    private BufferedImage drawEntity(Entity entity) {
        switch (entity.direction) {
            case "north" -> {
                return getSprite(entity,  5, 6);
            }
            case "south" -> {
                return getSprite(entity,  5, 2);
            }
            case "west" -> {
                return getSprite(entity,  5, 5);
            }
            default -> {
                return getSprite(entity,  2, 6);
            }
        }
    }

    private BufferedImage drawPlayer() {
        switch (gp.player.direction){
            case "north" ->{
                if (gp.player.spriteCount < 12){
                    return getSprite(gp.player, 2, 5);
                } else if (gp.player.spriteCount < 24){
                    return getSprite(gp.player, 3, 5);
                } else if (gp.player.spriteCount < 36){
                    return getSprite(gp.player, 4, 5);
                } else {
                    return getSprite(gp.player, 3, 5);
                }
            }
            case "south" ->{
                if (gp.player.spriteCount < 12){
                    return getSprite(gp.player, 3, 3);
                } else if (gp.player.spriteCount < 24){
                    return getSprite(gp.player, 4, 3);
                } else if (gp.player.spriteCount < 36){
                    return getSprite(gp.player, 5, 3);
                } else {
                    return getSprite(gp.player, 4, 3);
                }
            }
            case "west" ->{
                if (gp.player.spriteCount < 12){
                    return getSprite(gp.player, 1, 4);
                } else if (gp.player.spriteCount < 24){
                    return getSprite(gp.player, 2, 4);
                } else if (gp.player.spriteCount < 36){
                    return getSprite(gp.player, 3, 4);
                } else {
                    return getSprite(gp.player, 2, 4);
                }
            }
            default -> {
                if (gp.player.spriteCount < 12){
                    return getSprite(gp.player, 4, 4);
                } else if (gp.player.spriteCount < 24){
                    return getSprite(gp.player, 5, 4);
                } else if (gp.player.spriteCount < 36){
                    return getSprite(gp.player, 1, 5);
                } else {
                    return getSprite(gp.player, 5, 4);
                }
            }
        }
    }

    private boolean entityInScreen(Entity entity){
        return entity.worldX + gp.tileSize * 2 > gp.player.worldX - gp.player.screenX &&
                entity.worldX - gp.tileSize * 2 < gp.player.worldX + gp.player.screenX &&
                entity.worldY + gp.tileSize * 2 > gp.player.worldY - gp.player.screenY &&
                entity.worldY - gp.tileSize * 2 < gp.player.worldY + gp.player.screenY;
    }

    private BufferedImage getSprite(Entity player, int col, int row) {
        return UtilityTool.getSubImage(player.spriteSheet, col, row, gp.playerSize, gp.playerSize);
    }
}
