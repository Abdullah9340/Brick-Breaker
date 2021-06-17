import java.awt.Graphics;
import java.awt.event.*;

public class Player implements KeyListener {
    private int x, y, width, height, speed;
    private boolean moving;

    public Player() {
        x = 400;
        y = 520;
        width = 180;
        height = 50;
        speed = 8;
        moving = false;
    }

    public void render(Graphics g) {
        g.drawImage(Assets.playerPad, x, y, width, height, null);
    }

    public void update() {
        if (moving) {
            this.x += speed;
        }
        if (x < -width) {
            x = 800;
        }
        if (x > 800) {
            x = 0;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
            moving = true;
            speed = -5;
        }
        if (e.getKeyChar() == 'D' || e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moving = true;
            speed = 5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
            moving = false;
        }
        if (e.getKeyChar() == 'D' || e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moving = false;
        }
    }

}
