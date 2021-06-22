import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage threeDurBrick, twoDurBrick, oneDurBrick;
    public static BufferedImage playerPad;
    public static BufferedImage ball, heart, timesThreeBall;
    public static BufferedImage background, gameEnd, gameStart;

    public static void init() {
        playerPad = ImageLoader.loadImage("Assets/playerPaddle.jpeg");
        ball = ImageLoader.loadImage("Assets/ball.png");
        background = ImageLoader.loadImage("Assets/background.png");
        threeDurBrick = ImageLoader.loadImage("Assets/redbrick.png");
        twoDurBrick = ImageLoader.loadImage("Assets/greenbrick.png");
        oneDurBrick = ImageLoader.loadImage("Assets/graybrick.png");
        heart = ImageLoader.loadImage("Assets/heart.png");
        gameEnd = ImageLoader.loadImage("Assets/end.png");
        gameStart = ImageLoader.loadImage("Assets/start.png");
        timesThreeBall = ImageLoader.loadImage("Assets/threeBallPowerUp.png");
    }
}
