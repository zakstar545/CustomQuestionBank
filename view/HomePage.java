package view;

// Import all of swing and awt
import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private JLabel title;
    private JButton solveQuestionsButton;
    private JButton practiceTestButton;
    private JButton modifyButton;
    private JPanel titlePanel;
    private JPanel buttonsPanels;
    private JPanel solveQuestionsButtonPanel;
    private JPanel practiceTestButtonPanel;
    private JPanel modifyButtonPanel;

    public HomePage(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Set background color

        // Initializing the title Panel and calling JPanel methods
        // to set up its properties
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        titlePanel.setBackground(new Color(245, 245, 245)); // Set background color

        // Initializing the title label and setting its properties
        title = new JLabel("Custom Question Bank"); // Creating a label
        title.setFont(new Font("Bebas Neue", Font.BOLD, 34)); // Setting the size and font of the title
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // These methods work to keep the title centered
        // both vertically and horizontally
        titlePanel.add(Box.createVerticalGlue());
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalGlue());

        // Initializing the buttonsPanel and setting its properties
        buttonsPanels = new JPanel();
        buttonsPanels.setLayout(new GridLayout(0, 3));
        buttonsPanels.setBackground(new Color(245, 245, 245)); // Set background color

        // Initializing the first button and setting its properties
        solveQuestionsButton = new JButton("Solve Questions");
        solveQuestionsButton.setFont(new Font("Bebas Neue", Font.BOLD, 20));
        solveQuestionsButton.setBackground(new Color(220, 220, 220)); // Set button background color
        solveQuestionsButton.setFocusPainted(false);

        // Initializing the first button panel which
        // will contain the first button
        solveQuestionsButtonPanel = new JPanel();
        solveQuestionsButtonPanel.setLayout(new BoxLayout(solveQuestionsButtonPanel, BoxLayout.Y_AXIS));
        solveQuestionsButtonPanel.setBackground(new Color(245, 245, 245)); // Set background color

        // These methods keep the button centered in its panel
        // horizontally and vertically
        solveQuestionsButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        solveQuestionsButtonPanel.add(Box.createVerticalGlue());
        solveQuestionsButtonPanel.add(solveQuestionsButton);
        solveQuestionsButtonPanel.add(Box.createVerticalGlue());

        // Repeating the process for the other buttons
        practiceTestButton = new JButton("Practice Test");
        practiceTestButton.setFont(new Font("Bebas Neue", Font.BOLD, 20));
        practiceTestButton.setBackground(new Color(220, 220, 220)); // Set button background color
        practiceTestButton.setFocusPainted(false);

        practiceTestButtonPanel = new JPanel();
        practiceTestButtonPanel.setLayout(new BoxLayout(practiceTestButtonPanel, BoxLayout.Y_AXIS));
        practiceTestButtonPanel.setBackground(new Color(245, 245, 245)); // Set background color

        practiceTestButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        practiceTestButtonPanel.add(Box.createVerticalGlue());
        practiceTestButtonPanel.add(practiceTestButton);
        practiceTestButtonPanel.add(Box.createVerticalGlue());

        modifyButton = new JButton("Modify Questions");
        modifyButton.setFont(new Font("Bebas Neue", Font.BOLD, 20));
        modifyButton.setBackground(new Color(220, 220, 220)); // Set button background color
        modifyButton.setFocusPainted(false);

        modifyButtonPanel = new JPanel();
        modifyButtonPanel.setLayout(new BoxLayout(modifyButtonPanel, BoxLayout.Y_AXIS));
        modifyButtonPanel.setBackground(new Color(245, 245, 245)); // Set background color

        modifyButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        modifyButtonPanel.add(Box.createVerticalGlue());
        modifyButtonPanel.add(modifyButton);
        modifyButtonPanel.add(Box.createVerticalGlue());

        // Adding the 3 button panels to the main
        // parent button panel
        buttonsPanels.add(solveQuestionsButtonPanel);
        buttonsPanels.add(practiceTestButtonPanel);
        buttonsPanels.add(modifyButtonPanel);

        // Adding the title panel and buttons panel to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(buttonsPanels, BorderLayout.CENTER);
    }

    public JButton getSolveQuestionsButton() {
        return solveQuestionsButton;
    }

    public JButton getPracticeTestButton() {
        return practiceTestButton;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }
}
