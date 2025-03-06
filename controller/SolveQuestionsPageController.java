package controller;

import model.core.QuestionBank;
import model.entity.Question;
import view.page.SolveQuestionsPage;
import view.component.CustomButton;
import view.page.Frame;  // Add this import

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.Timer;
import java.text.NumberFormat;

public class SolveQuestionsPageController {
    private SolveQuestionsPage solveQuestionsPage;
    private Timer resizeTimer;
    private JPanel questionsPanel;
    private JPanel questionContainer;
    private Frame frame; // Add Frame instance variable

    public SolveQuestionsPageController(Frame frame) {
        this.frame = frame;
        this.solveQuestionsPage = frame.getSolveQuestionsPage();
        this.questionsPanel = solveQuestionsPage.getQuestionsPanel();
        this.questionContainer = solveQuestionsPage.getQuestionContainer();
        
        // Initialize components
        updateSubjectComboBox(solveQuestionsPage.getSubjectBox());
        updateTopicComboBox((String) solveQuestionsPage.getSubjectBox().getSelectedItem(), solveQuestionsPage.getTopicBox());

        addActionListeners();

        // Scale images initially
        SwingUtilities.invokeLater(() -> {
            scaleImages(questionsPanel.getWidth());
            System.out.println("Initial scaling complete");
        });
    }

    private void addActionListeners() {

        solveQuestionsPage.getHomeButton().addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "Home");
        });

        solveQuestionsPage.getSubjectBox().addActionListener(e -> {
            String selectedSubject = (String) solveQuestionsPage.getSubjectBox().getSelectedItem();
            updateTopicComboBox(selectedSubject, solveQuestionsPage.getTopicBox()); // Pass the selected subject and topic combo box
            System.out.println("Topics updated");
        });
        

        solveQuestionsPage.getTopicBox().addActionListener(e -> {
            filterQuestions();
        });

        for (JCheckBox paperBox : solveQuestionsPage.getPaperBoxes()) {
            paperBox.addActionListener(e -> {
                //handleCheckboxSelection(solveQuestionsPage.getPaperBoxes(), (JCheckBox) e.getSource());
                filterQuestions();
            });
        }

        for (JCheckBox difficultyBox : solveQuestionsPage.getDifficultyBoxes()) {
            difficultyBox.addActionListener(e -> {
                //handleCheckboxSelection(solveQuestionsPage.getDifficultyBoxes(), (JCheckBox) e.getSource());
                filterQuestions();  
            });
        }

        for (JCheckBox timeBox : solveQuestionsPage.getTimeBoxes()) {
            timeBox.addActionListener(e -> {
                //handleCheckboxSelection(solveQuestionsPage.getTimeBoxes(), (JCheckBox) e.getSource());
                filterQuestions();
            });
        }

        
        questionsPanel.addComponentListener(new ComponentAdapter() {
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
                            System.out.println("Window resized to: " + questionsPanel.getSize());
                            scaleImages(questionsPanel.getWidth());
                        });
                    }
                }, 1); // Delay in milliseconds
            }
        });
        
        solveQuestionsPage.getAddQuestionsButton().addActionListener(e -> 
        solveQuestionsPage.createAddQuestionDialog()
        );
        }


    //This method filters the questions in the QuestionBank based on the selected subjects, topics, papers, difficulties, and times.
    //It then updates the questions container to only include the filtered questions.
    private void filterQuestions() {
        Set<String> selectedSubjects = new HashSet<>();
        Set<String> selectedTopics = new HashSet<>();
        Set<String> selectedPapers = new HashSet<>();
        Set<String> selectedDifficulties = new HashSet<>();
        Set<String> selectedTimes = new HashSet<>();
        
        //These next if statements simple check what selections are made to the user and add them to the respective sets
        //if they are not null or "N/A". For the dropdown boxes, it is first checked if the selected item is not null, and then
        //it is checked if the selected item is not "N/A". If it is not "N/A", then the selected item is added to the respective set.
        //This set will later be used to filter the questions in the QuestionBank by comparing each question to the properties in the set.

        // Collect selected subjects
        if (solveQuestionsPage.getSubjectBox().getSelectedItem() != null) {
            String selectedSubject = (String) solveQuestionsPage.getSubjectBox().getSelectedItem();
            if (!selectedSubject.equals("N/A")) {
                selectedSubjects.add(selectedSubject);
            }
        }
    
        // Collect selected topics
        if (solveQuestionsPage.getTopicBox().getSelectedItem() != null) {
            String selectedTopic = (String) solveQuestionsPage.getTopicBox().getSelectedItem();
            if (!selectedTopic.equals("N/A")) {
                selectedTopics.add(selectedTopic);
            }
        }
    
        // Collect selected papers
        for (JCheckBox paperBox : solveQuestionsPage.getPaperBoxes()) {
            if (paperBox.isSelected()) {
                selectedPapers.add(paperBox.getText());
            }
        }
    
        // Collect selected difficulties
        for (JCheckBox difficultyBox : solveQuestionsPage.getDifficultyBoxes()) {
            if (difficultyBox.isSelected()) {
                selectedDifficulties.add(difficultyBox.getText());
            }
        }
    
        // Collect selected times
        for (JCheckBox timeBox : solveQuestionsPage.getTimeBoxes()) {
            if (timeBox.isSelected()) {
                selectedTimes.add(timeBox.getText());
            }
        }
        
        //This next line of code creates a new list called filteredQuestions which includes any and all questions
        //that match the selected subjects, topics, papers, difficulties, and times. It works by iterating through
        //every question in the QuestionBank and checking if it matches the selected subjects, topics, papers, difficulties.
        //and times. If it does not does not match any of the selected subjects, topics, papers, difficulties, and times,
        //matches is false (indicating the question does not match the selected criteria) and the question is not added to the
        //filteredQuestions list. After iterating through all the questions, the updateQuestions method is called to update
        //the questions container to only include the list of filtered questions.

        ArrayList<Question> filteredQuestions = new ArrayList<>();
        for (Question question : QuestionBank.questions) {
            boolean matches = true;
    
            // Check if the question matches the selected subjects
            if (!selectedSubjects.isEmpty() && !selectedSubjects.contains(question.getSubject())) {
                matches = false;
            }
    
            // Check if the question matches the selected topics
            if (!selectedTopics.isEmpty() && !selectedTopics.contains(question.getTopic())) {
                matches = false;
            }
    
            // Check if the question matches the selected papers
            if (!selectedPapers.isEmpty() && !selectedPapers.contains(question.getPaper())) {
                matches = false;
            }
    
            // Check if the question matches the selected difficulties
            if (!selectedDifficulties.isEmpty() && !selectedDifficulties.contains(question.getDifficulty())) {
                matches = false;
            }
    
            // Check if the question matches the selected times
            if (!selectedTimes.isEmpty() && !selectedTimes.contains(question.getTimeToSolve())) {
                matches = false;
            }
    
            if (matches) {
                filteredQuestions.add(question);
            }
        }
    
        updateQuestions(filteredQuestions, solveQuestionsPage.getQuestionContainer());
    }

    public void updateSubjectComboBox(JComboBox<String> subjectDropdown) {
        subjectDropdown.removeAllItems();
        subjectDropdown.addItem("N/A");
        for (String subject : Question.globalSubjectTopicManager.getSubjects()) {
            subjectDropdown.addItem(subject);
        }
    }
    
    public void updateTopicComboBox(String subject, JComboBox<String> topicDropdown) {
        topicDropdown.removeAllItems();
        topicDropdown.addItem("N/A");
        for (String topic : Question.globalSubjectTopicManager.getTopics(subject)) {
            topicDropdown.addItem(topic);
        }
    }
    
    /* 
    public void addSubject(String subject) {
        Question.globalSubjectTopicManager.addSubject(subject);
        updateSubjectComboBox();
    }

    public void removeSubject(String subject) {
        Question.globalSubjectTopicManager.removeSubject(subject);
        updateSubjectComboBox();
    }

    public void addTopic(String subject, String topic) {
        Question.globalSubjectTopicManager.addTopic(subject, topic);
        updateTopicComboBox();
    }

    public void removeTopic(String subject, String topic) {
        Question.globalSubjectTopicManager.removeTopic(subject, topic);
        updateTopicComboBox();
    }
    */
    
    
    


    public void setSubjects(Map<String, String> subjects) {
        JComboBox<String> subjectBox = solveQuestionsPage.getSubjectBox();
        subjectBox.removeAllItems();
        subjectBox.addItem("N/A"); // Add "N/A" as the default item
        for (String subject : subjects.keySet()) {
            subjectBox.addItem(subject);
        }
    }

    public void setTopics(Set<String> topics) {
        JComboBox<String> topicBox = solveQuestionsPage.getTopicBox();
        topicBox.removeAllItems();
        topicBox.addItem("N/A"); // Add "N/A" as the default item
        for (String topic : topics) {
            topicBox.addItem(topic);
        }
    }

    public void updateQuestions(ArrayList<Question> filteredQuestions, JPanel questionsContainer) {
        // Clear and rebuild the container
        questionsContainer.removeAll();
        
        // Create and add cards
        for (Question question : filteredQuestions) {
            JPanel card = solveQuestionsPage.getQuestionCard(question);
            questionsContainer.add(card);
            questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); 
        }
        
        // Scale all images after cards are created
        scaleImages(questionsContainer.getWidth());
        System.out.println("scaled from changed sorting options");
        
        // Refresh the container
        questionsContainer.revalidate();
        questionsContainer.repaint();
    }

    // This method scales the images in all question cards to match the panel width while maintaining aspect ratio
    public void scaleImages(int panelWidth) {
        // Keep track of actual question index
        int questionIndex = 0;
        for (int i = 0; i < questionContainer.getComponentCount(); i++) {
            Component component = questionContainer.getComponent(i);
            if (component instanceof JPanel) {
                JPanel card = (JPanel) component;
                for (Component cardComponent : card.getComponents()) {
                    if (cardComponent instanceof JLabel) {
                        JLabel content = (JLabel) cardComponent;
                        // Get the original image from the current question
                        ImageIcon originalImage = QuestionBank.questions.get(questionIndex).getQuestionImage();
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







    public void initializeAddQuestionDialog(JDialog dialog, JTextField titleField, 
    JComboBox<String> subjectDropdown, JComboBox<String> topicDropdown,
    ButtonGroup paperGroup, ButtonGroup difficultyGroup, ButtonGroup timeGroup,
    CustomButton uploadQuestionButton, CustomButton uploadMarkschemeButton,
    CustomButton saveButton, JLabel questionPreviewLabel,    // Add these parameters
    JLabel markschemePreviewLabel, JFormattedTextField marksField) {

        // Image holders
        final ImageIcon[] questionImage = {null};
        final ImageIcon[] markschemeImage = {null};

        // Initialize subject dropdown
        subjectDropdown.addItem("N/A");
        for (String subject : Question.globalSubjectTopicManager.getSubjects()) {
            subjectDropdown.addItem(subject);
        }
        subjectDropdown.setSelectedItem("N/A"); // Set the selected item to "N/A"

        // Update topics when subject changes
        subjectDropdown.addActionListener(e -> {
            String selectedSubject = (String) subjectDropdown.getSelectedItem();
            topicDropdown.removeAllItems();
            topicDropdown.addItem("N/A");
            if (selectedSubject != null && !selectedSubject.equals("N/A")) {
                for (String topic : Question.globalSubjectTopicManager.getTopics(selectedSubject)) {
                    topicDropdown.addItem(topic);
                }
            }
            topicDropdown.setSelectedItem("N/A"); // Set the selected item to "N/A"
        });

        // Add upload functionality
        uploadQuestionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                questionImage[0] = new ImageIcon(fileChooser.getSelectedFile().getPath());
                questionPreviewLabel.setText(fileChooser.getSelectedFile().getName()); // Update label
            }
        });

        uploadMarkschemeButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                markschemeImage[0] = new ImageIcon(fileChooser.getSelectedFile().getPath());
                markschemePreviewLabel.setText(fileChooser.getSelectedFile().getName()); // Update label
            }
        });

        // Add save functionality
        saveButton.addActionListener(e -> {
            if (validateInputs(titleField, subjectDropdown, topicDropdown, 
                paperGroup, difficultyGroup, timeGroup, questionImage[0], markschemeImage[0], marksField)) {

                int marks = ((Number) marksField.getValue()).intValue(); // Retrieve marks as integer
                saveNewQuestion(

                    titleField.getText(),
                    (String)subjectDropdown.getSelectedItem(),
                    (String)topicDropdown.getSelectedItem(),
                    getDifficultyFromSelection(difficultyGroup),
                    getTimeFromSelection(timeGroup),
                    getPaperFromSelection(paperGroup),
                    questionImage[0],
                    markschemeImage[0],
                    marks
                );
                
                dialog.dispose();
                filterQuestions(); // Refresh question list
            }
        });
    }

    private Question.Difficulty getDifficultyFromSelection(ButtonGroup group) {
        String selected = getSelectedButtonText(group);
        return Question.Difficulty.valueOf(selected.toUpperCase());
    }

    private Question.TimeToSolve getTimeFromSelection(ButtonGroup group) {
        String selected = getSelectedButtonText(group);
        switch(selected) {
            case "0-1":
                return Question.TimeToSolve.ZERO_TO_ONE;
            case "1-5":
                return Question.TimeToSolve.ONE_TO_FIVE;
            case "5-10":
                return Question.TimeToSolve.FIVE_TO_TEN;
            case "10-35":
                return Question.TimeToSolve.TEN_TO_THIRTYFIVE;
            case "35-60+":
                return Question.TimeToSolve.THIRTYFIVE_TO_SIXTY;
            default:
                throw new IllegalArgumentException("Invalid time range: " + selected);
        }
    }

    private Question.Paper getPaperFromSelection(ButtonGroup group) {
        String selected = getSelectedButtonText(group);
        if (selected.equals("N/A")) {
            return Question.Paper.NA;
        }
        switch (selected) {
            case "1":
                return Question.Paper.ONE;
            case "2":
                return Question.Paper.TWO;
            case "3":
                return Question.Paper.THREE;
            default:
                throw new IllegalArgumentException("Invalid paper: " + selected);
        }
    }

    private String getSelectedButtonText(ButtonGroup group) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    //This handles the inputs for the new question dialog. It checks if all fields are filled in and if both images are uploaded.
    //If all fields are filled in and both images are uploaded, the new question is saved to the QuestionBank and the dialog is closed.
    private boolean validateInputs(JTextField titleField, JComboBox<String> subjectBox,
        JComboBox<String> topicBox, ButtonGroup paperGroup, ButtonGroup difficultyGroup,
        ButtonGroup timeGroup, ImageIcon questionImage, ImageIcon markschemeImage, JFormattedTextField marksField) {
        
        if (titleField.getText().isEmpty() ||
            subjectBox.getSelectedItem() == null ||
            subjectBox.getSelectedItem().equals("N/A") ||
            topicBox.getSelectedItem() == null ||
            topicBox.getSelectedItem().equals("N/A") ||
            paperGroup.getSelection() == null ||
            difficultyGroup.getSelection() == null ||
            timeGroup.getSelection() == null ||
            questionImage == null ||
            markschemeImage == null ||
            marksField.getValue() == null
            ) {
            
            JOptionPane.showMessageDialog(null,
                "Please fill in all fields and upload both images",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveNewQuestion(String title, String subject, String topic,
        Question.Difficulty difficulty, Question.TimeToSolve timeToSolve,
        Question.Paper paper, ImageIcon questionImage, ImageIcon markschemeImage, int marks) {
        
        Question newQuestion = new Question(
            subject,
            topic,
            difficulty,
            timeToSolve,
            paper,
            questionImage,
            markschemeImage,
            marks, // Default marks
            title
        );

        QuestionBank.questions.add(newQuestion);
    }

    public void addSubject(String subject, JComboBox<String> subjectDropdown) {
        Question.globalSubjectTopicManager.addSubject(subject);
        updateSubjectComboBox(subjectDropdown); // Update the main subject combo box
    }
    /* 
    public void updateASubjectComboBox(JComboBox<String> subjectDropdown) {
        subjectDropdown.removeAllItems();
        subjectDropdown.addItem("N/A");
        for (String subject : Question.globalSubjectTopicManager.getSubjects()) {
            subjectDropdown.addItem(subject);
        }
    }
    */

    public void addTopic(String subject, String topic, JComboBox<String> topicDropdown) {
        Question.globalSubjectTopicManager.addTopic(subject, topic);
        updateTopicComboBox(topic, topicDropdown); // Update the main topic combo box
    }

    /*
    public void updateATopicComboBox(String subject, JComboBox<String> topicDropdown) {
        topicDropdown.removeAllItems();
        topicDropdown.addItem("N/A");
        for (String topic : Question.globalSubjectTopicManager.getTopics(subject)) {
            topicDropdown.addItem(topic);
        }
    }
    */

}
