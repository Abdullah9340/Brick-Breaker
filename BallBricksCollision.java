import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BallBricksCollision {

    public void checkCollisions(ArrayList<Brick> bricks, Ball ball, Player player, ArrayList<Powerups> powerups) {
        for (int i = bricks.size() - 1; i >= 0; i--) {
            checkCollision(ball, bricks.get(i), player);
            if (bricks.get(i).getDurability() == 0) {
                bricks.remove(i);
                // Chance for powerup to spawn
                int chance = (int) (Math.random() * 100) + 1;
                // 30 % Chance for a power up to spawn
                if (chance <= 30) {
                    if (chance <= 15) {
                        powerups.add(new Powerups(ball.getX(), ball.getY(), 0));
                    } else {
                        powerups.add(new Powerups(ball.getX(), ball.getY(), 1));
                    }
                }
            }
        }
    }

    private void checkCollision(Ball ball, Brick brick, Player player) {

        Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        Rectangle brickRect = new Rectangle(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());

        Point ballRight = new Point(ballRect.x + ballRect.width + 2, ballRect.y + 1);
        Point ballLeft = new Point(ballRect.x - 3, ballRect.y + 1);
        Point ballTop = new Point(ballRect.x + 2, ballRect.y - 2);
        Point ballBottom = new Point(ballRect.x + 2, ballRect.y + ballRect.height + 2);

        /*
         * Check if the collision rectangles intersects each other, if they do check
         * which side of the ball is colliding
         */
        if (ballRect.intersects(brickRect)) {

            if (brickRect.contains(ballRight)) {
                ball.setXVel();
            } else if (brickRect.contains(ballLeft)) {
                ball.setXVel();
            }
            if (brickRect.contains(ballTop)) {
                ball.setYVel();
            } else if (brickRect.contains(ballBottom)) {
                ball.setYVel();
            }
            if (!brickRect.contains(ballTop) && !brickRect.contains(ballBottom) && !brickRect.contains(ballRight)
                    && !brickRect.contains(ballLeft)) {
                ball.setYVel();
                ball.setXVel();
            }
            brick.setDurability(brick.getDurability() - 1);
            brick.setColor();
        }
    }
}