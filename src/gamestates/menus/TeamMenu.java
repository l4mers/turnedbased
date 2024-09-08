package gamestates.menus;

import entities.Component;
import gamestates.GameMenu;
import gamestates.UpdateAndDraw;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;

public class TeamMenu implements UpdateAndDraw {

    private final GamePanel gp;
    private int menuChoice = 1;
    private final MenuWindow menuWindow;

    public TeamMenu(GamePanel gp) {
        this.gp = gp;
        this.menuWindow = new MenuWindow();

    }


    @Override
    public void update() {
        if(gp.inputHandler.southPressed){
            menuChoice ++;
            if (menuChoice > gp.player.component.size() -1){
                menuChoice = gp.player.component.size() -1;
            }
            gp.inputHandler.southPressed = false;
        } else if (gp.inputHandler.northPressed){
            menuChoice --;
            if (menuChoice < 0){
                menuChoice = 0;
            }
            gp.inputHandler.northPressed = false;
        } else if (gp.inputHandler.interact){
            //use
            //gp.player.inventory.get(menuChoice);
            gp.inputHandler.interact = false;
        } else if (gp.inputHandler.select){
            gp.inputHandler.select = false;
            gp.controller.setCurrentUpdateAndDraw(new GameMenu(gp));
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        menuWindow.drawWindow(g2,Color.darkGray ,0,0,gp.screenWidth, gp.screenHeight,"Team", gp.tileSize + gp.tileSize/2);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        int x = gp.tileSize / 4;
        int y = gp.tileSize * 2 + gp.tileSize / 2;

        for (int i = 0; i < gp.player.component.size() - 1 ; i++) {
            Component comp = gp.player.component.get(i);

            g2.drawImage(UtilityTool.getSubImage(gp.player.spriteSheet, 4, 3, gp.playerSize, gp.playerSize),
                    x, y, gp.tileSize*2, gp.tileSize*2, null);
            x += gp.tileSize * 2;
            g2.drawString(comp.name + "   Lv. " + comp.level , x, y);
            y += gp.tileSize / 2;
            g2.drawString("HP:", x, y);
            x += gp.tileSize - gp.tileSize / 3;
            g2.drawRect(x, y - 20, gp.tileSize * 4, gp.tileSize / 3);
            if(comp.currentHealth > 0){
                g2.fillRect(x + 2, y - 18, ((gp.tileSize * 4) / comp.health) * comp.currentHealth - 1, gp.tileSize / 3 - 3);
            }
            x -= gp.tileSize - gp.tileSize / 3;
            y += gp.tileSize / 2;
            g2.drawString("Def " + comp.defence + ". Att " + comp.attack + ". M.Def " + comp.magicDefence + ". M.Att " + comp.magicAttack
            + ".", x, y);
            y += gp.tileSize / 2;
            g2.drawString("XP:", x, y);
            x += gp.tileSize - gp.tileSize / 3;
            g2.drawRect(x, y - 20, gp.tileSize * 4, gp.tileSize / 3);
            if(comp.experience > 0){
                g2.fillRect(x + 2, y - 18, ((gp.tileSize * 4) / comp.nextLevel) * comp.experience - 1, gp.tileSize / 3 - 3);

            }
            x = gp.tileSize / 4;
            y += gp.tileSize;
        }
    }
}
