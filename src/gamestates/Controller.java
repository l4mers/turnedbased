package gamestates;

import main.GamePanel;
import java.awt.*;

public class Controller {

    private UpdateAndDraw currentState;

    public Controller(GamePanel gp){
        currentState = new HomeMenu(gp);
    }

    public void update(){
        currentState.update();
    }

    public void draw(Graphics2D g2){
        currentState.draw(g2);
    }

    public void setCurrentUpdateAndDraw(UpdateAndDraw currentPainter) {
        this.currentState = currentPainter;
    }

}
