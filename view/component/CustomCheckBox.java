package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomCheckBox extends JCheckBox {

    public CustomCheckBox(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        Font defaultFont = UIManager.getFont("CheckBox.font");
        int defaultFontSize = defaultFont.getSize();
        setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));
    }
    
}