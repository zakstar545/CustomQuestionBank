package view.page;

import model.entity.Question;
import view.component.Common;
import view.component.CustomButton;
import view.component.CustomCheckBox;
import view.component.CustomLabel;
import view.component.CustomPanel;
import model.core.QuestionBank;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;


public class SolveQuestionsPage extends CustomPanel {
    // Define instance variables
    private JComboBox<String> subjectBox;
    private JComboBox<String> topicBox;
    private CustomCheckBox[] paperBoxes;
    private CustomCheckBox[] difficultyBoxes;
    private CustomCheckBox[] timeBoxes;
    private CustomButton homeButton;
    private CustomPanel mainContent;
    private CustomPanel titlePanel;
    private JSplitPane splitPane;
    private CustomPanel topBar;
    private CustomPanel optionsContainer;
    private CustomPanel questionsContainer;
    private LinkedList<Question> questions;
    private CustomPanel questionsPanel;

    Font defaultFont = UIManager.getFont("CheckBox.font");
    // Get the default font size
    int defaultFontSize = defaultFont.getSize();


    // Constructor for SolveQuestionsPage
    public SolveQuestionsPage() {
        setLayout(new BorderLayout(10, 10)); // Set the layout of the panel
        
        // Load sample questions into QuestionBank
        QuestionBank.loadSampleQuestions();
        
        // Main content panel with padding
        mainContent = new CustomPanel(new BorderLayout(10, 10));
  
        
        // Add the title panel
        titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        questionsPanel = createQuestionsPanel();
        // Create the main split content
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createQuestionSortingPanel(), questionsPanel);
        splitPane.setResizeWeight(0.25); // Give 25% space to the left panel
        mainContent.add(splitPane, BorderLayout.CENTER);    //Add the split pane which has the 2 primary panels to the main panel
        
        // Add navigation buttons
        topBar = createTopBar();
        
        mainContent.add(topBar, BorderLayout.NORTH);    //Add topBar to the main content panel
        
        add(mainContent, BorderLayout.CENTER);   //Add the main content panel to the SolveQuestionPage panel
    }

    //GUI Builder/create methods: One for title panel, one for the scrollable question sorting pane 
    // , one quseion sorting panel, one for the questions panel,
    // This method creates returns a title panel with the title`
    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Set background color

        CustomLabel title = new CustomLabel("Solve Questions");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 24)); // Set font for the label text

        panel.add(title, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return panel;
    }
    
    private CustomPanel createTopBar() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        homeButton = new CustomButton("Go Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        homeButton.setBackground(new Color(220, 220, 220)); // Set button background color
        homeButton.setFocusPainted(false);
        
        panel.add(homeButton, BorderLayout.WEST);  //Add home button to topBar

        return panel;
    }   

    // This method creates the panel that will contain the question sorting options using gridbag layout
    //It adds each element group in its own seperate roww
    private CustomPanel createQuestionSortingPanel() {
        // Create a panel with GridBagLayout to hold the JScrollPane
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        optionsContainer = new CustomPanel(new GridBagLayout());
        optionsContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Question Sorting"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
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

        CustomLabel subjectLabel = new CustomLabel("Subject");

        subjectLabel.setFont(defaultFont.deriveFont(Font.BOLD));

        optionsContainer.add(subjectLabel, containerGbc);

        containerGbc.gridy = 1;
        subjectBox = new JComboBox<>();
        subjectBox.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize)); // Set custom font for the checkbox text which we can then call getFont() to put on other elements

        optionsContainer.add(subjectBox, containerGbc);
        
        // Topic
        containerGbc.gridy = 2;

        CustomLabel topicLabel = new CustomLabel("Topic");
        topicLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(topicLabel, containerGbc);
        
        containerGbc.gridy = 3;
        topicBox = new JComboBox<>();
        topicBox.setFont(subjectBox.getFont()); // Set custom font for the checkbox text
        optionsContainer.add(topicBox, containerGbc);
        
        // Paper options
        containerGbc.gridy = 4;

        CustomLabel paperLabel = new CustomLabel("Paper");
        paperLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(paperLabel, containerGbc);
        
        containerGbc.gridy = 5;
        CustomPanel paperPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        paperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        paperBoxes = new CustomCheckBox[3];
        String[] papers = {"1", "2", "3"};
        for (int i = 0; i < 3; i++) {
            paperBoxes[i] = new CustomCheckBox(papers[i]);
            paperBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            paperPanel.add(paperBoxes[i]);
        }
        optionsContainer.add(paperPanel, containerGbc);
        
        // Difficulty options
        containerGbc.gridy = 6;

        CustomLabel difficultyLabel = new CustomLabel("Difficulty");
        difficultyLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(difficultyLabel, containerGbc);
        
        containerGbc.gridy = 7;
        CustomPanel difficultyPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        difficultyBoxes = new CustomCheckBox[3];
        String[] difficulties = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < 3; i++) {
            difficultyBoxes[i] = new CustomCheckBox(difficulties[i]);
            difficultyBoxes[i].setBackground(new Color(245, 245, 245)); // Set background color
            difficultyPanel.add(difficultyBoxes[i]);
        }

        optionsContainer.add(difficultyPanel, containerGbc);
        
        // Time to Solve options
        containerGbc.gridy = 8;

        CustomLabel timeToSolveLabel = new CustomLabel("Time to Solve (minutes)");
        timeToSolveLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(timeToSolveLabel, containerGbc);
        
        containerGbc.gridy = 9;
        CustomPanel timePanel = new CustomPanel(new GridLayout(0, 2));
        timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        timeBoxes = new CustomCheckBox[5];
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60"};
        for (int i = 0; i < 5; i++) {
            timeBoxes[i] = new CustomCheckBox(times[i]);
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
    private CustomPanel createQuestionsPanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        questionsContainer = new CustomPanel();
        questionsContainer.setLayout(new BoxLayout(questionsContainer, BoxLayout.Y_AXIS));
        questionsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Create and add question cards
        questions = QuestionBank.questions;
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
    private CustomPanel createQuestionCard(Question question) {
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
        CustomPanel topicsPanel = new CustomPanel(new GridLayout(6, 1));
        topicsPanel.setBackground(new Color(255, 255, 255)); // Set background color
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        topicsPanel.add(new CustomLabel("IB Subject: " + question.getSubject()));
        topicsPanel.add(new CustomLabel("Topic: " + question.getTopic()));
        topicsPanel.add(new CustomLabel("IB Paper: " + question.getPaper()));
        topicsPanel.add(new CustomLabel("Difficulty: " + question.getDifficulty()));
        topicsPanel.add(new CustomLabel("Time to Solve: " + question.getTimeToSolve() + " minutes"));
        topicsPanel.add(new CustomLabel("Marks: " + question.getMarks() + " marks"));


        header.add(topicsPanel, BorderLayout.CENTER);
        
        // Action buttons
        CustomPanel buttonsPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBackground(new Color(255, 255, 255)); // Set background color
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        CustomButton addToPracticeTest = new CustomButton("Add to Practice Test");
        CustomButton markschemeButton = new CustomButton("Show Markscheme");

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

    public CustomCheckBox[] getPaperBoxes() {
        return paperBoxes;
    }

    public CustomCheckBox[] getDifficultyBoxes() {
        return difficultyBoxes;
    }

    public CustomCheckBox[] getTimeBoxes() {
        return timeBoxes;
    }

    public CustomButton getHomeButton() {
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