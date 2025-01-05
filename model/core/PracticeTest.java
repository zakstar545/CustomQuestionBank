package model.core;

import javax.swing.ImageIcon;
import model.entity.Question;
import java.util.LinkedList;


public class PracticeTest {
    //creat the STATIC/GLOBAL linked list practice questions bank
    //these are the questions that are in the practice test
    public static LinkedList<Question> practiceQuestions = new LinkedList<>(); 

    public static void removeQuestion(Question question) {
        practiceQuestions.remove(question);
    }
}
