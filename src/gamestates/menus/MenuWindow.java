package gamestates.menus;

import main.UtilityTool;

import java.awt.*;

public class MenuWindow {
    private final Font maruMonica;

    public MenuWindow(){
        maruMonica = UtilityTool.loadFont("/font/x12y16pxMaruMonica.ttf");
    }

    public void drawWindow(Graphics2D g2, Color color, int x, int y, int width, int height, String title, int titleY){
        g2.setFont(maruMonica);
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
        x = getCenterForX(g2, title, width);

        // shadow
        g2.setColor(Color.black);
        g2.drawString(title, x+5, titleY+5);

        //main text
        g2.setColor(Color.white);
        g2.drawString(title, x, titleY);
    }
    public int getCenterForX(Graphics2D g2, String text, int width){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return width/2 - length/2;
    }
}
