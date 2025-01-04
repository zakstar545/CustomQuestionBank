package controller;

import view.page.HomePage;
import view.page.Frame;

import javax.swing.*;
import java.awt.*;

public class HomePageController {
    private HomePage homePage;
    private Frame frame;

    public HomePageController(Frame frame) {
        this.frame = frame;
        this.homePage = frame.getHomePage();
        addActionListeners();
    }

    private void addActionListeners() {
        homePage.getSolveQuestionsButton().addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "SolveQuestions");
        });

        homePage.getPracticeTestButton().addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "PracticeTest");
        });

        homePage.getModifyButton().addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "ModifyQuestions");
        });
    }
}