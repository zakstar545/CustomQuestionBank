package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text); // Call the constructor of JButton with the specified text
        initialize();
    }

    private void initialize() {
        setFont(new Font("Comic Sans MS", Font.BOLD, 20)); // Set font for the button text
        setBackground(new Color(220, 220, 220)); // Set button background color
        setFocusPainted(false); // Remove the focus border
        setMargin(new Insets(8, 12, 8, 12)); // Add margins (top, left, bottom, right)
    }

}

