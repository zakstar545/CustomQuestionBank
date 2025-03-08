package controller;

import model.core.PracticeTest;
import model.entity.Question;
import model.service.FileManager;
import view.page.Frame;
import view.page.PracticeTestPage;
import model.service.PdfGenerationService;

import javax.swing.*;
import java.awt.*;
import java.io.File;

//This class is responsible for controlling the PracticeTest actions
public class PracticeTestController {
    private Frame frame;
    private PracticeTestPage practiceTestPage;
    
    public PracticeTestController(Frame frame) {
        this.frame = frame;
        this.practiceTestPage = frame.getPracticeTestPage();
        
        practiceTestPage.updateQuestionsList();
        
        addActionListeners();
    }
    
    private void addActionListeners() {
        practiceTestPage.getHomeButton().addActionListener((_) -> {
            CardLayout cardLayout = (CardLayout) frame.getMainPanel().getLayout();
            cardLayout.show(frame.getMainPanel(), "Home");
        });
        
        practiceTestPage.getGenerateTestButton().addActionListener((_) -> {
            if (PracticeTest.practiceQuestions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "No questions added to the practice test.", 
                    "Empty Test", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            generatePracticeTestPDF(false);
        });
        
        practiceTestPage.getGenerateMarkschemeButton().addActionListener((_) -> {
            if (PracticeTest.practiceQuestions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "No questions added to the practice test.", 
                    "Empty Test", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            generatePracticeTestPDF(true);
        });
        
        practiceTestPage.getClearAllButton().addActionListener((_) -> {
            if (!PracticeTest.practiceQuestions.isEmpty()) {
                int response = JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to remove all questions?", 
                    "Confirm Clear", 
                    JOptionPane.YES_NO_OPTION);
                
                if (response == JOptionPane.YES_OPTION) {
                    PracticeTest.practiceQuestions.clear();
                    practiceTestPage.updateQuestionsList();
                    
                    savePracticeTestData();
                }
            }
        });
    }
    
    private void generatePracticeTestPDF(boolean includeMarkscheme) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save " + (includeMarkscheme ? "Markscheme" : "Practice Test") + " PDF");
        
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
    
    public void addQuestionToPracticeTest(Question question) {
        if (!PracticeTest.practiceQuestions.contains(question)) {
            PracticeTest.practiceQuestions.add(question);
            practiceTestPage.updateQuestionsList();
            
            savePracticeTestData();
            
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