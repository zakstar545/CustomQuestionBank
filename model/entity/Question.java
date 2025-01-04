package model.entity;

import model.service.SubjectTopicManager;

import javax.swing.ImageIcon;

public class Question {
    public enum Difficulty {
        EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

        private final String label;

        Difficulty(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum TimeToSolve {
        ZERO_TO_ONE("0-1"), 
        ONE_TO_FIVE("1-5"), 
        FIVE_TO_TEN("5-10"), 
        TEN_TO_THIRTYFIVE("10-35"), 
        THIRTYFIVE_TO_SIXTY("35-60");

        private final String label;

        TimeToSolve(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum Paper {
        ONE("1"),
        TWO("2"),
        THREE("3");

        private final String label;

        Paper(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }        
    }

    private int id;    //unique code for identifying the question
    private String subject; // which IB subject
    private String topic;    // which topic from that subject
    private Difficulty difficulty;   //easy, medium, hard
    private TimeToSolve timeToSolve;    //time choices in minutes
    private Paper paper;     //IB paper 1,2,3
    private ImageIcon questionImage;    //Image associated with the question
    private ImageIcon markschemeImage; //Image associated with the markscheme
    private int marks;    //marks for the question
    private String title;

    public static SubjectTopicManager globalSubjectTopicManager = new SubjectTopicManager();

    public Question(int id, String subject, String topic, Difficulty difficulty, TimeToSolve timeToSolve, Paper paper, ImageIcon questionImage, ImageIcon markschemeImage, int marks, String title) {
        if (!globalSubjectTopicManager.isValidSubject(subject)) {
            globalSubjectTopicManager.addSubject(subject);
        }
        if (!globalSubjectTopicManager.isValidTopic(subject, topic)) {
            globalSubjectTopicManager.addTopic(subject, topic);
        }

        this.id = id;
        this.subject = subject;
        this.topic = topic;
        this.difficulty = difficulty;
        this.timeToSolve = timeToSolve;
        this.paper = paper;
        this.questionImage = questionImage;
        this.markschemeImage = markschemeImage;
        this.marks = marks;
        this.title = title;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty.getLabel();
    }

    public String getTimeToSolve() {
        return timeToSolve.getLabel();
    }

    public String getPaper() {
        return paper.getLabel();
    }

    public ImageIcon getQuestionImage() {
        return questionImage;
    }

    public ImageIcon getMarkschemeImage() {
        return markschemeImage;
    }

    public int getMarks() {
        return marks;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        if (!globalSubjectTopicManager.isValidSubject(subject)) {
            globalSubjectTopicManager.addSubject(subject);
        }
        this.subject = subject;
    }

    public void setTopic(String topic) {
        if (!globalSubjectTopicManager.isValidTopic(subject, topic)) {
            globalSubjectTopicManager.addTopic(subject, topic);
        }
        this.topic = topic;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setTimeToSolve(TimeToSolve timeToSolve) {
        this.timeToSolve = timeToSolve;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void setQuestionImage(ImageIcon questionImage) {
        this.questionImage = questionImage;
    }

    public void setMarkschemeImage(ImageIcon markschemeImage) {
        this.markschemeImage = markschemeImage;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", topic='" + topic + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", timeToSolve='" + timeToSolve + '\'' +
                ", paper=" + paper +
                ", questionImage=" + (questionImage != null ? questionImage.getDescription() : "No Image") +
                ", markschemeImage=" + (markschemeImage != null ? markschemeImage.getDescription() : "No Markscheme Image") +
                ", marks=" + marks +
                '}';
    }
}