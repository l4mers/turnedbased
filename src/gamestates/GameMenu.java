package gamestates;

import gamestates.menus.ItemMenu;
import gamestates.menus.MenuWindow;
import gamestates.menus.TeamMenu;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;

public class GameMenu implements UpdateAndDraw{

    private final GamePanel gp;

    private int menuChoice = 1;
    private final Font maruMonica;
    private final MenuWindow menuWindow;

    public GameMenu(GamePanel gp) {
        this.gp = gp;
        menuWindow = new MenuWindow();
        maruMonica = UtilityTool.loadFont("/font/x12y16pxMaruMonica.ttf");
        gp.inputHandler.select = false;
    }

    @Override
    public void update() {
        if(gp.inputHandler.northPressed){
            menuChoice--;
            if(menuChoice < 1){
                menuChoice = 5;
            }
            gp.inputHandler.northPressed = false;
        } else if (gp.inputHandler.southPressed){
            menuChoice++;
            if(menuChoice > 5){
                menuChoice = 1;
            }
            gp.inputHandler.southPressed = false;
        } else if (gp.inputHandler.interact){
            switch (menuChoice){
                case 1 -> {
                    gp.controller.setCurrentUpdateAndDraw(new ItemMenu(gp));
                }
                case 2 -> {
                    gp.controller.setCurrentUpdateAndDraw(new TeamMenu(gp));
                }
                case 3 -> {
                    if(gp.slot != 0){
                        gp.saveGame();
                    } else {
                       // gp.controller.setCurrentUpdateAndDraw(new SaveMenu());
                    }
                }
                case 4 -> {

                }
                case 5 -> {

                }
            }
            gp.inputHandler.interact = false;
        } else if (gp.inputHandler.select){
            gp.inputHandler.select = false;
            gp.controller.setCurrentUpdateAndDraw(new Adventure(gp));
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        menuWindow.drawWindow(g2,Color.lightGray ,gp.screenWidth - gp.screenWidth / 3,0,gp.screenWidth / 3, gp.screenHeight,"", gp.tileSize);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        int x = gp.screenWidth - (gp.screenWidth / 3) + gp.tileSize / 2;
        int y = gp.tileSize * 2;

        int width = gp.tileSize * 4 + gp.tileSize / 2;

        g2.drawString("Items", x, y);
        if(menuChoice == 1){
            g2.drawRect(x - 5, y - getTextHeight(g2, "Items") + 5,width , getTextHeight(g2, "Items"));
        }

        y += gp.tileSize;
        g2.drawString("Team", x, y);
        if(menuChoice == 2){
            g2.drawRect(x - 5, y - getTextHeight(g2, "Team") + 5,width , getTextHeight(g2, "Team") + 5);
        }

        y += gp.tileSize;
        g2.drawString("Save", x, y);
        if(menuChoice == 3){
            g2.drawRect(x - 5, y - getTextHeight(g2, "Team") + 5,width , getTextHeight(g2, "Save") + 5);
        }

        y += gp.tileSize;
        g2.drawString("Main Menu", x, y);
        if(menuChoice == 4){
            g2.drawRect(x - 5, y - getTextHeight(g2, "Main Menu") + 5,width , getTextHeight(g2, "Main Menu") + 5);
        }

        y += gp.tileSize;
        g2.drawString("Exit", x, y);
        if(menuChoice == 5){
            g2.drawRect(x - 5, y - getTextHeight(g2, "Exit") + 5,width , getTextHeight(g2, "Exit") + 5);
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
