import java.awt.image.BufferedImage;

public class Assets {
    private static final int brickWidth = 24, brickHeight = 24;
    public static BufferedImage threeDurBrick, twoDurBrick, oneDurBrick, wallBrick;
    public static BufferedImage playerPad;
    public static BufferedImage ball;
    public static BufferedImage background;

    public static void init() {
        playerPad = ImageLoader.loadImage("Assets/playerPaddle.jpeg");
        ball = ImageLoader.loadImage("Assets/ball.png");
        background = ImageLoader.loadImage("Assets/background.png");
    }
}
