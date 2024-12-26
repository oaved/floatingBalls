import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Ball {
    private final Color COLOR = Color.WHITE;
    private final Color BORDER_COLOR = Color.BLACK;
    private final int BORDER_THICKNESS = 2;
    public final int RADIUS = 8;
    private final Coord NORMAL_GRAVITY = new Coord(0, 0.1);
    private final double GRAVITY_FACTOR = 0.0005;
    private final double VELOCITY_DAMPING = 0.99;
    private final double BOUNCE_DAMPING = 0.5;

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
    }

    void checkWallCollision() {
        double[] wallPositions = playground.getWallPosition();
        double ballRightEdge = this.position.x + RADIUS;
        double ballLeftEdge = this.position.x - RADIUS;
        double ballTopEdge = this.position.y - RADIUS;
        double ballBottomEdge = this.position.y + RADIUS;

        if (velocity.x > 0 && ballRightEdge > wallPositions[0]) {
            velocity.x = -velocity.x * BOUNCE_DAMPING;
        }
        if (velocity.y < 0 && ballTopEdge < wallPositions[1]) {
            velocity.y = -velocity.y * BOUNCE_DAMPING;
        }
        if (velocity.x < 0 && ballLeftEdge < wallPositions[2]) {
            velocity.x = -velocity.x * BOUNCE_DAMPING;
        }
        if (velocity.y > 0 && ballBottomEdge > wallPositions[3]) {
            velocity.y = -velocity.y * BOUNCE_DAMPING;
        }
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
        g.setColor(BORDER_COLOR);
        g.fillOval(
                (int) (position.x - RADIUS + 0.5),
                (int) (position.y - RADIUS + 0.5),
                (int) RADIUS * 2,
                (int) RADIUS * 2);
        g.setColor(COLOR);
        g.fillOval(
                (int) (position.x - RADIUS + 0.5 + BORDER_THICKNESS),
                (int) (position.y - RADIUS + 0.5 + BORDER_THICKNESS),
                (int) (RADIUS * 2 - 2 * BORDER_THICKNESS),
                (int) (RADIUS * 2 - 2 * BORDER_THICKNESS));
    }
}
