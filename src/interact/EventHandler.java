package interact;

import main.GamePanel;
import gamestates.Fight;

public class EventHandler {

    public void interactWithNPC(int index, GamePanel gp){
        if(index != 999){
            gp.inputHandler.interact = false;
            gp.controller.setCurrentUpdateAndDraw(new Fight(gp, gp.entityList.get(index)));
            //gp.fightController.setup(gp.entityList.get(index));
        }
    }
}
