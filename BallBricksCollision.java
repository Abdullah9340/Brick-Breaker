import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BallBricksCollision {

    public BallBricksCollision() {
    }

    public void checkCollisions(ArrayList<Brick> bricks, Ball ball, Player player) {
        for (int i = bricks.size() - 1; i >= 0; i--) {
            checkCollision(ball, bricks.get(i), player);
            if (bricks.get(i).getDurability() == 0) {
                bricks.remove(i);
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
                ball.setXVel(ball.getxVelocity() * -1);
            } else if (brickRect.contains(ballLeft)) {
                ball.setXVel(ball.getxVelocity() * -1);
            }
            if (brickRect.contains(ballTop)) {
                ball.setYVel();
            } else if (brickRect.contains(ballBottom)) {
                ball.setYVel();
            }
            brick.setDurability(brick.getDurability() - 1);
            brick.setColor();
        }
    }
}