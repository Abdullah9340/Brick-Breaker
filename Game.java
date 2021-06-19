import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

public class Game implements Runnable, KeyListener {

    // Variables for framerate
    private double timePerTick, delta;
    long now, lastTime;
    // Variables for graphics objects
    private Graphics g;
    private BufferStrategy bs;

    private String title;
    private int WIDTH, HEIGHT, FPS = 60;
    private boolean running = false;
    private boolean isDead = true, isMenu = true;
    private int lives = 3;

    private Thread thread;
    private Display display;
    private Player player;

    private ArrayList<Brick> bricks;
    private ArrayList<Ball> balls;
    private BallBricksCollision collisionCheck;

    /*-
    * Game() 
    * Description: This calls the title, sets the width and height Also
    * starts the game 
    * Pre: the width, height dimensions, title 
    * Post: Starts game
    */
    public Game(String title, int width, int height) {
        this.title = title;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.start(); // Start the thread/game
    }

    public void update() {
        if (!isMenu) {
            player.update();
            for (int i = balls.size() - 1; i >= 0; i--) {
                Ball b = balls.get(i);
                b.update();
                collisionCheck.checkCollisions(bricks, b, player);
                if (b.getX() < 0) {
                    b.setXVel();
                }
                // Hits Right Wall
                if (b.getX() > 800 - b.getWidth()) {
                    b.setXVel();
                    b.setX(800 - b.getWidth() - 5);
                }
                // Hits Top
                if (b.getY() < 0) {
                    b.setYVel();
                }
                // Hits Player paddle
                if (b.getX() >= player.getX() && b.getX() <= player.getX() + player.getWidth()) {
                    if (b.getY() + b.getHeight() == player.getY() + 20) {
                        b.setYVel();
                        int zoneWidth = player.getWidth() / 2;
                        int zoneOne = player.getX() + zoneWidth;
                        int zoneTwo = zoneOne + zoneWidth;

                        if (b.getX() < zoneOne) {
                            b.setXVel(-3);
                        } else if (b.getX() < zoneTwo) {
                            b.setXVel(3);
                        } else {
                            b.setXVel(3);
                        }
                    }
                }
                if (b.getY() > 600) {
                    balls.remove(i);
                    isDead = true;
                }
            }
        }

    }

    public void render() {
        // Set up the graphic objects
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) { // Create a buffer strategy
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.drawImage(Assets.background, 0, 0, WIDTH, HEIGHT, null);
        // Start Draw
        if (isMenu) {

        } else {
            player.render(g);
            for (Brick b : bricks) {
                b.render(g);
            }
            for (Ball b : balls) {
                b.render(g);
            }
        }
        // End Draw
        bs.show();
        g.dispose();
    }

    public void init() {
        display = new Display(title, WIDTH, HEIGHT);
        Assets.init();
        player = new Player();
        bricks = new ArrayList<Brick>();
        balls = new ArrayList<Ball>();
        setUpBricks();
        display.getJFrame().addKeyListener(player);
        display.getJFrame().addKeyListener(this);
        collisionCheck = new BallBricksCollision();
    }

    public void run() {
        init();
        timePerTick = 1000000000 / FPS;
        delta = 0;
        lastTime = System.nanoTime();
        // Create constant framerate
        // Main game loop
        while (running) {

            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            // Main game loop
            if (delta >= 1) {
                update();
                render();
                delta--;
            }

        }

    }

    /*-
    * start()
    * Description: Starts the whole game
    * initializes the thread
    * Pre: None
    * Post: Starts the game thread 
    */
    public void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start(); // Start the thread
    }

    /*-
    * stop()
    * Pre: None
    * Post: Starts the game thread 
    */
    public void stop() { // Stop the thread
        if (!running) {
            return;
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUpBricks() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 14; j++) {
                bricks.add(new Brick(50 * j + 4 * j, 50 * i + 4 * i, 50, 50, 3));
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (isMenu) {
            isMenu = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (lives >= 0 && isDead) {
                lives--;
                isDead = false;
                balls.add(new Ball(player));
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
