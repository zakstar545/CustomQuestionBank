package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        Font defaultFont = UIManager.getFont("Button.font");  

        setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFont.getSize()));
        setBackground(new Color(220, 220, 220));
        setFocusPainted(false);
        setMargin(new Insets(8, 12, 8, 12)); 
        setBorderPainted(true);
    }

}

