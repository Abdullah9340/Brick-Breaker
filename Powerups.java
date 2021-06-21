import java.awt.Graphics;
import java.awt.Color;

public class Powerups {
    public static String[] powerUpTypes = { "Plus 3 Balls", "Extra Life" };
    private int x, y, speed;
    private int powerUpType;

    public Powerups(int x, int y, int powerUpType) {
        this.x = x;
        this.y = y;
        speed = 3;
        this.powerUpType = powerUpType;
    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval(x, y, 25, 25);
    }

    public void update() {
        y += speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPowerUpType() {
        return powerUpType;
    }
}
