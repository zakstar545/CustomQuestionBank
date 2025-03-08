package view.page;

import javax.swing.*;
import java.awt.*;
import model.entity.Question;
import model.core.PracticeTest;
import view.component.*;
import java.util.LinkedList;

public class PracticeTestPage extends CustomPanel {
    private CustomButton homeButton;
    private CustomButton generateTestButton;
    private CustomButton generateMarkschemeButton;
    private CustomButton clearAllButton;
    private JPanel questionsContainer;
    private CustomLabel totalQuestionsLabel;
    private CustomLabel totalMarksLabel;
    private CustomLabel estimatedTimeLabel;
    private CustomPanel summaryPanel;
    private JTextField testTitleField;
    
    public PracticeTestPage() {
        setLayout(new BorderLayout(10, 10));
        
        // Create the title panel
        CustomPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        // Create main content panel
        CustomPanel mainContent = new CustomPanel(new BorderLayout(10, 10));
        
        // Create top bar
        CustomPanel topBar = createTopBar();
        mainContent.add(topBar, BorderLayout.NORTH);
        
        // Create split pane for question list and options
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
        createQuestionsPanel(), 
        createOptionsPanel());

        splitPane.setResizeWeight(0.7); // 70% for questions, 30% for options
        mainContent.add(splitPane, BorderLayout.CENTER);
        
        // Add action buttons at the bottom
        CustomPanel actionButtonsPanel = createActionButtonsPanel();
        mainContent.add(actionButtonsPanel, BorderLayout.SOUTH);
        
        add(mainContent, BorderLayout.CENTER);
    }

    private void savePracticeTestData() {
        model.service.FileManager.savePracticeTest();
    }
    
    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Set background color

        CustomLabel title = new CustomLabel("Practice Test Builder");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 24));
        
        panel.add(title, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return panel;
    }
    
    private CustomPanel createTopBar() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        homeButton = new CustomButton("< Back to Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        homeButton.setBackground(new Color(220, 220, 220)); // Set button background color
        homeButton.setFocusPainted(false);
        
        panel.add(homeButton, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createQuestionsPanel() {
        // Create panel to hold questions
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add title border similar to Test Options
        CustomPanel containerPanel = new CustomPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Questions Included"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Create container for questions with vertical BoxLayout - MATCHING SOLVEQPAGE
        questionsContainer = new CustomPanel();
        questionsContainer.setLayout(new BoxLayout(questionsContainer, BoxLayout.Y_AXIS));
        questionsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Add to scrollpane
        JScrollPane scrollPane = new JScrollPane(questionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(containerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private CustomPanel createOptionsPanel() {
        // Matching the style of the question sorting panel in SolveQuestionsPage
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create container with GridBagLayout
        CustomPanel optionsContainer = new CustomPanel(new GridBagLayout());
        optionsContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Test Options"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        
        // Test title section
        gbc.gridx = 0;
        gbc.gridy = 0;
        CustomLabel titleLabel = new CustomLabel("Test Title:");
        titleLabel.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 14));
        optionsContainer.add(titleLabel, gbc);
        
        gbc.gridy++;
        testTitleField = new JTextField(20);
        testTitleField.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, 14));
        optionsContainer.add(testTitleField, gbc);
        
        // Summary info
        gbc.gridy++;
        CustomLabel summaryTitle = new CustomLabel("Test Summary");
        summaryTitle.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 14));
        optionsContainer.add(summaryTitle, gbc);
        
        gbc.gridy++;
        summaryPanel = new CustomPanel(new GridLayout(0, 1, 0, 5));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        totalQuestionsLabel = new CustomLabel("Total Questions: 0");
        totalMarksLabel = new CustomLabel("Total Marks: 0");
        estimatedTimeLabel = new CustomLabel("Estimated Time: 0 min");
        
        summaryPanel.add(totalQuestionsLabel);
        summaryPanel.add(totalMarksLabel);
        summaryPanel.add(estimatedTimeLabel);
        
        optionsContainer.add(summaryPanel, gbc);
        
        // REMOVED: Export options section with cover page checkbox and paper size dropdown
        
        // Add filler to push everything up
        gbc.gridy++;
        gbc.weighty = 1.0;
        optionsContainer.add(Box.createVerticalGlue(), gbc);
        
        // Add to scroll pane
        JScrollPane scrollPane = new JScrollPane(optionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private CustomPanel createActionButtonsPanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Create a panel for the buttons with CENTER alignment instead of RIGHT
        CustomPanel buttonsPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        // Create buttons without the colored backgrounds or bold font
        clearAllButton = new CustomButton("Clear All Questions");
        clearAllButton.setFont(new Font("Arial", Font.PLAIN, 14));
        
        generateTestButton = new CustomButton("Generate Practice Test PDF");
        generateTestButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Changed from BOLD to PLAIN
        
        generateMarkschemeButton = new CustomButton("Generate Markscheme PDF");
        generateMarkschemeButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Changed from BOLD to PLAIN
        
        // Add buttons to panel
        buttonsPanel.add(clearAllButton);
        buttonsPanel.add(generateTestButton);
        buttonsPanel.add(generateMarkschemeButton);
        
        panel.add(buttonsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void updateQuestionsList() {
        // Clear existing questions
        questionsContainer.removeAll();
        
        // Get updated list of questions from model
        LinkedList<Question> questions = PracticeTest.practiceQuestions;
        
        if (questions.isEmpty()) {
            // Display a message when no questions
            CustomLabel emptyLabel = new CustomLabel("No questions added to the test yet");
            emptyLabel.setFont(new Font(Common.getDefaultFont(), Font.ITALIC, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setForeground(Color.GRAY);
            questionsContainer.add(emptyLabel);
        } else {
            // Update the questions list - MATCH STYLE FROM SOLVEQPAGE
            for (Question question : questions) {
                questionsContainer.add(createQuestionCard(question));
                questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Add 10px spacing
            }
        }
        
        // Update summary info
        updateSummaryInfo();
        
        // Refresh UI
        revalidate();
        repaint();
    }
    
    private CustomPanel createQuestionCard(Question question) {
        // Match the style from SolveQuestionsPage.createQuestionCard
        CustomPanel card = new CustomPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(255, 255, 255)); // Set background color
        
        // Question header
        CustomPanel header = new CustomPanel(new BorderLayout());
        header.setBackground(new Color(255, 255, 255)); // Set background color
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        CustomLabel titleLabel = new CustomLabel(question.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(titleLabel, BorderLayout.NORTH);
        
        // Topics and subtopics
        CustomPanel topicsPanel = new CustomPanel(new GridLayout(3, 1));
        topicsPanel.setBackground(new Color(255, 255, 255)); // Set background color
        topicsPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        
        // Match format from SolveQuestionsPage
        topicsPanel.add(new CustomLabel("Subject: " + question.getSubject() + " | Topic: " + question.getTopic()));
        topicsPanel.add(new CustomLabel("Paper: " + question.getPaper() + " | Difficulty: " + question.getDifficulty()));
        topicsPanel.add(new CustomLabel("Time: " + question.getTimeToSolve() + " min | Marks: " + question.getMarks()));

        header.add(topicsPanel, BorderLayout.CENTER);
        
        // Action buttons - MODIFIED: Now all buttons are in the same panel
        CustomPanel buttonsPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonsPanel.setBackground(new Color(255, 255, 255));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Show Question and Show Markscheme buttons FIRST
        CustomButton showQuestionButton = new CustomButton("Show Question");
        CustomButton showMarkschemeButton = new CustomButton("Show Markscheme");
        
        // Add the view buttons FIRST (on the left)
        buttonsPanel.add(showQuestionButton);
        buttonsPanel.add(showMarkschemeButton);
        
        // Then add movement buttons
        CustomButton moveUpButton = new CustomButton("Move Up");
        CustomButton moveDownButton = new CustomButton("Move Down");
        CustomButton removeButton = new CustomButton("Remove");

        int index = PracticeTest.practiceQuestions.indexOf(question);
        if (index > 0) {
            buttonsPanel.add(moveUpButton);
        }
        if (index < PracticeTest.practiceQuestions.size() - 1) {
            buttonsPanel.add(moveDownButton);
        }
        buttonsPanel.add(removeButton);

        // Add action listeners
        showQuestionButton.addActionListener(e -> {
            // Show question image in a dialog
            showImageDialog(question.getQuestionImage(), "Question");
        });
        
        showMarkschemeButton.addActionListener(e -> {
            // Show markscheme image in a dialog
            showImageDialog(question.getMarkschemeImage(), "Markscheme");
        });
        
        moveUpButton.addActionListener(e -> {
            PracticeTest.practiceQuestions.remove(question);
            PracticeTest.practiceQuestions.add(index - 1, question);
            updateQuestionsList();
            savePracticeTestData(); // Add this line
        });
        
        moveDownButton.addActionListener(e -> {
            PracticeTest.practiceQuestions.remove(question);
            PracticeTest.practiceQuestions.add(index + 1, question);
            updateQuestionsList();
            savePracticeTestData(); // Add this line
        });
        
        removeButton.addActionListener(e -> {
            PracticeTest.removeQuestion(question);
            updateQuestionsList();
            savePracticeTestData(); // Add this line
        });

        header.add(buttonsPanel, BorderLayout.SOUTH);
        card.add(header, BorderLayout.NORTH);

        return card;
    }

    // Method to show images in a dialog
    private void showImageDialog(ImageIcon image, String title) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(0, 5)); // Add vertical gap between components

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.8);
        int maxHeight = (int) (screenSize.getHeight() * 0.8);

        // Create panel with dynamic width based on screen size
        JPanel imagePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                if (image != null) {
                    double imageRatio = (double) image.getIconHeight() / image.getIconWidth();
                    int width = maxWidth;
                    int height = (int) (width * imageRatio);

                    if (height > maxHeight) {
                        height = maxHeight;
                        width = (int) (height / imageRatio);
                    }

                    return new Dimension(width, height);
                }
                return super.getPreferredSize();
            }
        };
        imagePanel.setLayout(new BorderLayout());

        // Scale image
        if (image != null) {
            Dimension panelSize = imagePanel.getPreferredSize();
            Image scaledImage = image.getImage().getScaledInstance(
                panelSize.width, panelSize.height, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        }

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Create button panel with proper spacing
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        CustomButton closeButton = new CustomButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void updateSummaryInfo() {
        LinkedList<Question> questions = PracticeTest.practiceQuestions;
        
        // Calculate totals
        int totalQuestions = questions.size();
        int totalMarks = 0;
        int estimatedTimeMin = 0;
        int estimatedTimeMax = 0;
        
        for (Question question : questions) {
            totalMarks += question.getMarks();
            
            // Estimate time based on the TimeToSolve enum
            String timeRange = question.getTimeToSolve();
            switch (timeRange) {
                case "0-1": 
                    estimatedTimeMin += 0; estimatedTimeMax += 1; 
                    break;
                case "1-5": 
                    estimatedTimeMin += 1; estimatedTimeMax += 5; 
                    break;
                case "5-10": 
                    estimatedTimeMin += 5; estimatedTimeMax += 10; 
                    break;
                case "10-35": 
                    estimatedTimeMin += 10; estimatedTimeMax += 35; 
                    break;
                case "35-60+": 
                    estimatedTimeMin += 35; estimatedTimeMax += 60; 
                    break;
            }
        }
        
        // Update labels
        totalQuestionsLabel.setText("Total Questions: " + totalQuestions);
        totalMarksLabel.setText("Total Marks: " + totalMarks);
        estimatedTimeLabel.setText("Estimated Time: " + estimatedTimeMin + "-" + estimatedTimeMax + " min");
    }
    
    // Getters for the controller to access components
    public CustomButton getHomeButton() {
        return homeButton;
    }
    
    public CustomButton getGenerateTestButton() {
        return generateTestButton;
    }
    
    public CustomButton getGenerateMarkschemeButton() {
        return generateMarkschemeButton;
    }
    
    public CustomButton getClearAllButton() {
        return clearAllButton;
    }
    
    public String getTestTitle() {
        return testTitleField.getText().trim();
    }
}