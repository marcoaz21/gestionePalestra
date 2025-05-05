package model;

import view.design;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            
            JFrame frame = new JFrame("Gestione Palestra");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null);
            
            design mainDesign = new design(frame);
            frame.setContentPane(mainDesign.getPanel());
            
            mainDesign.getPresenter().caricaIscritti();
            
            frame.setVisible(true);
        });
    }
}