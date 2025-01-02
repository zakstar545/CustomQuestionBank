package view;

import model.entity.Question;
import model.core.QuestionBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.TimerTask;
import java.util.Timer;

public class SolveQuestionsPage extends JPanel {
    // Define instance variables
    private JComboBox<String> subjectBox;
    private JComboBox<String> topicBox;
    private JCheckBox[] paperBoxes;
    private JCheckBox[] difficultyBoxes;
    private JCheckBox[] timeBoxes;
    private JButton homeButton;
    private JPanel questionsContainer;
    private Timer resizeTimer;
    private ArrayList<ImageIcon> originalImages; // Store original images

    // Constructor for SolveQuestionsPage
    public SolveQuestionsPage() {
        setLayout(new BorderLayout(10, 10)); // Set the layout of the panel
        setBackground(new Color(245, 245, 245)); // Set background color
        
        // Load sample questions into QuestionBank
        QuestionBank.loadSampleQuestions();
        
        // Main content panel with padding
        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContent.setBackground(new Color(245, 245, 245)); // Set background color
        
        // Add the title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        // Create the main split content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                createScrollableQuestionSortingPanel(),
                createQuestionsPanel());
        splitPane.setResizeWeight(0.25); // Give 25% space to the left panel
        mainContent.add(splitPane, BorderLayout.CENTER);    //Add the split pane which has the 2 primary panels to the main panel
        
        // Add navigation buttons
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(245, 245, 245)); // Set background color
        homeButton = new JButton("Go Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        homeButton.setBackground(new Color(220, 220, 220)); // Set button background color
        homeButton.setFocusPainted(false);
        
        topBar.add(homeButton, BorderLayout.WEST);  //Add home button to topBar
        
        mainContent.add(topBar, BorderLayout.NORTH);    //Add topBar to the main content panel
        
        add(mainContent, BorderLayout.CENTER);   //Add the main content panel to the SolveQuestionPage panel
    }

    // This method creates returns a title panel with the title`
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Set background color
        JLabel title = new JLabel("Custom Question Bank", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return panel;
    }
    

    // This method creates the the scroll pane for the question sorting oanel
    private JScrollPane createScrollableQuestionSortingPanel() {
        JPanel panel = createQuestionSortingPanel();
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Enable horizontal scroll bar if needed
        return scrollPane;
    }

    // This method creates the panel that will contain the question sorting options using gridbag layout
    //It adds each element group in its own seperate roww
    private JPanel createQuestionSortingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Question Sorting"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(new Color(245, 245, 245)); // Set background color
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Subject
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Subject"), gbc);
        
        gbc.gridy = 1;
        subjectBox = new JComboBox<>();
        panel.add(subjectBox, gbc);
        
        // Topic
        gbc.gridy = 2;
        panel.add(new JLabel("Topic"), gbc);
        
        gbc.gridy = 3;
        topicBox = new JComboBox<>();
        panel.add(topicBox, gbc);
        
        // Paper options
        gbc.gridy = 4;
        panel.add(new JLabel("Paper"), gbc);
        
        gbc.gridy = 5;
        JPanel paperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paperPanel.setBackground(new Color(245, 245, 245)); // Set background color
        paperBoxes = new JCheckBox[3];
        String[] papers = {"1", "2", "3"};
        for (int i = 0; i < 3; i++) {
            paperBoxes[i] = new JCheckBox(papers[i]);
            paperBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            paperPanel.add(paperBoxes[i]);
            
            // Add ActionListener to allow deselection and track active checkbox
            paperBoxes[i].addActionListener(e -> {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    for (JCheckBox button : paperBoxes) {
                        if (button != source) {
                            button.setSelected(false);
                        }
                    }
                    System.out.println("Active Paper: " + source.getText());
                } else {
                    source.setSelected(false);
                    System.out.println("No active Paper");
                }
            });
        }
        panel.add(paperPanel, gbc);
        
        // Difficulty options
        gbc.gridy = 6;
        panel.add(new JLabel("Difficulty"), gbc);
        
        gbc.gridy = 7;
        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        difficultyPanel.setBackground(new Color(245, 245, 245)); // Set background color
        difficultyBoxes = new JCheckBox[3];
        String[] difficulties = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < 3; i++) {
            difficultyBoxes[i] = new JCheckBox(difficulties[i]);
            difficultyBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            difficultyPanel.add(difficultyBoxes[i]);
            
            // Add ActionListener to allow deselection and track active checkbox
            difficultyBoxes[i].addActionListener(e -> {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    for (JCheckBox button : difficultyBoxes) {
                        if (button != source) {
                            button.setSelected(false);
                        }
                    }
                    System.out.println("Active Difficulty: " + source.getText());
                } else {
                    source.setSelected(false);
                    System.out.println("No active Difficulty");
                }
            });
        }
        panel.add(difficultyPanel, gbc);
        
        // Time to Solve options
        gbc.gridy = 8;
        panel.add(new JLabel("Time To Solve (Minutes)"), gbc);
        
        gbc.gridy = 9;
        JPanel timePanel = new JPanel(new GridLayout(0, 2));
        timePanel.setBackground(new Color(245, 245, 245)); // Set background color
        timeBoxes = new JCheckBox[5];
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60"};
        for (int i = 0; i < 5; i++) {
            timeBoxes[i] = new JCheckBox(times[i]);
            timeBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            timePanel.add(timeBoxes[i]);
            
            // Add ActionListener to allow deselection and track active checkbox
            timeBoxes[i].addActionListener(e -> {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    for (JCheckBox button : timeBoxes) {
                        if (button != source) {
                            button.setSelected(false);
                        }
                    }
                    System.out.println("Active Time to Solve: " + source.getText());
                } else {
                    source.setSelected(false);
                    System.out.println("No active Time to Solve");
                }
            });
        }
        panel.add(timePanel, gbc);
        
        // Add filler to push everything to the top
        gbc.gridy = 10;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);
        
        return panel;
    }

    
    // This method creates the panel that will contain the questions
    private JPanel createQuestionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(new Color(245, 245, 245)); // Set background color
        
        questionsContainer = new JPanel();
        questionsContainer.setLayout(new BoxLayout(questionsContainer, BoxLayout.Y_AXIS));
        questionsContainer.setBackground(new Color(245, 245, 245)); // Set background color
        
        // Create and add question cards
        LinkedList<Question> questions = QuestionBank.getQuestions();
        for (Question question : questions) {
            questionsContainer.add(createQuestionCard(question));
            questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between cards
        }
        
        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(questionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add a component listener to the panel to scale images
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (resizeTimer != null) {
                    resizeTimer.cancel();
                }
                resizeTimer = new Timer();
                resizeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> {
                            System.out.println("Window resized to: " + panel.getSize());
                            scaleImages(panel.getWidth());
                        });
                    }
                }, 200); // Delay in milliseconds
            }
        });

        return panel;
    }

    // This method scales the images in all question cards to match the panel width while maintaining aspect ratio
    private void scaleImages(int panelWidth) {
        // Keep track of actual question index
        int questionIndex = 0;
        for (int i = 0; i < questionsContainer.getComponentCount(); i++) {
            Component component = questionsContainer.getComponent(i);
            if (component instanceof JPanel) {
                JPanel card = (JPanel) component;
                for (Component cardComponent : card.getComponents()) {
                    if (cardComponent instanceof JLabel) {
                        JLabel content = (JLabel) cardComponent;
                        // Get the original image from the current question
                        ImageIcon originalImage = QuestionBank.getQuestions().get(questionIndex).getQuestionImage();
                        if (originalImage != null) {
                            Image tempImage = originalImage.getImage();
                            if (panelWidth > 0) {
                                Image tempScaledImage = tempImage.getScaledInstance(panelWidth - 100, -1, Image.SCALE_SMOOTH);
                                content.setIcon(new ImageIcon(tempScaledImage));
                            }
                        }
                    }
                }
                questionIndex++; // Increment only when we process a question card
            }
        }
    }

    // This method takes in a question object and returns the JPanel "card" for it
    private JPanel createQuestionCard(Question question) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(255, 255, 255)); // Set background color
        
        // Question header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(255, 255, 255)); // Set background color
        JLabel titleLabel = new JLabel(question.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(titleLabel, BorderLayout.NORTH);
        
        // Topics and subtopics
        JPanel topicsPanel = new JPanel(new GridLayout(4, 1));
        topicsPanel.setBackground(new Color(255, 255, 255)); // Set background color
        topicsPanel.add(new JLabel("Topic: " + question.getTopic()));
        topicsPanel.add(new JLabel("IB Subject: " + question.getSubject()));
        topicsPanel.add(new JLabel("IB Paper: " + question.getPaper()));
        topicsPanel.add(new JLabel("Time to Solve: " + question.getTimeToSolve() + " minutes"));

        header.add(topicsPanel, BorderLayout.CENTER);
        
        // Action buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBackground(new Color(255, 255, 255)); // Set background color
        JButton addToPracticeTest = new JButton("Add to Practice Test");
        JButton markschemeButton = new JButton("Markscheme");
        buttonsPanel.add(addToPracticeTest);
        buttonsPanel.add(markschemeButton);
        header.add(buttonsPanel, BorderLayout.SOUTH);
        
        card.add(header, BorderLayout.NORTH);
    
        // Question content (WILL BE THE SCREENSHOT LATER ON)
        ImageIcon image = question.getQuestionImage();    // Get the image 
        JLabel content = new JLabel(image);   //
        content.setHorizontalAlignment(JLabel.CENTER);
        card.add(content, BorderLayout.CENTER);
        
        return card;
    }
    
    public void setSubjects(Set<String> subjects) {
        subjectBox.removeAllItems();
        subjectBox.addItem("N/A");
        for (String subject : subjects) {
            subjectBox.addItem(subject);
        }
    }

    public void setTopics(Set<String> topics) {
        topicBox.removeAllItems();
        topicBox.addItem("N/A");
        for (String topic : topics) {
            topicBox.addItem(topic);
        }
    }
    
    public JComboBox<String> getSubjectBox() {
        return subjectBox;
    }

    public JComboBox<String> getTopicBox() {
        return topicBox;
    }

    public JCheckBox[] getPaperBoxes() {
        return paperBoxes;
    }

    public JCheckBox[] getDifficultyBoxes() {
        return difficultyBoxes;
    }

    public JCheckBox[] getTimeBoxes() {
        return timeBoxes;
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public void updateQuestions(ArrayList<Question> filteredQuestions) {
        // Store current panel width
        int currentWidth = questionsContainer.getWidth();
        
        // Clear and rebuild the container
        questionsContainer.removeAll();
        for (Question question : filteredQuestions) {
            JPanel card = createQuestionCard(question);
            // Scale the image immediately when creating the card
            JLabel imageLabel = findImageLabel(card);
            if (imageLabel != null && currentWidth > 0) {
                ImageIcon originalImage = question.getQuestionImage();
                if (originalImage != null) {
                    Image tempImage = originalImage.getImage();
                    Image scaledImage = tempImage.getScaledInstance(currentWidth - 100, -1, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
            questionsContainer.add(card);
            questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between cards
        }
        
        // Refresh the container
        questionsContainer.revalidate();
        questionsContainer.repaint();
    }
    
    // Helper method to find the image label in a card
    private JLabel findImageLabel(JPanel card) {
        for (Component comp : card.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getIcon() != null) {
                    return label;
                }
            }
        }
        return null;
    }
}