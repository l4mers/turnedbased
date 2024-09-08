package gamestates;

import enums.State;
import gamestates.menus.MenuWindow;
import main.GamePanel;
import save.Option;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class OptionMenu implements UpdateAndDraw{

    private final GamePanel gp;

    private int menuChoice = 1;

    private final Option option;

    private final MenuWindow menuWindow;

    public OptionMenu(GamePanel gp) {
        this.gp = gp;
        menuWindow = new MenuWindow();
        option = gp.settings;
    }

    @Override
    public void update() {
        if(gp.inputHandler.northPressed){
            menuChoice--;
            if(menuChoice < 1){
                menuChoice = 6;
            }
            gp.inputHandler.northPressed = false;
        } else if (gp.inputHandler.southPressed){
            menuChoice++;
            if(menuChoice > 6){
                menuChoice = 1;
            }
            gp.inputHandler.southPressed = false;
        } else if (gp.inputHandler.eastPressed || gp.inputHandler.westPressed || gp.inputHandler.interact){
            switch (menuChoice){
                case 1 -> {
                    if (gp.inputHandler.eastPressed){
                        option.musicVolume += 1;
                        if (option.musicVolume > 5){
                            option.musicVolume = 5;
                        }
                    } else if (gp.inputHandler.westPressed) {
                        option.musicVolume-= 1;
                        if (option.musicVolume < 0){
                            option.musicVolume = 0;
                        }
                    }
                }
                case 2 -> {
                    if (gp.inputHandler.eastPressed){
                        option.effectVolume += 1;
                        if (option.effectVolume > 5){
                            option.effectVolume = 5;
                        }
                    } else if(gp.inputHandler.westPressed) {
                        option.effectVolume -= 1;
                        if (option.effectVolume < 0){
                            option.effectVolume = 0;
                        }
                    }
                }
                case 3 ->{
                    option.animation = !option.animation;
                }
                case 4 ->{
                    option.fullScreen = !option.fullScreen;
                }
                case 5 ->{
                    writeSave();
                    if(gp.gameState == State.ADVENTURE_STATE){
                        gp.controller.setCurrentUpdateAndDraw(new Adventure(gp));
                    } else {
                        gp.controller.setCurrentUpdateAndDraw(new HomeMenu(gp));
                    }
                }
                case 6 ->{
                    if(gp.gameState == State.ADVENTURE_STATE){
                        gp.controller.setCurrentUpdateAndDraw(new Adventure(gp));
                    } else {
                        gp.controller.setCurrentUpdateAndDraw(new HomeMenu(gp));
                    }
                }
            }
            gp.inputHandler.eastPressed = false;
            gp.inputHandler.westPressed = false;
            gp.inputHandler.interact = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        menuWindow.drawWindow(g2,Color.darkGray ,0,0,gp.screenWidth, gp.screenHeight, "Options", gp.tileSize * 2);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        //MUSIC VOLUME
        int x = gp.screenWidth / 2 - getTextWidth(g2, "Music Volume :");
        int y = gp.tileSize * 3 + gp.tileSize / 2;

        if(menuChoice == 1){
            g2.drawString("->", x - gp.tileSize, y);
        }
        g2.drawString("Music Volume :", x, y);

        x += getTextWidth(g2, "Music Volume :") + gp.tileSize / 2 ;
        drawArray(g2, x, y, option.musicVolume);

        //EFFECT VOLUME
        x = gp.screenWidth / 2 - getTextWidth(g2, "Effect Volume :");
        y += gp.tileSize;

        if(menuChoice == 2){
            g2.drawString("->", x - gp.tileSize, y);
        }
        g2.drawString("Effect Volume :", x, y);

        x += getTextWidth(g2, "Effect Volume :") + gp.tileSize / 2 ;
        drawArray(g2, x, y, option.effectVolume);

        x = gp.screenWidth / 2 - getTextWidth(g2, "Animation :");
        y += gp.tileSize;

        if(menuChoice == 3){
            g2.drawString("->", x - gp.tileSize, y);
        }
        g2.drawString("Animation :", x, y);
        x += getTextWidth(g2, "Animation :") + gp.tileSize / 2;
        g2.drawRect(x, y- 36, 45,45);
        if(option.animation){
            g2.fillRect(x + 4, y - 32, 37, 37);
        }

        x = gp.screenWidth / 2 - getTextWidth(g2, "Full Screen :");
        y += gp.tileSize;
        if(menuChoice == 4){
            g2.drawString("->", x - gp.tileSize, y);
        }
        g2.drawString("Full Screen :", x, y);
        x += getTextWidth(g2, "Full Screen :") + gp.tileSize / 2;
        g2.drawRect(x, y- 36, 45,45);
        if(option.fullScreen){
            g2.fillRect(x + 4, y - 32, 37, 37);
        }

        x = getCenterOfText(g2, "Apply", gp.screenWidth / 2);
        y += gp.tileSize * 2;
        if(menuChoice == 5){
            g2.drawString("->", x - gp.tileSize, y);
        }
        g2.drawString("Apply", x, y);

        x = getCenterOfText(g2, "Back", gp.screenWidth / 2);
        y += gp.tileSize;
        if(menuChoice == 6){
            g2.drawString("->", x - gp.tileSize, y);
        }
        g2.drawString("Back", x, y);
    }

    private void drawArray(Graphics2D g2, int x, int y, int volume) {
        if (volume == 1){
            x = rect(g2, 4, filledRect(g2, 1, x, y), y);
        }
        else if (volume == 2){
            x = rect(g2, 3, filledRect(g2, 2, x, y), y);
        }
        else if (volume == 3){
            x = rect(g2, 2, filledRect(g2, 3, x, y), y);
        }
        else if (volume == 4){
            x = rect(g2, 1, filledRect(g2, 4, x, y), y);
        }
        else if (volume == 5){
            filledRect(g2, 5, x, y);
        }else {
            rect(g2, 5, x, y);
        }
    }

    private int filledRect(Graphics2D g2 ,int laps, int x, int y){
        for (int i = 0; i < laps; i++) {
            g2.fillRect(x, y - 36,25, 45);
            x += gp.tileSize / 2;
        }
        return x;
    }
    private int rect(Graphics2D g2 ,int laps, int x, int y){
        for (int i = 0; i < laps; i++) {
            g2.drawRect(x, y - 36,25, 45);
            x += gp.tileSize / 2;
        }
        return x;
    }

    private void writeSave(){
        try(ObjectOutputStream stream2 = new ObjectOutputStream(
                new FileOutputStream("settings/settings"))){
            stream2.writeObject(option);
            gp.settings = option;
        } catch (IOException e) {
            gp.settings = new Option();
            throw new RuntimeException(e);
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
