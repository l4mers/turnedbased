package gamestates;

import enums.State;
import gamestates.menus.MenuWindow;
import main.GamePanel;
import save.Data;
import save.LoadAndSave;


import java.awt.*;
import java.util.List;

public class LoadSave implements UpdateAndDraw{

    private final GamePanel gp;
    private int menuChoice = 1;
    private final MenuWindow menuWindow;
    private final List<Data> saves;

    boolean slot1 = false;
    boolean slot2 = false;
    boolean slot3 = false;

    public LoadSave(GamePanel gp) {
        this.gp = gp;
        menuWindow = new MenuWindow();
        saves = new LoadAndSave().loadAll();
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
                    if(slot1){
                        gp.slot = menuChoice;
                        gp.loadGame(saves.get(0));
                    }else {
                        gp.slot = menuChoice;
                        gp.newGame();
                    }
                }
                case 2 ->{
                    if(slot2){
                        gp.slot = menuChoice;
                        gp.loadGame(saves.get(0));
                    }else {
                        gp.slot = menuChoice;
                        gp.newGame();
                    }
                }
                case 3 ->{
                    if(slot3){
                        gp.slot = menuChoice;
                        gp.loadGame(saves.get(0));
                    }else {
                        gp.slot = menuChoice;
                        gp.newGame();
                    }
                }
                default -> {
                    if(gp.gameState == State.ADVENTURE_STATE){
                        gp.controller.setCurrentUpdateAndDraw(new Adventure(gp));
                    } else {
                        gp.controller.setCurrentUpdateAndDraw(new HomeMenu(gp));
                    }
                }
            }
            gp.inputHandler.interact = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        menuWindow.drawWindow(g2,Color.darkGray ,0,0,gp.screenWidth, gp.screenHeight, "Load", gp.tileSize * 2);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        int x = gp.screenWidth / 2 - gp.tileSize*3;
        int y = gp.tileSize * 3;
        g2.drawRect(x, y, gp.tileSize*6, gp.tileSize*2);
        if(menuChoice == 1){
            g2.drawRect(x - 5, y - 5, gp.tileSize*6 + 10, gp.tileSize*2 + 10);
        }
        String text = "Slot 1";
        g2.drawString(text, x + 5, y + getTextHeight(g2, text));
        if(saves.get(0).getLastPlayed() != null){
            slot1 = true;

            g2.drawString(saves.get(0).getLastPlayedAsString(), x + 5, y + getTextHeight(g2, saves.get(0).getLastPlayedAsString()) + getTextHeight(g2, saves.get(0).getLastPlayedAsString()) + 5);
        }else{
            text = "New Game";
            g2.drawString(text, x + 5, y + getTextHeight(g2, text) + getTextHeight(g2, text) + 5);
        }

        y += gp.tileSize * 2.5;
        g2.drawRect(x, y, gp.tileSize*6, gp.tileSize*2);
        if(menuChoice == 2){
            g2.drawRect(x - 5, y - 5, gp.tileSize*6 + 10, gp.tileSize*2 + 10);
        }
        text = "Slot 2";
        g2.drawString(text, x + 5, y + getTextHeight(g2, text));
        if(saves.get(1).getLastPlayed() != null){
            slot2 = true;
            g2.drawString(saves.get(1).getLastPlayedAsString(), x + 5, y + getTextHeight(g2, saves.get(1).getLastPlayedAsString()) + getTextHeight(g2, saves.get(1).getLastPlayedAsString()) + 5);
        }else{
            text = "New Game";
            g2.drawString(text, x + 5, y + getTextHeight(g2, text) + getTextHeight(g2, text) + 5);
        }

        y += gp.tileSize* 2.5;
        g2.drawRect(x, y, gp.tileSize*6, gp.tileSize*2);
        if(menuChoice == 3){
            g2.drawRect(x - 5, y - 5, gp.tileSize*6 + 10, gp.tileSize*2 + 10);
        }
        text = "Slot 3";
        g2.drawString(text, x + 5, y + getTextHeight(g2, text));
        if(saves.get(2).getLastPlayed() != null){
            slot3 = true;
            g2.drawString(saves.get(2).getLastPlayedAsString(), x + 5, y + getTextHeight(g2, saves.get(2).getLastPlayedAsString()) + getTextHeight(g2, saves.get(2).getLastPlayedAsString()) + 5);
        }else{
            text = "New Game";
            g2.drawString(text, x + 5, y + getTextHeight(g2, text) + getTextHeight(g2, text) + 5);
        }

        y += gp.tileSize* 3 + 10;
        text = "Back";
        x = getCenterForX(g2, text);
        g2.drawString(text, x, y);
        if(menuChoice == 4){
            g2.drawRect(x - 3, y - getTextHeight(g2, text) + 5, getTextWidth(g2, text) + 10, getTextHeight(g2, text));
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
    public int getTextWidth(Graphics2D g2, String text){
        return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }
    public int getTextHeight(Graphics2D g2, String text){
        return (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
    }
    public int getCenterOfText(Graphics2D g2, String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length/2;
    }
}
