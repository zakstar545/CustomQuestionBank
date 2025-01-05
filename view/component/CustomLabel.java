package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text) {
        super(text); // Call the constructor of JLabel with the specified text
        initialize();
    }

    private void initialize() {
        // Get the system's default font for labels
        Font defaultFont = UIManager.getFont("Label.font");
        // Get the default font size
        int defaultFontSize = defaultFont.getSize();
        // Set the custom font family and style, but keep the default font size
        setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize)); // Set custom font for the label text
    }
}