package keylisteners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public boolean northPressed, southPressed, westPressed, eastPressed, interact, select;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            northPressed = true;
        } if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            southPressed = true;
        } if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            westPressed = true;
        } if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            eastPressed = true;
        }
        if (key == KeyEvent.VK_X) {
            interact = true;
        }
        if (key == KeyEvent.VK_Z) {
            select = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            northPressed = false;
        } if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            southPressed = false;
        } if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            westPressed = false;
        } if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            eastPressed = false;
        }
    }
}
