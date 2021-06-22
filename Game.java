import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Game implements Runnable, KeyListener {

    // Variables for framerate
    private double timePerTick, delta;
    long now, lastTime;
    // Variables for graphics objects
    private Graphics g;
    private BufferStrategy bs;
    private Font f = new Font("Papyrus", Font.BOLD, 32);

    // Variables for the game
    private String title;
    private int WIDTH, HEIGHT, FPS = 60;
    private boolean running = false;
    private boolean isDead = true, isMenu = true, isGameOver = false;
    private int lives = 3;

    private Thread thread;
    private Display display;
    private Player player;

    private ArrayList<Brick> bricks;
    private ArrayList<Ball> balls;
    private BallBricksCollision collisionCheck;
    private ArrayList<Powerups> powerups;

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

    /*-
    * void update()
    * Description: This moves each object and checks for collisions
    * Pre : None
    * Post: none
    */
    public void update() {
        // If you are not in the menu state and not in the gameover state update the
        // game
        if (!isMenu && !isGameOver) {
            player.update(); // Update the player
            // Loop through each of the balls
            for (int i = balls.size() - 1; i >= 0; i--) {
                Ball b = balls.get(i);
                b.update(); // Update each ball
                // Check brick collision
                collisionCheck.checkCollisions(bricks, b, player, powerups);
                // If the ball hits the left wall
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
                        b.setYVel(); // Reverse Y velocity
                        // This zone width is used to determine which side the ball hit the paddle
                        int zoneWidth = player.getWidth() / 2;
                        int zoneOne = player.getX() + zoneWidth;
                        int zoneTwo = zoneOne + zoneWidth;
                        Music.genericNoise("Assets/paddle_hit.wav");
                        if (b.getX() < zoneOne) { // On the right side make the ball go to the right side
                            b.setXVel(-3);
                        } else if (b.getX() < zoneTwo) { // Go to the left side
                            b.setXVel(3);
                        } else {
                            b.setXVel(3);
                        }
                    }
                }
                if (b.getY() > 600) { // If the ball falls off the screen
                    balls.remove(i); // Remove it
                    Music.genericNoise("Assets/deathlaugh.wav");
                    if (balls.size() == 0) {
                        isDead = true; // Allow player to spawn another ball if they have lives
                        if (lives == 0) { // If they dont the game is over
                            isGameOver = true;
                        }
                    }
                }
            }
            for (int i = powerups.size() - 1; i >= 0; i--) { // Loop through each of the powerups
                powerups.get(i).update(); // Update each one
                // Check if it collides with the player paddle
                if (powerups.get(i).getX() >= player.getX()
                        && powerups.get(i).getX() <= player.getX() + player.getWidth()) {
                    if (powerups.get(i).getY() + 25 >= player.getY()
                            && powerups.get(i).getY() <= player.getY() + player.getHeight()) {
                        // Find which powerup was hit
                        switch (powerups.get(i).getPowerUpType()) {
                            case 0:
                                addThreeBalls(); // Add three balls
                                break;
                            case 1:
                                lives++; // Add another live
                                break;
                            case 2:
                                player.setWide(true);
                                break;
                            default:
                                break;

                        }
                        powerups.remove(powerups.get(i)); // Remove the powerup
                    }
                }
            }
            if (bricks.size() == 0) { // If no bricks left the game is over
                isGameOver = true;
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
        g.setColor(Color.red);
        // Start Draw
        // If you are in the menu
        if (isMenu) {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawImage(Assets.gameStart, 0, 0, 800, 600, null);
            g.setFont(f);
            g.setColor(Color.black);
            g.drawString("Press Any Key To Start", (WIDTH / 2) - 200, (HEIGHT / 2) + 50);
        } else if (isGameOver) { // If the game is over
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.white);
            g.drawImage(Assets.gameEnd, 0, 0, 800, 600, null);
            g.setFont(f);
            if (bricks.size() == 0) {
                g.drawString("You Won", (WIDTH / 2) - 70, (HEIGHT / 2) + 50);
            } else {
                g.drawString("You Lost", (WIDTH / 2) - 70, (HEIGHT / 2) + 50);
            }
        } else { // Otherwise draw all the game assets
            g.drawImage(Assets.background, 0, 0, WIDTH, HEIGHT, null);
            player.render(g);
            for (Brick b : bricks) {
                b.render(g);
            }
            for (Ball b : balls) {
                b.render(g);
            }
            for (Powerups p : powerups) {
                p.render(g);
            }
            for (int i = 0; i < lives; i++) {
                g.drawImage(Assets.heart, 24 + (i * 36), 530, 42, 42, null);
            }

        }
        // End Draw
        bs.show();
        g.dispose();
    }

    /*-
        void addThreeBalls()
        Description: Add three balls for each ball you currently have
        pre: none
        post: none
    */
    public void addThreeBalls() {
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            balls.add(new Ball(balls.get(i).getX(), balls.get(i).getY()));
            balls.add(new Ball(balls.get(i).getX(), balls.get(i).getY()));
            balls.add(new Ball(balls.get(i).getX(), balls.get(i).getY()));
        }

    }

    /*-
        void init()
        Description: Sets up the variables and assets
        pre: none 
        post: none
    */
    public void init() {
        Music.backgroundMusic();
        display = new Display(title, WIDTH, HEIGHT);
        Assets.init();
        player = new Player();
        bricks = new ArrayList<Brick>();
        balls = new ArrayList<Ball>();
        setUpBricks();
        display.getJFrame().addKeyListener(player);
        display.getJFrame().addKeyListener(this);
        collisionCheck = new BallBricksCollision();
        powerups = new ArrayList<Powerups>();
    }

    /*-
        void run()
        Description: Runs the game
        pre: none
        post: none
    */
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

    /*-
        void setUpBricks()
        description: set up the bricks
        pre: none
        post: none
    */
    private void setUpBricks() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                bricks.add(new Brick((60 * j + 4 * j) + 20, (24 * i + 4 * i) + 10, 60, 24, 3));
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    /*-
        void keyPressed()
        description: check for keys pressed
        pre: none
        post: none
    */
    public void keyPressed(KeyEvent e) {
        if (isMenu) {
            isMenu = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Spawn ball if possible
            if (lives >= 0 && isDead) {
                lives--;
                isDead = false;
                balls.add((new Ball(player.getX() + (player.getWidth() / 2), player.getY())));
                balls.get(balls.size() - 1).setXVel(0);
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
