package controller;

import model.core.PracticeTest;
import model.core.QuestionBank;
import model.entity.Question;
import view.page.SolveQuestionsPage;
import view.component.Common;
import view.component.CustomButton;
import view.component.CustomLabel;
import view.page.Frame;  // Add this import

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.Timer;
import model.service.FileManager;

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
        solveQuestionsPage.setFrame(frame);
        
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
            updateTopicComboBox(selectedSubject, solveQuestionsPage.getTopicBox());
            
            // Update tooltip based on selection
            if (selectedSubject != null && !selectedSubject.equals("N/A")) {
                solveQuestionsPage.getSubjectBox().setToolTipText("Right-click to edit or remove the subject '" + selectedSubject + "'");
            } else {
                solveQuestionsPage.getSubjectBox().setToolTipText(null);
            }
            
            System.out.println("Topics updated");
        });
    
        solveQuestionsPage.getTopicBox().addActionListener(e -> {
            String selectedTopic = (String) solveQuestionsPage.getTopicBox().getSelectedItem();
            
            // Update tooltip based on selection
            if (selectedTopic != null && !selectedTopic.equals("N/A")) {
                solveQuestionsPage.getTopicBox().setToolTipText("Right-click to edit or remove the topic '" + selectedTopic + "'");
            } else {
                solveQuestionsPage.getTopicBox().setToolTipText(null);
            }
            
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
            if (!selectedPapers.isEmpty() && !selectedPapers.contains(question.getPaper()) && !question.getPaper().equals("N/A"))  {
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
        
        // Check if there are any questions to display
        if (filteredQuestions.isEmpty()) {
            // Display a message when no questions match the filters
            CustomLabel emptyLabel = new CustomLabel("No questions match the current filters");
            emptyLabel.setFont(new Font(Common.getDefaultFont(), Font.ITALIC, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setForeground(Color.GRAY);
            questionsContainer.add(emptyLabel);
        } else {
            // Create and add cards
            for (Question question : filteredQuestions) {
                JPanel card = solveQuestionsPage.getQuestionCard(question);
                questionsContainer.add(card);
                questionsContainer.add(Box.createRigidArea(new Dimension(0, 10))); 
            }
            
            // Scale all images after cards are created
            scaleImages(questionsContainer.getWidth());
            System.out.println("scaled from changed sorting options");
        }
        
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

        topicDropdown.addItem("N/A");

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

    public void initializeEditQuestionDialog(JDialog dialog, JTextField titleField, 
        JComboBox<String> subjectDropdown, JComboBox<String> topicDropdown,
        ButtonGroup paperGroup, ButtonGroup difficultyGroup, ButtonGroup timeGroup,
        CustomButton uploadQuestionButton, CustomButton uploadMarkschemeButton,
        CustomButton saveButton, JLabel questionPreviewLabel,
        JLabel markschemePreviewLabel, JFormattedTextField marksField,
        Question question) { // Add Question parameter

        // Image holders - initialize with existing images
        final ImageIcon[] questionImage = {question.getQuestionImage()};
        final ImageIcon[] markschemeImage = {question.getMarkschemeImage()};

        // Set existing values
        titleField.setText(question.getTitle());
        marksField.setValue(question.getMarks());

        // Initialize and set subject dropdown
        updateSubjectComboBox(subjectDropdown);
        subjectDropdown.setSelectedItem(question.getSubject());

        // Initialize and set topic dropdown
        updateTopicComboBox(question.getSubject(), topicDropdown);
        topicDropdown.setSelectedItem(question.getTopic());

        // Set paper selection
        for (Enumeration<AbstractButton> buttons = paperGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.getText().equals(question.getPaper())) {
                button.setSelected(true);
                break;
            }
        }

        // Set difficulty selection
        for (Enumeration<AbstractButton> buttons = difficultyGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.getText().equals(question.getDifficulty())) {
                button.setSelected(true);
                break;
            }
        }

        // Set time selection
        for (Enumeration<AbstractButton> buttons = timeGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.getText().equals(question.getTimeToSolve())) {
                button.setSelected(true);
                break;
            }
        }

        // Set preview labels
        questionPreviewLabel.setText("Current question image");
        markschemePreviewLabel.setText("Current markscheme image");

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
        });

        // Add upload functionality
        uploadQuestionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                questionImage[0] = new ImageIcon(fileChooser.getSelectedFile().getPath());
                questionPreviewLabel.setText(fileChooser.getSelectedFile().getName());
            }
        });

        uploadMarkschemeButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                markschemeImage[0] = new ImageIcon(fileChooser.getSelectedFile().getPath());
                markschemePreviewLabel.setText(fileChooser.getSelectedFile().getName());
            }
        });

        // Add save functionality
        saveButton.addActionListener(e -> {
            if (validateInputs(titleField, subjectDropdown, topicDropdown, 
                paperGroup, difficultyGroup, timeGroup, questionImage[0], markschemeImage[0], marksField)) {
                
                updateQuestion(
                    question,
                    titleField.getText(),
                    (String)subjectDropdown.getSelectedItem(),
                    (String)topicDropdown.getSelectedItem(),
                    getDifficultyFromSelection(difficultyGroup),
                    getTimeFromSelection(timeGroup),
                    getPaperFromSelection(paperGroup),
                    questionImage[0],
                    markschemeImage[0],
                    ((Number)marksField.getValue()).intValue()
                );
                
                dialog.dispose();
                filterQuestions(); // Refresh question list
            }
        });
    }

        
    public void deleteQuestion(Question question) {
        int result = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to delete this question?",
            "Delete Question",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
    
        if (result == JOptionPane.YES_OPTION) {
            // Remove from QuestionBank
            QuestionBank.questions.remove(question);
            
            // Also remove from PracticeTest if it's there
            if (PracticeTest.practiceQuestions.contains(question)) {
                PracticeTest.practiceQuestions.remove(question);
                
                // Update practice test page if it's open
                if (frame != null && frame.getPracticeTestPage() != null) {
                    frame.getPracticeTestPage().updateQuestionsList();
                }
            }
            
            // Refresh the questions display
            FileManager.saveQuestions();
            FileManager.savePracticeTest();
            filterQuestions();
        }
    }

    private void updateQuestion(Question question, String title, String subject, String topic,
        Question.Difficulty difficulty, Question.TimeToSolve timeToSolve,
        Question.Paper paper, ImageIcon questionImage, ImageIcon markschemeImage, int marks) {
        
        question.setTitle(title);
        question.setSubject(subject);
        question.setTopic(topic);
        question.setDifficulty(difficulty);
        question.setTimeToSolve(timeToSolve);
        question.setPaper(paper);
        question.setQuestionImage(questionImage);
        question.setMarkschemeImage(markschemeImage);
        question.setMarks(marks);
            
        PracticeTest.updateQuestionReferences();
    
        // Update practice test page if it's open
        if (frame != null && frame.getPracticeTestPage() != null) {
            frame.getPracticeTestPage().updateQuestionsList();
        }

        FileManager.saveQuestions();
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
        FileManager.saveQuestions();
    }

    public void addSubject(String subject, JComboBox<String> subjectDropdown) {
        Question.globalSubjectTopicManager.addSubject(subject);
        updateSubjectComboBox(subjectDropdown); // Update the main subject combo box
        FileManager.saveSubjectsAndTopics();
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
        FileManager.saveSubjectsAndTopics();
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

        // In SolveQuestionsPageController.java
    public void editSubject(String oldSubject, String newSubject) {
        String currentTopic = (String) solveQuestionsPage.getTopicBox().getSelectedItem();

        // Update all questions with the old subject
        for (Question question : QuestionBank.questions) {
            if (question.getSubject().equals(oldSubject)) {
                question.setSubject(newSubject);
            }
        }
        
        // Update the subject in the manager
        Set<String> topics = Question.globalSubjectTopicManager.getTopics(oldSubject);
        Question.globalSubjectTopicManager.removeSubject(oldSubject);
        Question.globalSubjectTopicManager.addSubject(newSubject);
        for (String topic : topics) {
            Question.globalSubjectTopicManager.addTopic(newSubject, topic);
        }
        
        // Update UI
        updateSubjectComboBox(solveQuestionsPage.getSubjectBox());
        solveQuestionsPage.getSubjectBox().setSelectedItem(newSubject); 

        updateTopicComboBox(newSubject, solveQuestionsPage.getTopicBox());
        if (currentTopic != null && !currentTopic.equals("N/A")) {
            solveQuestionsPage.getTopicBox().setSelectedItem(currentTopic);
        }

        filterQuestions();
        FileManager.saveAllData();
    }

    public void removeSubject(String subject) {
        // Remove all questions with this subject
        QuestionBank.questions.removeIf(q -> q.getSubject().equals(subject));
        
        // Remove subject from manager
        Question.globalSubjectTopicManager.removeSubject(subject);
        
        // Update UI
        updateSubjectComboBox(solveQuestionsPage.getSubjectBox());
        filterQuestions();
        FileManager.saveAllData();
    }

    public void editTopic(String subject, String oldTopic, String newTopic) {

        // Update all questions with the old topic
        for (Question question : QuestionBank.questions) {
            if (question.getSubject().equals(subject) && question.getTopic().equals(oldTopic)) {
                question.setTopic(newTopic);
            }
        }
        
        // Update the topic in the manager
        Question.globalSubjectTopicManager.removeTopic(subject, oldTopic);
        Question.globalSubjectTopicManager.addTopic(subject, newTopic);
        
        // Update UI
        updateTopicComboBox(subject, solveQuestionsPage.getTopicBox());
        solveQuestionsPage.getTopicBox().setSelectedItem(newTopic);
        filterQuestions();
        FileManager.saveAllData();
    }

    public void removeTopic(String subject, String topic) {
        // Remove all questions with this topic
        QuestionBank.questions.removeIf(q -> 
            q.getSubject().equals(subject) && q.getTopic().equals(topic));
        
        // Remove topic from manager
        Question.globalSubjectTopicManager.removeTopic(subject, topic);
        
        // Update UI
        updateTopicComboBox(subject, solveQuestionsPage.getTopicBox());
        filterQuestions();
        FileManager.saveAllData();
    }
    



}
