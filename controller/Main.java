package controller;

import model.service.FileManager;
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
    
        FileManager.loadAllData();
    
        // Create the frame
        Frame frame = new Frame();
    
        // Initialize controllers for each page
        HomePageController homeController = new HomePageController(frame);
        SolveQuestionsPageController solveQuestionsController = new SolveQuestionsPageController(frame);
        PracticeTestController practiceTestController = new PracticeTestController(frame);
        
        frame.getSolveQuestionsPage().setSolveQuestionsController(solveQuestionsController);
        
        // Update practice test page with loaded data
        frame.getPracticeTestPage().updateQuestionsList();
    
        Runtime.getRuntime().addShutdownHook(new Thread(FileManager::saveAllData));
    }
}