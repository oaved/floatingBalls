import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Playground extends JPanel implements ActionListener, KeyListener {
    private int ballAmount = 500;
    Ball[] balls = new Ball[ballAmount];

    private final Color BACKGROUND_COLOR = Color.BLACK;
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private final int SPAWN_WIDTH_PART = (WIDTH / (ballAmount + 1));
    private final int SPAWN_HEIGHT = (HEIGHT / 2);
    private final Dimension PLAYGROUND_SIZE = new Dimension(WIDTH, HEIGHT);
    private final Timer timer;

    Playground() {
        this.setPreferredSize(PLAYGROUND_SIZE);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(BACKGROUND_COLOR);

        timer = new Timer((int) (1000 / Main.FPS), this);
        timer.start();

        // Spawn all balls
        for (int i = 0; i < ballAmount; i++) {
            Coord spawnCoord = new Coord((i + 1) * SPAWN_WIDTH_PART, SPAWN_HEIGHT);
            balls[i] = new Ball(spawnCoord, this);
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
