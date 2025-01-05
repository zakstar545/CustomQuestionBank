package view.component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text); // Call the constructor of JButton with the specified text
        initialize();
    }

    private void initialize() {
        Font defaultFont = UIManager.getFont("Button.font");


        setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFont.getSize())); // Set custom font for the button text
        setBackground(new Color(220, 220, 220)); // Set button background color
        setFocusPainted(false); // Remove the focus border
        setMargin(new Insets(8, 12, 8, 12)); // Add margins (top, left, bottom, right)
        setBorderPainted(true);
    }

}

