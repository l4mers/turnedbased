package gamestates.menus;

import gamestates.GameMenu;
import gamestates.UpdateAndDraw;
import main.GamePanel;

import java.awt.*;

public class ItemMenu implements UpdateAndDraw {

    private final GamePanel gp;

    private int menuChoice = 0;
    private final MenuWindow menuWindow;

    public ItemMenu(GamePanel gp){
        this.gp = gp;
        menuWindow = new MenuWindow();
    }

    @Override
    public void update() {
        if(gp.inputHandler.southPressed){
            menuChoice ++;
            if (menuChoice > gp.player.inventory.size() -1){
                menuChoice = gp.player.inventory.size() -1;
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
        menuWindow.drawWindow(g2,Color.lightGray ,gp.screenWidth - gp.screenWidth / 3,0,gp.screenWidth / 3, gp.screenHeight,"", gp.tileSize);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));


        if (gp.player.inventory.size() != 0){

            int x = gp.screenWidth - (gp.screenWidth / 3) + gp.tileSize / 2;
            int y = gp.screenWidth / 2 - gp.tileSize * 2;

            int width = gp.tileSize * 4 + gp.tileSize / 2;

            g2.drawRect(x - 5, y - getTextHeight(g2, "Items") + 5,width , getTextHeight(g2, "Items"));

            switch (menuChoice){
                case 0 -> {
                    g2.drawString(gp.player.inventory.get(0).getName(), x + 10, y);
                    if(gp.player.inventory.size() > 1) {
                        y += gp.tileSize;
                        g2.drawString(gp.player.inventory.get(1).getName(), x, y);
                        if(gp.player.inventory.size() > 2) {
                            y += gp.tileSize;
                            g2.drawString(gp.player.inventory.get(2).getName(), x, y);
                        }
                    }
                }
                case 1 -> {
                    g2.drawString(gp.player.inventory.get(1).getName(), x + 10, y);
                    y -= gp.tileSize;
                    g2.drawString(gp.player.inventory.get(0).getName(), x, y);
                    if(gp.player.inventory.size() > 2) {
                        y += gp.tileSize * 2;
                        g2.drawString(gp.player.inventory.get(2).getName(), x, y);
                        if(gp.player.inventory.size() > 3) {
                            y += gp.tileSize;
                            g2.drawString(gp.player.inventory.get(3).getName(), x, y);
                        }
                    }
                }
                case 2 ->{
                    g2.drawString(gp.player.inventory.get(2).getName(), x + 10, y);
                    y -= gp.tileSize * 2;
                    g2.drawString(gp.player.inventory.get(0).getName(), x, y);
                    y += gp.tileSize;
                    g2.drawString(gp.player.inventory.get(1).getName(), x, y);
                    if(gp.player.inventory.size() > 3) {
                        y += gp.tileSize * 2;
                        g2.drawString(gp.player.inventory.get(3).getName(), x, y);
                        if(gp.player.inventory.size() > 4) {
                            y += gp.tileSize;
                            g2.drawString(gp.player.inventory.get(4).getName(), x, y);
                        }
                    }
                }
                default ->{
                    if (menuChoice == gp.player.inventory.size() -1){
                        g2.drawString(gp.player.inventory.get(menuChoice).getName(), x + 10, y);
                        y -= gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice -1).getName(), x, y);
                        y -= gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice -2).getName(), x, y);
                    } else if (menuChoice == gp.player.inventory.size() -2){
                        g2.drawString(gp.player.inventory.get(menuChoice).getName(), x + 10, y);
                        y -= gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice -1).getName(), x, y);
                        y -= gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice -2).getName(), x, y);
                        y += gp.tileSize * 3;
                        g2.drawString(gp.player.inventory.get(menuChoice +1).getName(), x, y);
                    } else {
                        y -= gp.tileSize * 2;
                        g2.drawString(gp.player.inventory.get(menuChoice - 2).getName(), x, y);
                        y += gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice - 1).getName(), x, y);
                        y += gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice).getName(), x + 10, y);
                        y += gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice + 1).getName(), x, y);
                        y += gp.tileSize;
                        g2.drawString(gp.player.inventory.get(menuChoice + 2).getName(), x, y);
                    }
                }
            }
        }
    }
    public int getTextHeight(Graphics2D g2, String text){
        return (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
    }
}
