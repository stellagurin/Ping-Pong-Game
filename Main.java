import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main{

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tennis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        TennisPanel tennisPanel = new TennisPanel();
        frame.add(tennisPanel, BorderLayout.CENTER);

        frame.setSize(800, 500);
        frame.setVisible(true);

    }
}
