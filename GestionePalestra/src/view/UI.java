package view;

import javax.swing.*;
import java.awt.*;

public class UI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Gestione Palestra");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null); // Center on screen
            

            design mainDesign = new design(frame);
            frame.setContentPane(mainDesign.getPanel());
            

            mainDesign.getPresenter().caricaIscritti();
            

            frame.setVisible(true);
        });
    }
}
