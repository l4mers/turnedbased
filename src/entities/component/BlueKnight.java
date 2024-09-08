package entities.component;

import entities.Component;

public class BlueKnight extends Component {

    public BlueKnight(){
        imagePath = "/characters/npc/blue_knight.png";
        name = "Blue Knight";
        health = 8;
        defence = 3;
        magicDefence = 1;
        attack = 3;
        magicAttack = 1;
        expGain = 50;
        currentHealth = health;
    }
}
