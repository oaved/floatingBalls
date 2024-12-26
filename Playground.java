import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Playground extends JPanel implements ActionListener, KeyListener{
    private int ballAmount = 1;
    private Ball[] balls = new Ball[ballAmount];
    final int WIDTH = 600;
    final int HEIGHT = 600;
    private final Dimension PLAYGROUND_SIZE = new Dimension(WIDTH, HEIGHT);
    private final Timer timer;

    Playground() {
        this.setPreferredSize(PLAYGROUND_SIZE);
        balls[0] = new Ball(new Coord(WIDTH / 2, HEIGHT / 2), this);
        balls[0].move();

        this.addKeyListener(this);
        this.setFocusable(true);

        timer = new Timer((int ) (1000 / main.FPS), this);
        timer.start();
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
        double[] wallPositions = {rightWallPos, topWallPos, leftWallPos, bottomWallPos};

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
