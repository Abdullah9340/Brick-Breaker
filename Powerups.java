import java.awt.Graphics;

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
        if (powerUpType == 1) {
            g.drawImage(Assets.heart, x, y, 36, 36, null);
        } else if (powerUpType == 0) {
            g.drawImage(Assets.timesThreeBall, x, y, 36, 36, null);
        }
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
