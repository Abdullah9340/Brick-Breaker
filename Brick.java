import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Brick {
    private int x, y, width, height, durability;
    private BufferedImage img;

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
                img = Assets.threeDurBrick;
                break;
            case 2:
                img = Assets.twoDurBrick;
                break;
            case 1:
                img = Assets.oneDurBrick;
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
        g.drawImage(img, x, y, width, height, null);
    }

}