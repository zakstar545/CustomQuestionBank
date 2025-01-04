package controller;

import model.core.QuestionBank;
import model.entity.Question;
import view.page.Frame;
import view.page.HomePage;
import view.page.ModifyQuestionsPage;
import view.page.PracticeTestPage;
import view.page.SolveQuestionsPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GUIController {
    public static Frame frame;
    private HomePage homePage;
    private SolveQuestionsPage solveQuestionsPage;
    private PracticeTestPage practiceTestPage;
    private ModifyQuestionsPage modifyQuestionsPage;

    public GUIController(Frame frame) {
        GUIController.frame = frame;
        this.homePage = frame.getHomePage(); // Initialize homePage
        this.solveQuestionsPage = frame.getSolveQuestionsPage();        
        this.practiceTestPage = frame.getPracticeTestPage();
        this.modifyQuestionsPage = frame.getModifyQuestionsPage();

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
            updateTopicComboBox(); // Update topics based on selected subject
            filterQuestions();
        });

        solveQuestionsPage.getTopicBox().addActionListener(e -> {
            filterQuestions();
        });

        for (JCheckBox paperBox : solveQuestionsPage.getPaperBoxes()) {
            paperBox.addActionListener(e -> {
                handleCheckboxSelection(solveQuestionsPage.getPaperBoxes(), (JCheckBox) e.getSource());
                filterQuestions();
            });
        }

        for (JCheckBox difficultyBox : solveQuestionsPage.getDifficultyBoxes()) {
            difficultyBox.addActionListener(e -> {
                handleCheckboxSelection(solveQuestionsPage.getDifficultyBoxes(), (JCheckBox) e.getSource());
                filterQuestions();  
            });
        }

        for (JCheckBox timeBox : solveQuestionsPage.getTimeBoxes()) {
            timeBox.addActionListener(e -> {
                handleCheckboxSelection(solveQuestionsPage.getTimeBoxes(), (JCheckBox) e.getSource());
                filterQuestions();
            });
        }
    }

    private void handleCheckboxSelection(JCheckBox[] checkBoxes, JCheckBox selectedCheckbox) {
        if (selectedCheckbox.isSelected()) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox != selectedCheckbox) {
                    checkBox.setSelected(false);
                }
            }
        } else {
            selectedCheckbox.setSelected(false);
        }
    }

    private void filterQuestions() {
        String selectedSubject = (String) solveQuestionsPage.getSubjectBox().getSelectedItem();
        String selectedTopic = (String) solveQuestionsPage.getTopicBox().getSelectedItem();
        String selectedPaper = null;
        String selectedTime = null;

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

        for (JCheckBox timeBox : solveQuestionsPage.getTimeBoxes()) {
            if (timeBox.isSelected()) {
                selectedTime = timeBox.getText();
                break;
            }
        }

        ArrayList<Question> filteredQuestions = new ArrayList<>();
        for (Question question : QuestionBank.getQuestions()) {
            boolean matches = true;
            if (selectedSubject != null && !selectedSubject.equals("N/A") && !question.getSubject().equals(selectedSubject)) {
                matches = false;
            }
            if (selectedTopic != null && !selectedTopic.equals("N/A") && !question.getTopic().equals(selectedTopic)) {
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
                System.out.println("time is not a match");
            }
            if (matches) {
                filteredQuestions.add(question);
            }
        }

        updateQuestions(filteredQuestions, solveQuestionsPage.getQuestionContainer());
    }

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
        // Store current panel width
        int currentWidth = questionsContainer.getWidth();
        
        // Clear and rebuild the container
        questionsContainer.removeAll();
        for (Question question : filteredQuestions) {
            JPanel card = solveQuestionsPage.getQuestionCard(question);
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

    
    
    public static void main(String[] args) {
        // Set the look and feel to the system's default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the frame and controller
        Frame frame = new Frame();
        GUIController controller = new GUIController(frame); // Create controller instance
    }
}
