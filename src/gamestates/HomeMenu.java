package gamestates;

import gamestates.menus.MenuWindow;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HomeMenu implements UpdateAndDraw {

    private final GamePanel gp;
    private int menuChoice = 1;
    private final MenuWindow menuWindow;

    public HomeMenu(GamePanel gp){
        this.gp = gp;
        menuWindow = new MenuWindow();
    }

    @Override
    public void update() {

        if(gp.inputHandler.northPressed){
            menuChoice--;
            if(menuChoice < 1){
                menuChoice = 4;
            }
            gp.inputHandler.northPressed = false;
        } else if (gp.inputHandler.southPressed){
            menuChoice++;
            if(menuChoice > 4){
                menuChoice = 1;
            }
            gp.inputHandler.southPressed = false;
        } else if (gp.inputHandler.interact){
            switch (menuChoice){
                case 1 -> {
                    gp.controller.setCurrentUpdateAndDraw(new LoadSave(gp));
                }
                case 2 -> {
                    gp.slot = 0;
                    gp.newGame();
                }
                case 3 -> {
                    gp.controller.setCurrentUpdateAndDraw(new OptionMenu(gp));
                }
                case 4 -> {
                    System.exit(0);
                }
            }
            gp.inputHandler.interact = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        menuWindow.drawWindow(g2,Color.darkGray ,0,0,gp.screenWidth, gp.screenHeight,"WujuMajamangu", gp.tileSize * 3);

        int x = gp.screenWidth / 2 - (gp.tileSize*2) / 2;
        int y = gp.tileSize * 5;
        g2.drawImage(UtilityTool.getSubImage(gp.player.spriteSheet, 4, 3, gp.playerSize, gp.playerSize),
                x, y, gp.tileSize*2, gp.tileSize*2, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        String text = "Load Game";
        x  = getCenterForX(g2, text);
        y += gp.tileSize*3;
        g2.drawString(text, x, y);
        if (menuChoice == 1){
            g2.drawString("->", x - gp.tileSize, y);
        }

        text = "New Game";
        x  = getCenterForX(g2, text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (menuChoice == 2){
            g2.drawString("->", x - gp.tileSize, y);
        }

        text = "Option";
        x  = getCenterForX(g2, text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (menuChoice == 3){
            g2.drawString("->", x - gp.tileSize, y);
        }

        text = "Quit";
        x  = getCenterForX(g2, text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (menuChoice == 4){
            g2.drawString("->", x - gp.tileSize, y);
        }

    }
    public int getCenterForX(Graphics2D g2, String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
    public int getXForAlignText(Graphics2D g2, String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
    public int getCenterOfText(Graphics2D g2, String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length/2;
    }
}
