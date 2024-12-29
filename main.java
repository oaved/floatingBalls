import javax.swing.*;

public class Main {

    final static int FPS = 100;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("BALLS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JPanel playground = new Playground();
        frame.add(playground);
        frame.pack();
        frame.setVisible(true);
    }
}