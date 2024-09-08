package gamestates;

import entities.Component;
import entities.Entity;
import enums.State;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Fight implements UpdateAndDraw{

    GamePanel gp;
    List<Component> cp;

    int playerWorldX;
    int playerWorldY;

    private String oldMapPath;
    private String nextMap = "src/resource/maps/battlefields/001.txt";

    int foeSprite = 0;
    boolean turn = false;

    public Fight(GamePanel gp, Entity entity) {
        this.gp = gp;
        this.cp = entity.component;
        cp.get(0).image = entity.spriteSheet;
        //gp.player.component.get(0).image = gp.player.spriteSheet;

        playerWorldX = gp.player.worldX;
        playerWorldY = gp.player.worldY;
        gp.player.worldX = gp.screenWidth / 2;
        gp.player.worldY = gp.screenHeight / 2;

        oldMapPath = gp.worldController.mapPath;
        gp.worldController.changeWorld(nextMap);

        gp.gameState = State.FIGHT_STATE;
    }

    @Override
    public void update() {
        if(gp.inputHandler.interact && turn){
            System.out.println("\n------------------------------------");
            System.out.println("Foe defence: " + cp.get(0).defence);
            System.out.println("Player attack: " + gp.player.component.get(0).attack);
            System.out.println("Foe health: " + cp.get(0).currentHealth);
            gp.player.component.get(0).dealDamage(cp.get(0));
            int damage = gp.player.component.get(0).attack - cp.get(0).defence;
            if(damage <= 0){
                damage = 1;
            }
            System.out.println("Foe damage taken: " + damage);
            System.out.println("Foe health: " + cp.get(0).currentHealth);
            System.out.println("------------------------------------\n");
            turn = false;
            gp.inputHandler.interact = false;
            foeSprite = 0;
        } else if (!turn){
            foeSprite++;
            if (foeSprite > 40){
                System.out.println("\n------------------------------------");
                System.out.println("Player defence: " + gp.player.component.get(0).defence);
                System.out.println("Foe attack: " + cp.get(0).attack);
                System.out.println("Player health: " + gp.player.component.get(0).currentHealth);
                cp.get(0).dealDamage(gp.player.component.get(0));
                int damage = gp.player.component.get(0).attack - cp.get(0).defence;
                if(damage <= 0){
                    damage = 1;
                }
                System.out.println("Player damage taken: " + damage);
                System.out.println("Player health: " + gp.player.component.get(0).currentHealth);
                System.out.println("------------------------------------\n");
                turn = true;
                foeSprite = 0;
            }
            gp.inputHandler.interact = false;
        }

        if(gp.player.component.get(0).currentHealth <= 0){
            System.out.println("You lost");
            gp.player.component.get(0).restore();
            cp.get(0).restore();
            end();
        }
        if(cp.get(0).currentHealth <= 0){
            System.out.println("\n------------------------------------");
            System.out.println("You win");
            System.out.println("Current level: " + gp.player.component.get(0).level);
            System.out.println("Current experience: "  + gp.player.component.get(0).experience + " / " + gp.player.component.get(0).nextLevel);
            System.out.println("You gain: " + cp.get(0).expGain + " experience");
            gp.player.component.get(0).gainExperience(cp.get(0).expGain);
            System.out.println("Experience now: " + gp.player.component.get(0).experience + " / " + gp.player.component.get(0).nextLevel);
            System.out.println("Player health restored");
            System.out.println("------------------------------------\n");
            gp.player.component.get(0).restore();
            cp.get(0).restore();
            end();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(getSprite(gp.player.component.get(0).image, gp.player.component.get(0).width, 5, 4),
                gp.tileSize, gp.screenHeight /2 - gp.tileSize,null);

        g2.drawImage(getSprite(cp.get(0).image, cp.get(0).width, 5, 5),
                gp.screenWidth - gp.tileSize * 2 - gp.tileSize/2, gp.screenHeight /2 - gp.tileSize,null);
    }

    private BufferedImage getSprite(BufferedImage spriteSheet, int size, int col, int row) {
        return UtilityTool.getSubImage(spriteSheet, col, row, size, size);
    }

    public void end() {
        gp.worldController.changeWorld(oldMapPath);
        gp.player.worldX = playerWorldX;
        gp.player.worldY = playerWorldY;
        gp.controller.setCurrentUpdateAndDraw(new Adventure(gp));
        gp.gameState = State.ADVENTURE_STATE;
    }
}
