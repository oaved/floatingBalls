import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Playground extends JPanel implements ActionListener, KeyListener {
    private int ballAmount = 2;
    Ball[] balls = new Ball[ballAmount];

    private final Color BACKGROUND_COLOR = Color.BLACK;
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private final Coord SPAWN_1 = new Coord(1 * (WIDTH / 3), HEIGHT / 2);
    private final Coord SPAWN_2 = new Coord(2 * (WIDTH / 3), HEIGHT / 2);
    private final Dimension PLAYGROUND_SIZE = new Dimension(WIDTH, HEIGHT);
    private final Timer timer;

    Playground() {
        this.setPreferredSize(PLAYGROUND_SIZE);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(BACKGROUND_COLOR);

        timer = new Timer((int) (1000 / main.FPS), this);
        timer.start();

        // Spawn all balls
        for (int i = 0; i < ballAmount; i++) {
            if (i % 2 == 0) {
                balls[i] = new Ball(SPAWN_1, this);
            } else {
                balls[i] = new Ball(SPAWN_2, this);
            }
            balls[i].move();
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == timer) {
            for (Ball ball : balls) {
                ball.move();
            }
            repaint();
        }
    }

    public double[] getWallPosition() {
        double rightWallPos = WIDTH;
        double topWallPos = 0;
        double leftWallPos = 0;
        double bottomWallPos = HEIGHT;
        double[] wallPositions = { rightWallPos, topWallPos, leftWallPos, bottomWallPos };

        return (wallPositions);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball ball : balls) {
            ball.paint(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Ball ball : balls) {
            ball.gravitateCenter();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Ball ball : balls) {
            ball.resetGravity();
        }
    }
}
