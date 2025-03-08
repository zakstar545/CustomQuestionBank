package model.core;

import model.entity.Question;
import java.util.LinkedList;


public class PracticeTest {
    //creat the STATIC/GLOBAL linked list practice questions bank
    //these are the questions that are in the practice test
    public static LinkedList<Question> practiceQuestions = new LinkedList<>(); 

    public static void removeQuestion(Question question) {
        practiceQuestions.remove(question);
        // Save the changes
        model.service.FileManager.savePracticeTest();
    }
    
    // Add a method to handle question editing
    public static void updateQuestionReferences() {
        // This method is called when questions are edited to ensure practice test has up-to-date references
        LinkedList<Question> updatedList = new LinkedList<>();
        for (Question practiceQuestion : practiceQuestions) {
            for (Question bankQuestion : QuestionBank.questions) {
                if (bankQuestion.getId() == practiceQuestion.getId()) {
                    updatedList.add(bankQuestion);
                    break;
                }
            }
        }
        practiceQuestions = updatedList;
        model.service.FileManager.savePracticeTest();
    }
}
