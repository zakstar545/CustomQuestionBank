package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomCheckBox extends JCheckBox {

    public CustomCheckBox(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        // Get the system's default font for checkboxes
        Font defaultFont = UIManager.getFont("CheckBox.font");
        // Get the default font size
        int defaultFontSize = defaultFont.getSize();
        // Set the custom font family and style, but keep the default font size
        setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize)); // Set custom font for the checkbox text
    }
}