package controller;

import model.core.PracticeTest;
import model.entity.Question;
import model.service.FileManager;
import view.page.Frame;
import view.page.PracticeTestPage;
import model.service.PdfGenerationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class PracticeTestController {
    private Frame frame;
    private PracticeTestPage practiceTestPage;
    
    public PracticeTestController(Frame frame) {
        this.frame = frame;
        this.practiceTestPage = frame.getPracticeTestPage();
        
        // Initialize the UI
        practiceTestPage.updateQuestionsList();
        
        // Add action listeners
        addActionListeners();
    }
    
    private void addActionListeners() {
        // Home button
        practiceTestPage.getHomeButton().addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "Home");
        });
        
        // Generate test PDF button
        practiceTestPage.getGenerateTestButton().addActionListener(e -> {
            if (PracticeTest.practiceQuestions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "No questions added to the practice test.", 
                    "Empty Test", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            generatePracticeTestPDF(false);
        });
        
        // Generate markscheme PDF button
        practiceTestPage.getGenerateMarkschemeButton().addActionListener(e -> {
            if (PracticeTest.practiceQuestions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "No questions added to the practice test.", 
                    "Empty Test", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            generatePracticeTestPDF(true);
        });
        
        // Clear all button
        practiceTestPage.getClearAllButton().addActionListener(e -> {
            if (!PracticeTest.practiceQuestions.isEmpty()) {
                int response = JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to remove all questions?", 
                    "Confirm Clear", 
                    JOptionPane.YES_NO_OPTION);
                
                if (response == JOptionPane.YES_OPTION) {
                    PracticeTest.practiceQuestions.clear();
                    practiceTestPage.updateQuestionsList();
                    
                    // Save the updated practice test
                    savePracticeTestData();
                }
            }
        });
    }
    
    // Method to generate PDF with test questions or markscheme
    private void generatePracticeTestPDF(boolean includeMarkscheme) {
        // Get file save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save " + (includeMarkscheme ? "Markscheme" : "Practice Test") + " PDF");
        
        // Set default filename based on test title
        String testTitle = practiceTestPage.getTestTitle();
        if (testTitle.isEmpty()) {
            testTitle = "Practice Test";
        }
        
        String filename = testTitle + (includeMarkscheme ? " Markscheme" : "") + ".pdf";
        fileChooser.setSelectedFile(new File(filename));
        
        int result = fileChooser.showSaveDialog(frame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File outputFile = fileChooser.getSelectedFile();
        
        PdfGenerationService.generatePDF(outputFile, PracticeTest.practiceQuestions, includeMarkscheme, testTitle);
    }
    
    // Method to add a question to the practice test and update UI
    public void addQuestionToPracticeTest(Question question) {
        // Only add if not already in the practice test
        if (!PracticeTest.practiceQuestions.contains(question)) {
            PracticeTest.practiceQuestions.add(question);
            practiceTestPage.updateQuestionsList();
            
            // Save the updated practice test
            savePracticeTestData();
            
            // Optional: Show a brief message
            JOptionPane.showMessageDialog(frame, 
                "Question added to practice test.", 
                "Question Added", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, 
                "This question is already in your practice test.", 
                "Duplicate Question", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void savePracticeTestData() {
        FileManager.savePracticeTest();
    }
}