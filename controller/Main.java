package controller;

import view.page.Frame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set the look and feel to the system's default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the frame
        Frame frame = new Frame();

        // Initialize controllers for each page
        HomePageController homeController = new HomePageController(frame);
        SolveQuestionsPageController solveQuestionsController = new SolveQuestionsPageController(frame);
        frame.getSolveQuestionsPage().setSolveQuestionsController(solveQuestionsController);
        // Add other controllers as needed
    }
}