package controller;

import view.page.HomePage;
import view.page.Frame;

import java.awt.*;

//This class is responsible for controlling the HomePage actions
public class HomePageController {
    private HomePage homePage;
    private Frame frame;

    public HomePageController(Frame frame) {
        this.frame = frame;
        this.homePage = frame.getHomePage();
        addActionListeners();
    }

    private void addActionListeners() {
        homePage.getSolveQuestionsButton().addActionListener((_) -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "SolveQuestions");
        });

        homePage.getPracticeTestButton().addActionListener((_) -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "PracticeTest");
        });
    }
}