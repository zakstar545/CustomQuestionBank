package view.page;

import javax.swing.*;

import model.entity.Question;
import view.component.*;

import java.awt.*;
import java.util.LinkedList;

public class ModifyQuestionsPage extends CustomPanel{
    private JComboBox<String> subjectBox;
    private JComboBox<String> topicBox;
    private CustomCheckBox[] paperBoxes;
    private CustomCheckBox[] difficultyBoxes;
    private CustomCheckBox[] timeBoxes;
    private CustomButton homeButton;
    private CustomPanel mainContent;
    private CustomPanel titlePanel;
    private CustomPanel topBar;
    private CustomPanel uploadButtonsContainer;
    private CustomPanel subjectTopicsContainer;
    private CustomPanel addQuestionPanel;
    private LinkedList<Question> questions;
    private CustomPanel questionsPanel;
    private CustomPanel optionsContainer;
    private CustomButton addSubject;
    private CustomButton addTopic;
    private JPopupMenu addSubjectPopup;
    private JPopupMenu addTopicPopup;

    Font defaultFont = UIManager.getFont("CheckBox.font");
    // Get the default font size
    int defaultFontSize = defaultFont.getSize();

    public ModifyQuestionsPage(){
        super();
        setLayout(new BorderLayout(10, 10)); // Set the layout of the panel
        
        titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
    }

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());

        CustomLabel title = new CustomLabel("Edit Questions");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 24)); // Set font for the label text

        panel.add(title, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
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

        addSubject = new CustomButton("Add Subject");

        optionsContainer.add(addSubject, containerGbc);
        
        // Topic
        containerGbc.gridy = 2;

        CustomLabel topicLabel = new CustomLabel("Topic");
        topicLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(topicLabel, containerGbc);
        
        containerGbc.gridy = 3;
        topicBox = new JComboBox<>();
        topicBox.setFont(subjectBox.getFont()); // Set custom font for the checkbox text
        optionsContainer.add(topicBox, containerGbc);

        addTopic = new CustomButton("Add Topic");

        optionsContainer.add(addTopic, containerGbc);
        
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

    private CustomPanel createUploadButtonsContainerPanel() {
        CustomPanel buttonsPanel = new CustomPanel(new GridLayout(1,2));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        CustomPanel uploadQuestionButtonPanel = new CustomPanel(new BorderLayout());

        CustomButton uploadQuestionButton = new CustomButton("Upload Screenshot of Question");
        uploadQuestionButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, defaultFontSize));
        uploadQuestionButtonPanel.add(uploadQuestionButton, BorderLayout.CENTER);
        buttonsPanel.add(uploadQuestionButtonPanel);

        CustomPanel uploadMarkschemeButtonPanel = new CustomPanel(new BorderLayout());

        CustomButton uploadMarkschemeButton = new CustomButton("Upload Screenshot of Markscheme");
        uploadMarkschemeButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, defaultFontSize));
        uploadMarkschemeButton.add(uploadMarkschemeButton, BorderLayout.CENTER);
        buttonsPanel.add(uploadMarkschemeButtonPanel);

        return buttonsPanel;
    }

    private createSubjectPopup



}
