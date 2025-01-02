package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class TEMPLATE extends JFrame {
    private JComboBox<String> subjectBox;
    private JComboBox<String> topicBox;
    private JCheckBox[] paperBoxes;
    private JCheckBox[] difficultyBoxes;
    private JCheckBox[] timeBoxes;
    private JPanel questionsContainer;
    private ArrayList<Question> questions;
    
    public TEMPLATE() {
        setTitle("Question Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Initialize sample questions
        initializeQuestions();
        
        // Main content panel with padding
        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add the title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        // Create the main split content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                createQuestionSortingPanel(),
                createQuestionsPanel());
        splitPane.setResizeWeight(0.3); // Give 30% space to the left panel
        mainContent.add(splitPane, BorderLayout.CENTER);
        
        // Add navigation buttons
        JPanel topBar = new JPanel(new BorderLayout());
        JButton homeButton = new JButton("< Go Home");
        homeButton.addActionListener(e -> System.out.println("Go Home clicked"));
        
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        
        topBar.add(homeButton, BorderLayout.WEST);
        topBar.add(rightButtons, BorderLayout.EAST);
        
        mainContent.add(topBar, BorderLayout.NORTH);
        
        add(mainContent, BorderLayout.CENTER);
        
        // Set preferred size and make visible
        setPreferredSize(new Dimension(1200, 800));
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Mortar & Pestle", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return panel;
    }
    
    private JPanel createQuestionSortingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Question Sorting"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Subject
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Subject"), gbc);
        
        gbc.gridy = 1;
        String[] subjects = {"Choose Subject...", "Mathematics", "Physics", "Chemistry", "Biology"};
        subjectBox = new JComboBox<>(subjects);
        subjectBox.setToolTipText("Choose a subject");
        panel.add(subjectBox, gbc);
        
        // Topic
        gbc.gridy = 2;
        panel.add(new JLabel("Topic"), gbc);
        
        gbc.gridy = 3;
        String[] topics = {"Choose Topic...", "Calculus", "Algebra", "Statistics", "Mechanics"};
        topicBox = new JComboBox<>(topics);
        topicBox.setToolTipText("Choose a topic");
        panel.add(topicBox, gbc);
        
        // Paper options
        gbc.gridy = 4;
        panel.add(new JLabel("Paper"), gbc);
        
        gbc.gridy = 5;
        JPanel paperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paperBoxes = new JCheckBox[3];
        for (int i = 0; i < 3; i++) {
            paperBoxes[i] = new JCheckBox(String.valueOf(i + 1));
            paperPanel.add(paperBoxes[i]);
        }
        panel.add(paperPanel, gbc);
        
        // Difficulty options
        gbc.gridy = 6;
        panel.add(new JLabel("Difficulty"), gbc);
        
        gbc.gridy = 7;
        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        difficultyBoxes = new JCheckBox[3];
        String[] difficulties = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < 3; i++) {
            difficultyBoxes[i] = new JCheckBox(difficulties[i]);
            difficultyPanel.add(difficultyBoxes[i]);
        }
        panel.add(difficultyPanel, gbc);
        
        // Time to Solve options
        gbc.gridy = 8;
        panel.add(new JLabel("Time To Solve (Minutes)"), gbc);
        
        gbc.gridy = 9;
        JPanel timePanel = new JPanel(new GridLayout(0, 2));
        timeBoxes = new JCheckBox[5];
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60"};
        for (int i = 0; i < 5; i++) {
            timeBoxes[i] = new JCheckBox(times[i]);
            timePanel.add(timeBoxes[i]);
        }
        panel.add(timePanel, gbc);
        
        // Add filler to push everything to the top
        gbc.gridy = 10;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);
        
        return panel;
    }

    // ... (rest of the previous code remains the same)
    
    private void initializeQuestions() {
        questions = new ArrayList<>();
        // Add sample questions
        questions.add(new Question("18M.2.HL.TZ0.18", 
            "option-d-object-oriented-programming",
            "d.4-advanced-program-development",
            "Full details of the customers are stored as objects of the Customer class...",
            new String[]{"Markscheme", "Examiners report", "Add to PDF"}));
            
        questions.add(new Question("18M.2.HL.TZ0.19",
            "option-d-object-oriented-programming",
            "d.4-advanced-program-development",
            "Consider the following recursive method...",
            new String[]{"Markscheme", "Examiners report", "Add to PDF"}));

        questions.add(new Question("18M.2.HL.TZ0.20",
            "option-d-object-oriented-programming",
            "d.4-advanced-program-development",
            "Consider a banking system where each account is represented by a class Account with the following attributes:\n\n" +
            "- accountNumber (String)\n" +
            "- balance (double)\n" +
            "- accountType (String)\n" +
            "- customerName (String)\n\n" +
            "Write a method deposit(double amount) that:\n" +
            "1. Accepts an amount to deposit\n" +
            "2. Updates the balance\n" +
            "3. Returns the new balance\n" +
            "4. Throws an IllegalArgumentException if the amount is negative\n\n" +
            "Also implement a method transfer(Account destination, double amount) that transfers money to another account.",
            new String[]{"Markscheme", "Examiners report", "Add to PDF"}));
    }
    
    private JPanel createQuestionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        questionsContainer = new JPanel();
        questionsContainer.setLayout(new BoxLayout(questionsContainer, BoxLayout.Y_AXIS));
        
        // Create and add question cards
        for (Question question : questions) {
            questionsContainer.add(createQuestionCard(question));
            questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between cards
        }
        
        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(questionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createQuestionCard(Question question) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Question header
        JPanel header = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(question.getId());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(titleLabel, BorderLayout.NORTH);
        
        // Topics and subtopics
        JPanel topicsPanel = new JPanel(new GridLayout(2, 1));
        topicsPanel.add(new JLabel("Topics: " + question.getTopic()));
        topicsPanel.add(new JLabel("Subtopics: " + question.getSubtopic()));
        header.add(topicsPanel, BorderLayout.CENTER);
        
        // Action buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addToPdfButton = new JButton("Add to PDF");
        addToPdfButton.setBackground(new Color(144, 238, 144)); // Light green
        buttonsPanel.add(addToPdfButton);
        header.add(buttonsPanel, BorderLayout.SOUTH);
        
        card.add(header, BorderLayout.NORTH);
        
        // Question content
        JTextArea content = new JTextArea(question.getContent());
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setEditable(false);
        content.setBackground(card.getBackground());
        card.add(content, BorderLayout.CENTER);
        
        return card;
    }
    
    // Question class to hold question data
    private static class Question {
        private String id;
        private String topic;
        private String subtopic;
        private String content;
        private String[] actions;
        
        public Question(String id, String topic, String subtopic, String content, String[] actions) {
            this.id = id;
            this.topic = topic;
            this.subtopic = subtopic;
            this.content = content;
            this.actions = actions;
        }
        
        public String getId() { return id; }
        public String getTopic() { return topic; }
        public String getSubtopic() { return subtopic; }
        public String getContent() { return content; }
        public String[] getActions() { return actions; }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TEMPLATE().setVisible(true);
        });
    }
}