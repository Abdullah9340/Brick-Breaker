import java.awt.Graphics;
import java.awt.Color;

public class Brick {
    private int x, y, width, height, durability;
    private Color c;

    public Brick(int x, int y, int width, int height, int durability) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.durability = durability;
        setColor();
    }

    public void setColor() {
        switch (durability) {
            case 3:
                c = Color.RED;
                break;
            case 2:
                c = Color.GREEN;
                break;
            case 1:
                c = Color.GRAY;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

}