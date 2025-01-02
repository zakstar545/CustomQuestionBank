package controller;

import model.core.QuestionBank;
import view.HomePage;
import view.SolveQuestionsPage;
import view.Frame;
import model.entity.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIController {
        public static Frame frame;
        private SolveQuestionsPage solveQuestionsPage;
        private HomePage homePage;
    
        public GUIController(Frame frame) {
            this.frame = frame;
            this.solveQuestionsPage = frame.getSolveQuestionsPage();
            this.homePage = frame.getHomePage();
    
            // Populate subjectComboBox and topicComboBox initially
            updateSubjectComboBox();
            updateTopicComboBox();
    
            // Add action listeners to the buttons
            addActionListeners();
        }
        
        
        private void addActionListeners() {
            homePage.getSolveQuestionsButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
                    cardLayout.show(frame.getMainPanel(), "SolveQuestions");
                    System.out.println("Button 1 clicked");
                }
            });
    
            homePage.getPracticeTestButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Navigate to Practice Test Page (to be implemented)
                }
            });
    
            homePage.getModifyButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Navigate to Modify Questions Page (to be implemented)
                }
            });
    
            solveQuestionsPage.getHomeButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
                    cardLayout.show(frame.getMainPanel(), "Home");
                }
            });
    
            solveQuestionsPage.getSubjectBox().addActionListener(e -> {
                JComboBox<String> subjectBox = (JComboBox<String>) e.getSource();
                if (subjectBox.getSelectedItem().equals("N/A")) {
                    subjectBox.setToolTipText("Choose Subject...");
                } else {
                    subjectBox.setToolTipText(null);
                }
                filterQuestions();
            });
    
            solveQuestionsPage.getTopicBox().addActionListener(e -> {
                JComboBox<String> topicBox = (JComboBox<String>) e.getSource();

                if (topicBox.getSelectedItem().equals("N/A")) {
                    topicBox.setToolTipText("Choose Topic...");
                } else {
                    topicBox.setToolTipText(null);
                }
                filterQuestions();
            });
    
            for (JCheckBox paperBox : solveQuestionsPage.getPaperBoxes()) {
                paperBox.addActionListener(e -> filterQuestions());
            }
    
            for (JCheckBox difficultyBox : solveQuestionsPage.getDifficultyBoxes()) {
                difficultyBox.addActionListener(e -> filterQuestions());
            }
    
            for (JCheckBox timeBox : solveQuestionsPage.getTimeBoxes()) {
                timeBox.addActionListener(e -> filterQuestions());
            }
        }
    
        private void filterQuestions() {
            String selectedSubject = (String) solveQuestionsPage.getSubjectBox().getSelectedItem();
            String selectedTopic = (String) solveQuestionsPage.getTopicBox().getSelectedItem();
            String selectedPaper = null;
            for (JCheckBox paperBox : solveQuestionsPage.getPaperBoxes()) {
                if (paperBox.isSelected()) {
                    selectedPaper = paperBox.getText();
                    break;
                }
            }
            String selectedDifficulty = null;
            for (JCheckBox difficultyBox : solveQuestionsPage.getDifficultyBoxes()) {
                if (difficultyBox.isSelected()) {
                    selectedDifficulty = difficultyBox.getText();
                    break;
                }
            }
            String selectedTime = null;
            for (JCheckBox timeBox : solveQuestionsPage.getTimeBoxes()) {
                if (timeBox.isSelected()) {
                    selectedTime = timeBox.getText();
                    break;
                }
            }
    
            ArrayList<Question> filteredQuestions = new ArrayList<>();
            for (Question question : QuestionBank.getQuestions()) {
                boolean matches = true;
                if (!selectedSubject.equals("N/A") && !question.getSubject().equals(selectedSubject)) {
                    matches = false;
                }
                if (!selectedTopic.equals("N/A") && !question.getTopic().equals(selectedTopic)) {
                    matches = false;
                }
                if (selectedPaper != null && !question.getPaper().equals(selectedPaper)) {
                    matches = false;
                }
                if (selectedDifficulty != null && !question.getDifficulty().equals(selectedDifficulty)) {
                    matches = false;
                }
                if (selectedTime != null && !question.getTimeToSolve().equals(selectedTime)) {
                    matches = false;
                }
                if (matches) {
                    filteredQuestions.add(question);
                }
            }
    
            solveQuestionsPage.updateQuestions(filteredQuestions);
        }
    

    public void addSubject(String subject) {
        Question.globalSubjectTopicManager.addSubject(subject);
        updateSubjectComboBox();
    }

    public void removeSubject(String subject) {
        Question.globalSubjectTopicManager.removeSubject(subject);
        updateSubjectComboBox();
    }

    public void addTopic(String topic) {
        Question.globalSubjectTopicManager.addTopic(topic);
        updateTopicComboBox();
    }

    public void removeTopic(String topic) {
        Question.globalSubjectTopicManager.removeTopic(topic);
        updateTopicComboBox();
    }

    private void updateSubjectComboBox() {
        solveQuestionsPage.setSubjects(Question.globalSubjectTopicManager.getSubjects());
    }

    private void updateTopicComboBox() {
        solveQuestionsPage.setTopics(Question.globalSubjectTopicManager.getTopics());
    }

    public static void main(String[] args) {
        // Set the look and feel to the system's default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the frame and controller
        Frame frame = new Frame();
        new GUIController(frame);   // Pass the frame instance to the controller    
    }
}
