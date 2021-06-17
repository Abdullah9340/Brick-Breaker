import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BallBricksCollision {

    public BallBricksCollision() {
    }

    /**
     * Loops through the array of bricks and checks if any of the bricks collides
     * with the ball.
     *
     * @param bricks Array of the bricks
     * @param ball   The ball which collision is being inspected
     * @param player Player who is playing the game
     */
    public void checkCollisions(ArrayList<Brick> bricks, Ball ball, Player player) {
        for (int i = bricks.size() - 1; i >= 0; i--) {
            checkCollision(ball, bricks.get(i), player);
            if (bricks.get(i).getDurability() == 0) {
                bricks.remove(i);
            }
        }
    }

    /**
     * Method for checking collision between a Brick and the ball.
     * <p>
     * If ball is colliding with a brick, check which side of the ball collides and
     * reverse the correct movement adjustment.
     *
     * @param ball   Ball which is being inspected
     * @param brick  Brick which is being inspected
     * @param player Player who is playing
     */
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