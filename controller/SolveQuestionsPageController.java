package controller;

import model.core.QuestionBank;
import model.entity.Question;
import view.page.SolveQuestionsPage;
import view.page.Frame;  // Add this import

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.Timer;


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
        updateSubjectComboBox();
        updateTopicComboBox();
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
            updateTopicComboBox(); // Let the topic box listener handle filtering
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
    
    private void updateSubjectComboBox() {
        Set<String> subjects = Question.globalSubjectTopicManager.getSubjects();
        Map<String, String> subjectsMap = new HashMap<>();
        for (String subject : subjects) {
            subjectsMap.put(subject, subject);
        }
        setSubjects(subjectsMap);
    }

    private void updateTopicComboBox() {
        String selectedSubject = (String) solveQuestionsPage.getSubjectBox().getSelectedItem();
        Set<String> topics;

        if (selectedSubject != null && !selectedSubject.equals("N/A")) {
            topics = Question.globalSubjectTopicManager.getTopics(selectedSubject);
        } else {
            topics = new HashSet<>();
        }
        setTopics(topics);
    }

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
}
