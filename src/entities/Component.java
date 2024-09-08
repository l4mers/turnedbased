package entities;

import java.awt.image.BufferedImage;

public class Component {

    public BufferedImage image;
    public String imagePath;
    public String name;
    public int level = 1;
    public int experience = 0;
    public int nextLevel = 100;
    public int health;
    public int currentHealth;
    public int defence;
    public int magicDefence;
    public int attack;
    public int expGain;
    public int magicAttack;
    public int width, height;

    public Component(){
    }

    public Component(String imagePath, String name, int health, int defence, int magicDefence, int attack, int magicAttack) {
        this.imagePath = imagePath;
        this.name = name;
        this.health = health;
        this.currentHealth = health;
        this.defence = defence;
        this.magicDefence = magicDefence;
        this.attack = attack;
        this.magicAttack = magicAttack;
    }

    public void gainExperience(int exp){
        if (exp + experience >= nextLevel){
            experience = exp + experience - nextLevel;
            levelUp();
        } else {
            experience += exp;
        }
    }

    private void levelUp() {
        level++;
        health++;
        currentHealth = health;
        defence ++;
        magicDefence++;
        attack++;
        magicAttack++;
        nextLevel += nextLevel / 2;
    }

    public void dealDamage(Component foe) {
        if(attack <= foe.defence){
            foe.currentHealth -= 1;
        } else {
            foe.currentHealth -= attack - foe.defence;
        }
    }

    public void restore() {
        currentHealth = health;
    }
}
