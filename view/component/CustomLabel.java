package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text) {
        super(text); // Call the constructor of JButton with the specified text
        initialize();
    }

    private void initialize() {
        setFont(new Font("Comic Sans MS", Font.BOLD, 20)); // Set font for the label text
    }
}