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
                        ImageIcon originalImage = QuestionBank.getQuestions().get(questionIndex).getQuestionImage();
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
