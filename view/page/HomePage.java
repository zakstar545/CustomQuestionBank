package view.page;

import view.component.*;

import javax.swing.*;
import java.awt.*;

public class HomePage extends CustomPanel {

    private CustomButton solveQuestionsButton;
    private CustomButton practiceTestButton;
    private CustomButton modifyButton;
    private CustomPanel titlePanel;
    private CustomPanel buttonsPanels;

    public HomePage() {
        setLayout(new BorderLayout());

        titlePanel = createTitlePanel();

        buttonsPanels = createButtonsPanel();

        // Adding the title panel and buttons panel to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(buttonsPanels, BorderLayout.CENTER);
    }

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(200, 100, 0, 100));

        CustomLabel title = new CustomLabel("Custom Question Bank");
        Font currentFont = title.getFont();
        title.setFont(currentFont.deriveFont(74f)); // Change only the size to 34
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private CustomPanel createButtonsPanel() {
        CustomPanel panel = new CustomPanel(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Set default border

        solveQuestionsButton = new CustomButton("Solve Questions");
        solveQuestionsButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 20)); // 

        practiceTestButton = new CustomButton("Practice Test");
        practiceTestButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 20)); // 

        modifyButton = new CustomButton("Modify Questions");
        modifyButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 20)); // 

        panel.add(createPanelWithButton(solveQuestionsButton));
        panel.add(createPanelWithButton(practiceTestButton));
        //panel.add(createPanelWithButton(modifyButton));

        return panel;
    }

    private CustomPanel createPanelWithButton(CustomButton button) {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Set default border

        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    public CustomButton getSolveQuestionsButton() {
        return solveQuestionsButton;
    }

    public CustomButton getPracticeTestButton() {
        return practiceTestButton;
    }

    public CustomButton getModifyButton() {
        return modifyButton;
    }
}
