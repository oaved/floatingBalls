import java.awt.*;

public class Ball {
    private final Color COLOR = Color.WHITE;
    private final int BORDER_THICKNESS = 2;
    public final int RADIUS = 4;
    private final Coord NORMAL_GRAVITY = new Coord(0, 0.15);
    private final double GRAVITY_FACTOR = 0.0015;
    private final double VELOCITY_DAMPING = 0.99;
    private final double WALL_BOUNCE_DAMPING = 0.5;
    private final double BALL_BOUNCE_DAMPING = 0.9;


    public Coord position;
    public Coord velocity;
    private Coord gravity;
    public Playground playground;

    Ball(Coord initialPosition, Playground currentPlayground) {
        position = initialPosition;
        playground = currentPlayground;
        gravity = new Coord(NORMAL_GRAVITY.x, NORMAL_GRAVITY.y);
        velocity = new Coord(0, 0);
    }

    void move() {
            velocity.increase(gravity);

            velocity.x *= VELOCITY_DAMPING;
            velocity.y *= VELOCITY_DAMPING;
            position.x += velocity.x;
            if (position.y < (playground.HEIGHT - this.RADIUS) || velocity.y < 0) {
                position.y += velocity.y;
            }
            
            checkWallCollision();
            for (int i = 0; i < playground.balls.length; i++) {
                if (playground.balls[i] != null && playground.balls[i] != this) {
                    checkBallCollision(this, playground.balls[i]);
                }
            }
    }

    void checkBallCollision(Ball ball, Ball otherBall) {
        double distance = Coord.distance(otherBall.position, ball.position);
        
        if (distance < 2 * RADIUS && isMovingTowardsEachother(ball, otherBall)) {
            if (otherBall.isMoving()) {
                Coord collisionVector = calcCollisionVector(otherBall, ball);
                ball.velocity = Coord.mul(BALL_BOUNCE_DAMPING, Coord.add(ball.velocity, collisionVector)) ;
                otherBall.velocity = Coord.mul(BALL_BOUNCE_DAMPING, Coord.sub(otherBall.velocity, collisionVector));
            }
            else if (ball.isMoving()) {
                Coord collisionVector = calcCollisionVector(ball, otherBall);
                ball.velocity = Coord.mul(BALL_BOUNCE_DAMPING, Coord.sub(ball.velocity, collisionVector));
                otherBall.velocity = Coord.mul(BALL_BOUNCE_DAMPING, Coord.add(otherBall.velocity, collisionVector));
            }
            
        }
    }

    void checkWallCollision() {
        double[] wallPositions = playground.getWallPosition();
        double ballRightEdge = this.position.x + RADIUS;
        double ballLeftEdge = this.position.x - RADIUS;
        double ballTopEdge = this.position.y - RADIUS;
        double ballBottomEdge = this.position.y + RADIUS;

        if (velocity.x > 0 && ballRightEdge > wallPositions[0]) {
            velocity.x = -velocity.x * WALL_BOUNCE_DAMPING;
        }
        if (velocity.y < 0 && ballTopEdge < wallPositions[1]) {
            velocity.y = -velocity.y * WALL_BOUNCE_DAMPING;
        }
        if (velocity.x < 0 && ballLeftEdge < wallPositions[2]) {
            velocity.x = -velocity.x * WALL_BOUNCE_DAMPING;
        }
        if (velocity.y > 0 && ballBottomEdge > wallPositions[3]) {
            velocity.y = -velocity.y * WALL_BOUNCE_DAMPING;
        }
    }

    Coord calcCollisionVector(Ball senderBall, Ball recieverBall) {
        double xScalar = (recieverBall.position.x - senderBall.position.x) / 
                        Math.sqrt(Math.pow(recieverBall.position.x - senderBall.position.x, 2) + Math.pow(recieverBall.position.y - senderBall.position.y, 2));

        double yScalar = (recieverBall.position.y - senderBall.position.y) / 
                        Math.sqrt(Math.pow(recieverBall.position.x - senderBall.position.x, 2) + Math.pow(recieverBall.position.y - senderBall.position.y, 2));

        Coord xVector = Coord.mul(xScalar, Coord.I_HAT);
        Coord yVector = Coord.mul(yScalar, Coord.J_HAT);
        Coord dHat = Coord.add(xVector, yVector);

        double impulseMagnitude = Coord.scal(senderBall.velocity, dHat) - Coord.scal(recieverBall.velocity, dHat);
        Coord collisionVector = Coord.mul(impulseMagnitude, dHat);

        return (collisionVector);
    }
    
    boolean isMovingTowardsEachother (Ball ball, Ball otherBall) {
        Coord positionDiff = Coord.sub(ball.position, otherBall.position);
        Coord velocityDiff = Coord.sub(ball.velocity, otherBall.velocity);
        double scalarProd = Coord.scal(positionDiff, velocityDiff);

        if (scalarProd < 0) {
            return (true);
        }
        return (false);
    }

    boolean isMoving() {  
        return velocity.magnitude() > GRAVITY_FACTOR;
    }


    void gravitateCenter() {
        double distanceX = (playground.WIDTH / 2) - position.x;
        double distanceY = (playground.HEIGHT / 2) - position.y;

        gravity.x = Math.signum(distanceX) * Math.min(Math.abs(distanceX) * GRAVITY_FACTOR, 0.5);
        gravity.y = Math.signum(distanceY) * Math.min(Math.abs(distanceY) * GRAVITY_FACTOR, 0.5);
    }

    void resetGravity() {
        gravity = new Coord(NORMAL_GRAVITY.x, NORMAL_GRAVITY.y);
    }

    void paint(Graphics g) {
        g.setColor(COLOR);
        g.fillOval(
                (int) (position.x - RADIUS + 0.5 + BORDER_THICKNESS),
                (int) (position.y - RADIUS + 0.5 + BORDER_THICKNESS),
                (int) (RADIUS * 2 - 2 * BORDER_THICKNESS),
                (int) (RADIUS * 2 - 2 * BORDER_THICKNESS));
    }
}
