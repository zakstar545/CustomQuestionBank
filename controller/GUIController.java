package controller;

import view.HomePage;
import view.SolveQuestionsPage;
import view.Frame;
import model.entity.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController {
    private Frame frame;
    private SolveQuestionsPage solveQuestionsPage;
    private HomePage homePage;

    public GUIController(Frame frame) {
        this.frame = frame;
        this.solveQuestionsPage = frame.getSolveQuestionsPage();
        this.homePage = frame.getHomePage();

        // Populate subjectComboBox initially
        updateSubjectComboBox();

        // Add action listeners to the buttons
        addActionListeners();
    }

    private void addActionListeners() {
        homePage.getButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
                cardLayout.show(frame.getMainPanel(), "SolveQuestions");
                System.out.println("Button 1 clicked");
            }
        });

        homePage.getButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to Practice Test Page (to be implemented)
            }
        });

        homePage.getButton3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to Add/Edit Questions Page (to be implemented)
            }
        });
    }


    public void addSubject(String subject) {
        Question.globalSubjectTopicManager.addSubject(subject);
        updateSubjectComboBox();
    }

    public void removeSubject(String subject) {
        Question.globalSubjectTopicManager.removeSubject(subject);
        updateSubjectComboBox();
    }

    private void updateSubjectComboBox() {
        solveQuestionsPage.setSubjects(Question.globalSubjectTopicManager.getSubjects());
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
        new GUIController(frame);   // Pass the frame instance to the controller    
    }
}
