package main;

import entities.Entity;
import entities.npc.Knight;
import entities.player.Player;
import enums.State;
import keylisteners.InputHandler;
import gamestates.Adventure;
import gamestates.Controller;
import save.Data;
import save.LoadAndSave;
import save.Option;
import world.WorldController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    //Scaling and size
    private final int size = 32;
    private final int scale = 2;
    private final int playerScale = 3;
    public final int playerSize = size * playerScale;
    public final int tileSize = size * scale;
    private final int tileWidth = 16;
    private final int tileHeight = 12;
    public final int screenWidth = tileSize * tileWidth;
    public final int screenHeight = tileSize * tileHeight;
    public int worldWidth;
    public int worldHeight;

    public State gameState;
    public int slot;

    //FPS
    double FPS = 60;
    public boolean fightAnimation = true;

    //Objects
    Thread gameThread;
    public InputHandler inputHandler = new InputHandler();
    public Player player = new Player(this);
    public ArrayList<Entity> entityList = new ArrayList<>();
    public Controller controller = new Controller(this);
    public WorldController worldController = new WorldController(this);
    public Option settings;
    //public ArrayList<Item> inventory = new ArrayList<>();


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(inputHandler);
        setupGame();
    }

    public void setupGame(){
        gameState = State.HOME_MENU_STATE;
        settings = new LoadAndSave().loadOptions();
        entityList.add(player);
    }
    public void loadGame(Data data){
        slot = data.getSlot();
        player = data.getPlayer();
        entityList = data.getEntities();
        worldController.changeWorld(data.getMapPath());
    }
    public void saveGame(){
        LoadAndSave loadAndSave = new LoadAndSave();
        Data data = new Data();
        data.setLastPlayed(LocalDateTime.now());
        data.setSlot(slot);
        data.setPlayer(player);
        data.setEntities(entityList);
        data.setMapPath(worldController.mapPath);
        loadAndSave.setData(data);
        loadAndSave.save();
    }
    public void newGame(){
        worldController.setup();
        entityList.add(new Knight(this, 300, 300));
        controller.setCurrentUpdateAndDraw(new Adventure(this));
        gameState = State.ADVENTURE_STATE;
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double updateInterval = 1000000000 / FPS; //0.016666 sekund
        double delta = 0;
        long lastUpdate = System.nanoTime(); //nanosekunder för precision
        long currentTime;

        while (gameThread.isAlive()){

            currentTime = System.nanoTime();

            delta += (currentTime - lastUpdate) / updateInterval;
            lastUpdate = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        switch (gameState){
            case HOME_MENU_STATE, ADVENTURE_STATE, FIGHT_STATE ->{
                controller.update();
            }
            case SCEN_STATE ->{}
            case GAME_MENU_STATE ->{}
            case PAUSE_STATE ->{}
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        switch (gameState){
            case HOME_MENU_STATE ->{
                controller.draw(g2);
            }
            case ADVENTURE_STATE, FIGHT_STATE ->{
                worldController.draw(g2);
                controller.draw(g2);
            }
            case SCEN_STATE ->{}
            case GAME_MENU_STATE ->{}
            case PAUSE_STATE ->{}
        }
        g2.dispose();
    }
}
