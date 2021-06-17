import java.awt.Graphics;

public class Ball {
    private int xVelocity, yVelocity, width, height;
    private int x, y;

    public Ball(Player player) {
        xVelocity = -3;
        yVelocity = -5;
        width = 15;
        height = 15;
        x = player.getX() + 50;
        y = player.getY();
    }

    public void render(Graphics g) {
        g.drawImage(Assets.ball, x, y, width, height, null);
    }

    public void update() {
        x += xVelocity;
        y += yVelocity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setXVel(int xVel) {
        xVelocity = xVel;
    }

    public void setYVel() {
        yVelocity *= -1;
    }
}
