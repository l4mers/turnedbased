package item;

public class Item {

    private final String name;
    private final Boolean inFight;

    public Item(String name, Boolean inFight) {
        this.name = name;
        this.inFight = inFight;
    }

    public String getName() {
        return name;
    }
}
