package model.core;

import javax.swing.ImageIcon;

import model.entity.Question;

import java.util.LinkedList;

public class QuestionBank {
        public static LinkedList<Question> questions = new LinkedList<>(); //creatin the STATIC/GLOBAL linked list question bank

        public static LinkedList<Question> getQuestions() {
            return questions;
        }

        public static void addQuestion(Question question) {
            questions.add(question);
        }

        public static void removeQuestion(Question question) {
            questions.remove(question);
        }

        public static void loadSampleQuestions() {
            // Load sample image from resources directory
            ImageIcon sampleImage = new ImageIcon("C:\\Users\\zakal\\OneDrive\\Desktop\\compsciIA\\resources\\Screenshot 2024-03-11 231547.png");
            
            // Add sample questions
            questions.add(new Question(1, "Mathematics", "Calculus", Question.Difficulty.MEDIUM, Question.TimeToSolve.ONE_TO_FIVE, Question.Paper.ONE, sampleImage, sampleImage, 10, "Calculate the derivative of x^2 + 3x + 2"));
            questions.add(new Question(2, "Physics", "Mechanics", Question.Difficulty.HARD, Question.TimeToSolve.FIVE_TO_TEN, Question.Paper.TWO, sampleImage, sampleImage, 15, "Explain the concept of inertia and its applications"));
            questions.add(new Question(3, "Chemistry", "Organic Chemistry", Question.Difficulty.EASY, Question.TimeToSolve.THIRTYFIVE_TO_SIXTY, Question.Paper.THREE, sampleImage, sampleImage, 5, "Draw the structure of methane and explain its properties"));
        }

}
