package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {

    public CustomPanel() {
        initialize();
    }

    public CustomPanel(LayoutManager layout) {
        super(layout);
        initialize();
    }

    private void initialize() {
        setBackground(new Color(245, 245, 245)); 
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
}