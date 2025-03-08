package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text) {
        super(text); 
        initialize();
    }

    private void initialize() {
        Font defaultFont = UIManager.getFont("Label.font");
        int defaultFontSize = defaultFont.getSize();
        setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize)); 
    }
    
}