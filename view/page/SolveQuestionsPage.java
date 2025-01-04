package view.page;

import model.entity.Question;
import model.core.QuestionBank;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;


public class SolveQuestionsPage extends JPanel {
    // Define instance variables
    private JComboBox<String> subjectBox;
    private JComboBox<String> topicBox;
    private JCheckBox[] paperBoxes;
    private JCheckBox[] difficultyBoxes;
    private JCheckBox[] timeBoxes;
    private JButton homeButton;
    private JPanel mainContent;
    private JPanel titlePanel;
    private JSplitPane splitPane;
    private JPanel topBar;
    private JPanel optionsContainer;
    private JPanel questionsContainer;
    private LinkedList<Question> questions;
    private JPanel questionsPanel;



    // Constructor for SolveQuestionsPage
    public SolveQuestionsPage() {
        setLayout(new BorderLayout(10, 10)); // Set the layout of the panel
        setBackground(new Color(245, 245, 245)); // Set background color
        
        // Load sample questions into QuestionBank
        QuestionBank.loadSampleQuestions();
        
        // Main content panel with padding
        mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContent.setBackground(new Color(245, 245, 245)); // Set background color
        
        // Add the title panel
        titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        questionsPanel = createQuestionsPanel();
        // Create the main split content
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createQuestionSortingPanel(), questionsPanel);
        splitPane.setResizeWeight(0.25); // Give 25% space to the left panel
        mainContent.add(splitPane, BorderLayout.CENTER);    //Add the split pane which has the 2 primary panels to the main panel
        
        // Add navigation buttons
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(245, 245, 245)); // Set background color
        
        homeButton = new JButton("Go Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        homeButton.setBackground(new Color(220, 220, 220)); // Set button background color
        homeButton.setFocusPainted(false);
        
        topBar.add(homeButton, BorderLayout.WEST);  //Add home button to topBar
        
        mainContent.add(topBar, BorderLayout.NORTH);    //Add topBar to the main content panel
        
        add(mainContent, BorderLayout.CENTER);   //Add the main content panel to the SolveQuestionPage panel
    }

    //GUI Builder/create methods: One for title panel, one for the scrollable question sorting pane 
    // , one quseion sorting panel, one for the questions panel,
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
    


    // This method creates the panel that will contain the question sorting options using gridbag layout
    //It adds each element group in its own seperate roww
    private JPanel createQuestionSortingPanel() {
        // Create a panel with GridBagLayout to hold the JScrollPane
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(new Color(245, 245, 245)); // Set background color

        optionsContainer = new JPanel(new GridBagLayout());
        optionsContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Question Sorting"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        optionsContainer.setBackground(new Color(245, 245, 245)); // Set background color
        
        // Create a JScrollPane and add it to the panel
        JScrollPane scrollPane = new JScrollPane(optionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        panel.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints containerGbc = new GridBagConstraints();
        containerGbc.fill = GridBagConstraints.HORIZONTAL;
        containerGbc.insets = new Insets(5, 5, 5, 5);
        
        // Subject
        containerGbc.gridx = 0;
        containerGbc.gridy = 0;
        optionsContainer.add(new JLabel("Subject"), containerGbc);
        
        containerGbc.gridy = 1;
        subjectBox = new JComboBox<>();
        optionsContainer.add(subjectBox, containerGbc);
        
        // Topic
        containerGbc.gridy = 2;
        optionsContainer.add(new JLabel("Topic"), containerGbc);
        
        containerGbc.gridy = 3;
        topicBox = new JComboBox<>();
        optionsContainer.add(topicBox, containerGbc);
        
        // Paper options
        containerGbc.gridy = 4;
        optionsContainer.add(new JLabel("Paper"), containerGbc);
        
        containerGbc.gridy = 5;
        JPanel paperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paperPanel.setBackground(new Color(245, 245, 245)); // Set background color
        paperBoxes = new JCheckBox[3];
        String[] papers = {"1", "2", "3"};
        for (int i = 0; i < 3; i++) {
            paperBoxes[i] = new JCheckBox(papers[i]);
            paperBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            paperPanel.add(paperBoxes[i]);
        }
        optionsContainer.add(paperPanel, containerGbc);
        
        // Difficulty options
        containerGbc.gridy = 6;
        optionsContainer.add(new JLabel("Difficulty"), containerGbc);
        
        containerGbc.gridy = 7;
        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        difficultyPanel.setBackground(new Color(245, 245, 245)); // Set background color
        difficultyBoxes = new JCheckBox[3];
        String[] difficulties = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < 3; i++) {
            difficultyBoxes[i] = new JCheckBox(difficulties[i]);
            difficultyBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            difficultyPanel.add(difficultyBoxes[i]);
        }
        optionsContainer.add(difficultyPanel, containerGbc);
        
        // Time to Solve options
        containerGbc.gridy = 8;
        optionsContainer.add(new JLabel("Time To Solve (Minutes)"), containerGbc);
        
        containerGbc.gridy = 9;
        JPanel timePanel = new JPanel(new GridLayout(0, 2));
        timePanel.setBackground(new Color(245, 245, 245)); // Set background color
        timeBoxes = new JCheckBox[5];
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60"};
        for (int i = 0; i < 5; i++) {
            timeBoxes[i] = new JCheckBox(times[i]);
            timeBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            timePanel.add(timeBoxes[i]);
        }
        optionsContainer.add(timePanel, containerGbc);
        
        // Add filler to push everything to the top
        containerGbc.gridy = 10;
        containerGbc.weighty = 1.0;
        optionsContainer.add(Box.createVerticalGlue(), containerGbc);

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
        questions = QuestionBank.getQuestions();
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
    
        // Question content
        ImageIcon image = question.getQuestionImage();    // Get the image 
        JLabel content = new JLabel(image);   //
        card.add(content, BorderLayout.CENTER);
        
        return card;
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

    public JPanel getQuestionContainer() {
        return questionsContainer;
    }

    public JPanel getQuestionCard(Question question) {
        return createQuestionCard(question);
    }

    public JPanel getQuestionsPanel() {
        return questionsPanel;
    }
}