package view.page;

import model.entity.Question;
import view.component.Common;
import view.component.CustomButton;
import view.component.CustomCheckBox;
import view.component.CustomLabel;
import view.component.CustomPanel;
import model.core.PracticeTest;
import model.core.QuestionBank;
import controller.SolveQuestionsPageController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.text.NumberFormat;
import java.util.LinkedList;

//This class has the frontend for the solve questions page
public class SolveQuestionsPage extends CustomPanel {
    private JComboBox<String> subjectBox;
    private JComboBox<String> topicBox;
    private CustomCheckBox[] paperBoxes;
    private CustomCheckBox[] difficultyBoxes;
    private CustomCheckBox[] timeBoxes;
    private CustomButton homeButton;
    private CustomButton addQuestionsButton;
    private CustomPanel mainContent;
    private CustomPanel titlePanel;
    private JSplitPane splitPane;
    private CustomPanel topBar;
    private CustomPanel optionsContainer;
    private CustomPanel questionsContainer;
    private LinkedList<Question> questions;
    private CustomPanel questionsPanel;
    private CustomButton addToPracticeTest;
    private CustomButton markschemeButton;
    private CustomButton editQuestionButton;
    private CustomButton deleteQuestionButton;
    private SolveQuestionsPageController controller;
    private CustomButton uploadQuestionButton;
    private CustomButton uploadMarkschemeButton;
    private CustomLabel questionPreviewLabel;
    private CustomLabel markschemePreviewLabel;
    private JMenuItem editSubject;
    private JMenuItem removeSubject;
    private JPopupMenu subjectPopupMenu;
    private JPopupMenu topicPopupMenu;
    private Frame frame;

    //This method sets the parent frame so that whenever a qusetion is added to practice test, it can be communicated.
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    // Associates this page with its controller for handling tha business logic
    public void setSolveQuestionsController(SolveQuestionsPageController controller) {
        this.controller = controller;
    }

    Font defaultFont = UIManager.getFont("CheckBox.font");
    int defaultFontSize = defaultFont.getSize();

    //Constructor to initialize ui and everything
    public SolveQuestionsPage() {
        setLayout(new BorderLayout(10, 10));
        
        mainContent = new CustomPanel(new BorderLayout(10, 10));
  
        titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        questionsPanel = createQuestionsPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createQuestionSortingPanel(), questionsPanel);
        splitPane.setResizeWeight(0.25); 
        mainContent.add(splitPane, BorderLayout.CENTER);    
        
        topBar = createTopBar();
        
        mainContent.add(topBar, BorderLayout.NORTH);   
        
        add(mainContent, BorderLayout.CENTER);  

        initializePopupMenus();
    }

    //Builds the title panel with the title heading
    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        CustomLabel title = new CustomLabel("Solve Questions");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Common.getDefaultFont(), Font.BOLD, 24));

        panel.add(title, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return panel;
    }
    
    //Builds the nav bar with home button and add question button
    private CustomPanel createTopBar() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        homeButton = new CustomButton("Go Home");
        panel.add(homeButton, BorderLayout.WEST); 

        addQuestionsButton = new CustomButton("Add Questions");
        panel.add(addQuestionsButton, BorderLayout.EAST); 

        return panel;
    }   

    //builds the sorting panel for filtering questions with all its filters
    private CustomPanel createQuestionSortingPanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        optionsContainer = new CustomPanel(new GridBagLayout());
        optionsContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Question Sorting"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JScrollPane scrollPane = new JScrollPane(optionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        panel.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints containerGbc = new GridBagConstraints();
        containerGbc.fill = GridBagConstraints.HORIZONTAL;
        containerGbc.insets = new Insets(5, 5, 5, 5);
        
        containerGbc.gridx = 0;


        containerGbc.gridy = 0;

        CustomLabel subjectLabel = new CustomLabel("Subject");

        subjectLabel.setFont(defaultFont.deriveFont(Font.BOLD));

        optionsContainer.add(subjectLabel, containerGbc);

        containerGbc.gridy = 1;
        subjectBox = new JComboBox<>();
        subjectBox.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));

        optionsContainer.add(subjectBox, containerGbc);
        

        containerGbc.gridy = 2;

        CustomLabel topicLabel = new CustomLabel("Topic");
        topicLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(topicLabel, containerGbc);
        

        containerGbc.gridy = 3;
        topicBox = new JComboBox<>();
        topicBox.setFont(subjectBox.getFont()); 
        optionsContainer.add(topicBox, containerGbc);
        

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
            paperBoxes[i].setBackground(new Color(245, 245, 245)); 
            paperPanel.add(paperBoxes[i]);
        }
        optionsContainer.add(paperPanel, containerGbc);
        

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
            difficultyBoxes[i].setBackground(new Color(245, 245, 245));
            difficultyPanel.add(difficultyBoxes[i]);
        }

        optionsContainer.add(difficultyPanel, containerGbc);
        

        containerGbc.gridy = 8;

        CustomLabel timeToSolveLabel = new CustomLabel("Time to Solve (minutes)");
        timeToSolveLabel.setFont(subjectLabel.getFont());

        optionsContainer.add(timeToSolveLabel, containerGbc);
        

        containerGbc.gridy = 9;
        CustomPanel timePanel = new CustomPanel(new GridLayout(0, 2));
        timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        timeBoxes = new CustomCheckBox[5];
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60+"};
        for (int i = 0; i < 5; i++) {
            timeBoxes[i] = new CustomCheckBox(times[i]);
            timeBoxes[i].setBackground(new Color(245, 245, 245));
            timePanel.add(timeBoxes[i]);
        }
        optionsContainer.add(timePanel, containerGbc);
        


        containerGbc.gridy = 10;
        containerGbc.weighty = 1.0;
        optionsContainer.add(Box.createVerticalGlue(), containerGbc);

        return panel;
    }

    //Builds the scrollable panel that has question cards
    private CustomPanel createQuestionsPanel() {
        CustomPanel panel = new CustomPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        questionsContainer = new CustomPanel();
        questionsContainer.setLayout(new BoxLayout(questionsContainer, BoxLayout.Y_AXIS));
        questionsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        questions = QuestionBank.questions;
        for (Question question : questions) {
            questionsContainer.add(createQuestionCard(question));
            questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); 
        }
        
        JScrollPane scrollPane = new JScrollPane(questionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    //Builds the question cards with all there details
    private CustomPanel createQuestionCard(Question question) {
        CustomPanel card = new CustomPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(255, 255, 255)); 
        
        CustomPanel header = new CustomPanel(new BorderLayout());
        header.setBackground(new Color(255, 255, 255)); 
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        CustomLabel titleLabel = new CustomLabel(question.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(titleLabel, BorderLayout.NORTH);
        
        CustomPanel topicsPanel = new CustomPanel(new GridLayout(6, 1));
        topicsPanel.setBackground(new Color(255, 255, 255));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        topicsPanel.add(new CustomLabel("IB Subject: " + question.getSubject()));
        topicsPanel.add(new CustomLabel("Topic: " + question.getTopic()));
        topicsPanel.add(new CustomLabel("IB Paper: " + question.getPaper()));
        topicsPanel.add(new CustomLabel("Difficulty: " + question.getDifficulty()));
        topicsPanel.add(new CustomLabel("Time to Solve: " + question.getTimeToSolve() + " minutes"));
        topicsPanel.add(new CustomLabel("Marks: " + question.getMarks() + " marks"));

        header.add(topicsPanel, BorderLayout.CENTER);
        
        CustomPanel buttonsPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBackground(new Color(255, 255, 255));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        addToPracticeTest = new CustomButton("Add to Practice Test");
        markschemeButton = new CustomButton("Show Markscheme");
        editQuestionButton = new CustomButton("Edit Question");
        deleteQuestionButton = new CustomButton("Delete Question");


        buttonsPanel.add(addToPracticeTest);
        buttonsPanel.add(markschemeButton);
        buttonsPanel.add(editQuestionButton);
        buttonsPanel.add(deleteQuestionButton);

        header.add(buttonsPanel, BorderLayout.SOUTH);
        
        card.add(header, BorderLayout.NORTH);
    
        ImageIcon image = question.getQuestionImage(); 
        JLabel content = new JLabel(image);   
        card.add(content, BorderLayout.CENTER);

        addToPracticeTest.addActionListener((_) -> {
            if (controller != null) {
                if (!PracticeTest.practiceQuestions.contains(question)) {
                    PracticeTest.practiceQuestions.add(question);
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(card), 
                        "Question added to practice test");
                } else {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(card), 
                        "This question is already in your practice test");
                }
                
                if (frame != null && frame.getPracticeTestPage() != null) {
                    frame.getPracticeTestPage().updateQuestionsList();
                }
            }
        });

        markschemeButton.addActionListener((_) -> showMarkschemeDialog(question));
        editQuestionButton.addActionListener((_) -> showEditQuestionDialog(question));
        deleteQuestionButton.addActionListener((_) -> controller.deleteQuestion(question));
        return card;
    }

    //creates the panel that has the buttons for uploading question and markscheme images which will be used in the add/edit questions dialog
    private CustomPanel createUploadButtonsContainerPanel() {
        CustomPanel buttonsPanel = new CustomPanel(new GridLayout(1,2));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
        CustomPanel uploadQuestionPanel = new CustomPanel(new BorderLayout(0, 5)); 
        
        CustomPanel questionButtonAndPreview = new CustomPanel(new BorderLayout(0, 5));
        uploadQuestionButton = new CustomButton("Upload Screenshot of Question");
        uploadQuestionButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, defaultFontSize));
        
        questionPreviewLabel = new CustomLabel("No file selected");
        questionPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPreviewLabel.setFont(new Font(Common.getDefaultFont(), Font.ITALIC, defaultFontSize-2));
        
        questionButtonAndPreview.add(uploadQuestionButton, BorderLayout.CENTER);
        questionButtonAndPreview.add(questionPreviewLabel, BorderLayout.SOUTH);
        
        uploadQuestionPanel.add(questionButtonAndPreview, BorderLayout.CENTER);
        buttonsPanel.add(uploadQuestionPanel);
    
        CustomPanel uploadMarkschemePanel = new CustomPanel(new BorderLayout(0, 5));
        
        CustomPanel markschemeButtonAndPreview = new CustomPanel(new BorderLayout(0, 5));
        uploadMarkschemeButton = new CustomButton("Upload Screenshot of Markscheme");
        uploadMarkschemeButton.setFont(new Font(Common.getDefaultFont(), Font.BOLD, defaultFontSize));
        
        markschemePreviewLabel = new CustomLabel("No file selected");
        markschemePreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        markschemePreviewLabel.setFont(new Font(Common.getDefaultFont(), Font.ITALIC, defaultFontSize-2));
        
        markschemeButtonAndPreview.add(uploadMarkschemeButton, BorderLayout.CENTER);
        markschemeButtonAndPreview.add(markschemePreviewLabel, BorderLayout.SOUTH);
        
        uploadMarkschemePanel.add(markschemeButtonAndPreview, BorderLayout.CENTER);
        buttonsPanel.add(uploadMarkschemePanel);

        return buttonsPanel;
    }

    //builds the add question dialog
    public void showAddQuestionDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Question");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
    
        CustomPanel contentPanel = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        

        CustomPanel uploadPanel = createUploadButtonsContainerPanel();
        contentPanel.add(uploadPanel, gbc);
        

        gbc.gridy++;
        CustomLabel titleLabel = new CustomLabel("Question Title:");
        contentPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JTextField titleField = new JTextField(20);
        titleField.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));
        contentPanel.add(titleField, gbc);


        gbc.gridy++;
        CustomLabel subjectLabel = new CustomLabel("Subject:");
        contentPanel.add(subjectLabel, gbc);
        
        gbc.gridy++;
        JComboBox<String> subjectDropdown = new JComboBox<>();
        CustomButton addSubjectBtn = new CustomButton("Add Subject");
        CustomPanel subjectPanel = new CustomPanel(new BorderLayout(5, 0));
        subjectPanel.add(subjectDropdown, BorderLayout.CENTER);
        subjectPanel.add(addSubjectBtn, BorderLayout.EAST);
        contentPanel.add(subjectPanel, gbc);
        
        addSubjectBtn.addActionListener((_) -> showAddSubjectDialog(subjectDropdown));


        gbc.gridy++;
        CustomLabel topicLabel = new CustomLabel("Topic:");
        contentPanel.add(topicLabel, gbc);
        
        gbc.gridy++;
        JComboBox<String> topicDropdown = new JComboBox<>();
        CustomButton addTopicBtn = new CustomButton("Add Topic"); 
        CustomPanel topicPanel = new CustomPanel(new BorderLayout(5, 0));
        topicPanel.add(topicDropdown, BorderLayout.CENTER);
        topicPanel.add(addTopicBtn, BorderLayout.EAST);
        contentPanel.add(topicPanel, gbc);

        addTopicBtn.addActionListener((_) -> showAddTopicDialog(subjectDropdown, topicDropdown));


        gbc.gridy++;
        CustomLabel marksLabel = new CustomLabel("Marks:");
        contentPanel.add(marksLabel, gbc);

        gbc.gridy++;
        JFormattedTextField marksField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        marksField.setColumns(5);
        marksField.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));
        contentPanel.add(marksField, gbc);


        gbc.gridy++;
        CustomPanel paperPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        CustomLabel paperLabel = new CustomLabel("Paper:");
        paperPanel.add(paperLabel);
        String[] papers = {"1", "2", "3","N/A"};
        ButtonGroup paperGroup = new ButtonGroup();
        for (String paper : papers) {
            JRadioButton paperBtn = new JRadioButton(paper);
            paperGroup.add(paperBtn);
            paperPanel.add(paperBtn);
        }
        contentPanel.add(paperPanel, gbc);
    

        gbc.gridy++;
        CustomPanel difficultyPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        CustomLabel difficultyLabel = new CustomLabel("Difficulty:");
        difficultyPanel.add(difficultyLabel);
        String[] difficulties = {"Easy", "Medium", "Hard"};
        ButtonGroup difficultyGroup = new ButtonGroup();
        for (String difficulty : difficulties) {
            JRadioButton difficultyBtn = new JRadioButton(difficulty);
            difficultyGroup.add(difficultyBtn);
            difficultyPanel.add(difficultyBtn);
        }
        contentPanel.add(difficultyPanel, gbc);
    

        gbc.gridy++;
        CustomPanel timePanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        CustomLabel timeLabel = new CustomLabel("Time to Solve:");
        timePanel.add(timeLabel);
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60+"};
        ButtonGroup timeGroup = new ButtonGroup();
        for (String time : times) {
            JRadioButton timeBtn = new JRadioButton(time);
            timeGroup.add(timeBtn);
            timePanel.add(timeBtn);
        }
        contentPanel.add(timePanel, gbc);
    

        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomButton saveButton = new CustomButton("Save");
        CustomButton cancelButton = new CustomButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
    
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        cancelButton.addActionListener((_) -> dialog.dispose());
        
        controller.initializeAddQuestionDialog(
            dialog,
            titleField, 
            subjectDropdown,
            topicDropdown,
            paperGroup,
            difficultyGroup,
            timeGroup,
            uploadQuestionButton,
            uploadMarkschemeButton,
            saveButton,
            questionPreviewLabel,
            markschemePreviewLabel,
            marksField
        );

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    //shows the edit question dialog
    public void showEditQuestionDialog(Question question) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Question");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
    
        CustomPanel contentPanel = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        CustomPanel uploadPanel = createUploadButtonsContainerPanel();
        contentPanel.add(uploadPanel, gbc);
    
        gbc.gridy++;
        CustomLabel titleLabel = new CustomLabel("Question Title:");
        contentPanel.add(titleLabel, gbc);
    
        gbc.gridy++;
        JTextField titleField = new JTextField(20);
        titleField.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));
        contentPanel.add(titleField, gbc);
    


        gbc.gridy++;
        CustomLabel subjectLabel = new CustomLabel("Subject:");
        contentPanel.add(subjectLabel, gbc);
        
        gbc.gridy++;
        JComboBox<String> subjectDropdown = new JComboBox<>();
        CustomButton addSubjectBtn = new CustomButton("Add Subject");
        CustomPanel subjectPanel = new CustomPanel(new BorderLayout(5, 0));
        subjectPanel.add(subjectDropdown, BorderLayout.CENTER);
        subjectPanel.add(addSubjectBtn, BorderLayout.EAST);
        contentPanel.add(subjectPanel, gbc);
    

        gbc.gridy++;
        CustomLabel topicLabel = new CustomLabel("Topic:");
        contentPanel.add(topicLabel, gbc);
        
        gbc.gridy++;
        JComboBox<String> topicDropdown = new JComboBox<>();
        CustomButton addTopicBtn = new CustomButton("Add Topic");
        CustomPanel topicPanel = new CustomPanel(new BorderLayout(5, 0));
        topicPanel.add(topicDropdown, BorderLayout.CENTER);
        topicPanel.add(addTopicBtn, BorderLayout.EAST);
        contentPanel.add(topicPanel, gbc);
    

        gbc.gridy++;
        CustomLabel marksLabel = new CustomLabel("Marks:");
        contentPanel.add(marksLabel, gbc);
    
        gbc.gridy++;
        JFormattedTextField marksField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        marksField.setColumns(5);
        contentPanel.add(marksField, gbc);
    

        gbc.gridy++;
        CustomPanel paperPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        CustomLabel paperLabel = new CustomLabel("Paper:");
        paperPanel.add(paperLabel);
        ButtonGroup paperGroup = new ButtonGroup();
        String[] papers = {"1", "2", "3", "N/A"};
        for (String paper : papers) {
            JRadioButton paperBtn = new JRadioButton(paper);
            paperGroup.add(paperBtn);
            paperPanel.add(paperBtn);
        }
        contentPanel.add(paperPanel, gbc);
    

        gbc.gridy++;
        CustomPanel difficultyPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        CustomLabel difficultyLabel = new CustomLabel("Difficulty:");
        difficultyPanel.add(difficultyLabel);
        ButtonGroup difficultyGroup = new ButtonGroup();
        String[] difficulties = {"Easy", "Medium", "Hard"};
        for (String difficulty : difficulties) {
            JRadioButton difficultyBtn = new JRadioButton(difficulty);
            difficultyGroup.add(difficultyBtn);
            difficultyPanel.add(difficultyBtn);
        }
        contentPanel.add(difficultyPanel, gbc);
    

        gbc.gridy++;
        CustomPanel timePanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT));
        CustomLabel timeLabel = new CustomLabel("Time to Solve:");
        timePanel.add(timeLabel);
        ButtonGroup timeGroup = new ButtonGroup();
        String[] times = {"0-1", "1-5", "5-10", "10-35", "35-60+"};
        for (String time : times) {
            JRadioButton timeBtn = new JRadioButton(time);
            timeGroup.add(timeBtn);
            timePanel.add(timeBtn);
        }
        contentPanel.add(timePanel, gbc);
    

        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomButton saveButton = new CustomButton("Save");
        CustomButton cancelButton = new CustomButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
    
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        addSubjectBtn.addActionListener((_) -> showAddSubjectDialog(subjectDropdown));
        addTopicBtn.addActionListener((_) -> showAddTopicDialog(subjectDropdown, topicDropdown));
    
        cancelButton.addActionListener((_) -> dialog.dispose());
    
        controller.initializeEditQuestionDialog(
            dialog,
            titleField,
            subjectDropdown,
            topicDropdown,
            paperGroup,
            difficultyGroup,
            timeGroup,
            uploadQuestionButton,
            uploadMarkschemeButton,
            saveButton,
            questionPreviewLabel,
            markschemePreviewLabel,
            marksField,
            question
        );
    
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    //shows the add subject dialog
    public void showAddSubjectDialog(JComboBox<String> subjectDropdown) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Subject");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
    
        CustomPanel contentPanel = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        CustomLabel subjectLabel = new CustomLabel("Subject Name:");
        contentPanel.add(subjectLabel, gbc);
    
        gbc.gridy++;
        JTextField subjectField = new JTextField(20); 
        subjectField.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));
        contentPanel.add(subjectField, gbc);
    
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomButton addButton = new CustomButton("Add");
        CustomButton cancelButton = new CustomButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
    
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        cancelButton.addActionListener((_) -> dialog.dispose());
        addButton.addActionListener((_) -> {
            String newSubject = subjectField.getText().trim();
            if (!newSubject.isEmpty()) {
                controller.addSubject(newSubject, subjectDropdown);
                controller.updateSubjectComboBox(subjectBox);
                controller.updateSubjectComboBox(subjectDropdown);
                subjectDropdown.setSelectedItem(newSubject); 
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Subject name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    //shows the add topic dialog
    public void showAddTopicDialog(JComboBox<String> subjectDropdown, JComboBox<String> topicDropdown) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Topic");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
    
        CustomPanel contentPanel = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        CustomLabel topicLabel = new CustomLabel("Topic Name:");
        contentPanel.add(topicLabel, gbc);
    
        gbc.gridy++;
        JTextField topicField = new JTextField(20); 
        topicField.setFont(new Font(Common.getDefaultFont(), Font.PLAIN, defaultFontSize));
        contentPanel.add(topicField, gbc);
    
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomButton addButton = new CustomButton("Add");
        CustomButton cancelButton = new CustomButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
    
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        cancelButton.addActionListener((_) -> dialog.dispose());
        addButton.addActionListener((_) -> {
            String newTopic = topicField.getText().trim();
            String selectedSubject = (String) subjectDropdown.getSelectedItem();
            if (!newTopic.isEmpty() && selectedSubject != null && !selectedSubject.equals("N/A")) {
                controller.addTopic(selectedSubject, newTopic, topicDropdown);
                controller.updateTopicComboBox(selectedSubject, topicBox); 
                controller.updateTopicComboBox(selectedSubject, topicDropdown);
                topicDropdown.setSelectedItem(newTopic); 
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Topic name cannot be empty and a valid subject must be selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    //initializes popup menus for right clicking on subjects and topics
    private void initializePopupMenus() {
        subjectPopupMenu = new JPopupMenu();
        editSubject = new JMenuItem("Edit Subject");
        removeSubject = new JMenuItem("Remove Subject");
        subjectPopupMenu.add(editSubject);
        subjectPopupMenu.add(removeSubject);

        topicPopupMenu = new JPopupMenu();
        JMenuItem editTopic = new JMenuItem("Edit Topic");
        JMenuItem removeTopic = new JMenuItem("Remove Topic");
        topicPopupMenu.add(editTopic);
        topicPopupMenu.add(removeTopic);

        subjectBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showSubjectPopup(e);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showSubjectPopup(e);
                }
            }
        });

        topicBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showTopicPopup(e);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showTopicPopup(e);
                }
            }
        });

        editSubject.addActionListener((_) -> {
            String selectedSubject = (String) subjectBox.getSelectedItem();
            if (selectedSubject != null && !selectedSubject.equals("N/A")) {
                showEditSubjectDialog(selectedSubject);
            }
        });

        removeSubject.addActionListener((_) -> {
            String selectedSubject = (String) subjectBox.getSelectedItem();
            if (selectedSubject != null && !selectedSubject.equals("N/A")) {
                showRemoveSubjectDialog(selectedSubject);
            }
        });

        editTopic.addActionListener((_) -> {
            String selectedSubject = (String) subjectBox.getSelectedItem();
            String selectedTopic = (String) topicBox.getSelectedItem();
            if (selectedSubject != null && !selectedSubject.equals("N/A") &&
                selectedTopic != null && !selectedTopic.equals("N/A")) {
                showEditTopicDialog(selectedSubject, selectedTopic);
            }
        });

        removeTopic.addActionListener((_) -> {
            String selectedSubject = (String) subjectBox.getSelectedItem();
            String selectedTopic = (String) topicBox.getSelectedItem();
            if (selectedSubject != null && !selectedSubject.equals("N/A") &&
                selectedTopic != null && !selectedTopic.equals("N/A")) {
                showRemoveTopicDialog(selectedSubject, selectedTopic);
            }
        });
    }

    //shows the subject popup menu
    private void showSubjectPopup(MouseEvent e) {
        String selectedSubject = (String) subjectBox.getSelectedItem();
        if (selectedSubject != null && !selectedSubject.equals("N/A")) {
            subjectPopupMenu.show(subjectBox, e.getX(), e.getY());
        }
    }

    //shows the topic popup menu
    private void showTopicPopup(MouseEvent e) {
        String selectedSubject = (String) subjectBox.getSelectedItem();
        String selectedTopic = (String) topicBox.getSelectedItem();
        if (selectedSubject != null && !selectedSubject.equals("N/A") &&
            selectedTopic != null && !selectedTopic.equals("N/A")) {
            topicPopupMenu.show(topicBox, e.getX(), e.getY());
        }
    }

    //shows the edit subject dialog
    public void showEditSubjectDialog(String oldSubject) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Subject");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
    
        CustomPanel panel = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        panel.add(new CustomLabel("New Subject Name:"), gbc);
        
        gbc.gridy++;
        JTextField newSubjectField = new JTextField(oldSubject);
        panel.add(newSubjectField, gbc);
    
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomButton saveButton = new CustomButton("Save");
        CustomButton cancelButton = new CustomButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
    
        saveButton.addActionListener((_) -> {
            String newSubject = newSubjectField.getText().trim();
            if (!newSubject.isEmpty()) {
                controller.editSubject(oldSubject, newSubject);
                dialog.dispose();
            }
        });
    
        cancelButton.addActionListener((_) -> dialog.dispose());
    
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    //shows the edit topic dialog
    public void showEditTopicDialog(String subject, String oldTopic) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Topic");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
    
        CustomPanel panel = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        panel.add(new CustomLabel("New Topic Name:"), gbc);
        
        gbc.gridy++;
        JTextField newTopicField = new JTextField(oldTopic);
        panel.add(newTopicField, gbc);
    
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomButton saveButton = new CustomButton("Save");
        CustomButton cancelButton = new CustomButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
    
        saveButton.addActionListener((_) -> {
            String newTopic = newTopicField.getText().trim();
            if (!newTopic.isEmpty()) {
                controller.editTopic(subject, oldTopic, newTopic);
                dialog.dispose();
            }
        });
    
        cancelButton.addActionListener((_) -> dialog.dispose());
    
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    //shows the remove subject dialog
    public void showRemoveSubjectDialog(String subject) {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to remove the subject '" + subject + "'?\n" +
            "This will also remove all questions associated with this subject.",
            "Remove Subject",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
    
        if (result == JOptionPane.YES_OPTION) {
            controller.removeSubject(subject);
        }
    }
    
    //shows the remove topic dialog
    public void showRemoveTopicDialog(String subject, String topic) {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to remove the topic '" + topic + "'?\n" +
            "This will also remove all questions associated with this topic.",
            "Remove Topic",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
    
        if (result == JOptionPane.YES_OPTION) {
            controller.removeTopic(subject, topic);
        }
    }
    
    //shows the markscheme image dialog
    public void showMarkschemeDialog(Question question) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Markscheme");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(0, 5)); 
    
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.8);
        int maxHeight = (int) (screenSize.getHeight() * 0.8);
    
        JPanel imagePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                ImageIcon markschemeIcon = question.getMarkschemeImage();
                if (markschemeIcon != null) {
                    double imageRatio = (double) markschemeIcon.getIconHeight() / markschemeIcon.getIconWidth();
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
    
        ImageIcon originalIcon = question.getMarkschemeImage();
        if (originalIcon != null) {
            Dimension panelSize = imagePanel.getPreferredSize();
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                panelSize.width, panelSize.height, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        }
    
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        CustomButton closeButton = new CustomButton("Close");
        closeButton.addActionListener((_) -> dialog.dispose());
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    //getters for all the components
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

    public CustomButton getAddQuestionsButton() {
        return addQuestionsButton;
    }

    public CustomButton getAddToPracticeTest() {
        return addToPracticeTest;
    }

    public CustomButton getMarkschemeButton() {
        return markschemeButton;
    }

    public CustomButton getEditQuestionButton() {
        return editQuestionButton;
    }

    public CustomButton getDeleteQuestionButton() {
        return deleteQuestionButton;
    }
   
    public CustomButton getUploadQuestionButton() {
        return uploadQuestionButton;
    }
    
    public CustomButton getUploadMarkschemeButton() {
        return uploadMarkschemeButton;
    }
}