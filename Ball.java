import java.awt.Graphics;

public class Ball {
    private int xVelocity, yVelocity, width, height;
    private int x, y;

    public Ball(int x, int y) {
        xVelocity = (int) (Math.random() * 2) + 1;
        if ((int) (Math.random() * 100) <= 50) {
            xVelocity *= -1;
        }
        yVelocity = -5;
        width = 15;
        height = 15;
        this.x = x;
        this.y = y;
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

    public void setXVel() {
        if (xVelocity > 0) {
            xVelocity = 3;
        } else {
            xVelocity = -3;
        }
        xVelocity *= -1;
        xVelocity += (int) ((Math.random() * 2) - 1);
    }

    public void setXVel(int xVel) {
        if (xVel == 0) {
            xVelocity = 0;
        } else {
            xVelocity = xVel;
            xVelocity += (int) (Math.random() * -1) + 1;
        }
    }

    public void setYVel() {
        yVelocity *= -1;
    }

    public void setYVel(int y) {
        this.yVelocity = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
