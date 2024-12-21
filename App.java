import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardwidth = 360;
        int boardheight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        //frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);

        frame.setLocationRelativeTo(null); // Center the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack(); // Adjust size to match the preferred size of the content
        flappyBird.requestFocus();
        frame.setVisible(true); // Make the frame visible
    }
}
